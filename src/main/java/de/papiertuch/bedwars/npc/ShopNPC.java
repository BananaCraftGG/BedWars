package de.papiertuch.bedwars.npc;

import com.github.juliarn.npc.NPC;
import com.github.juliarn.npc.NPCPool;
import com.github.juliarn.npc.event.PlayerNPCHideEvent;
import com.github.juliarn.npc.event.PlayerNPCInteractEvent;
import com.github.juliarn.npc.event.PlayerNPCShowEvent;
import com.github.juliarn.npc.modifier.LabyModModifier;
import com.github.juliarn.npc.modifier.MetadataModifier;
import com.github.juliarn.npc.profile.Profile;
import de.papiertuch.bedwars.BedWars;
import de.papiertuch.bedwars.utils.LocationAPI;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;
@Getter
@Setter
public class ShopNPC implements Listener {
    private final Random random;

    private ArrayList<Location> shopLocations = new ArrayList<>();

    private Profile playerProfile;

    public ShopNPC(Plugin plugin) {
        BedWars.getInstance().setNpcPool(NPCPool.builder(plugin)
                .spawnDistance(60)
                .actionDistance(30)
                .tabListRemoveTicks(20)
                .build());
        this.random = new Random();
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public void createNPC(Location location) {
        NPC npc = NPC.builder()
                .profile(playerProfile)
                .location(location)
                .imitatePlayer(false)
                .lookAtPlayer(true)
                .usePlayerProfiles(true)
                .build(BedWars.getInstance().getNpcPool());
    }

    public Profile createProfile() {
        Profile profile = new Profile(new UUID(this.random.nextLong(), 0));
        profile.complete();
        profile.setName(ChatColor.GOLD + "Shop");
        profile.setUniqueId(new UUID(this.random.nextLong(), 0));
        return profile;
    }

    public void loadNPCLocations() {
        LocationAPI locationAPI = new LocationAPI("shops");
        int npcCount = locationAPI.getCfg().getInt("shops");
        Bukkit.broadcastMessage("count " + npcCount);
        for (int i = 1; i <= npcCount; i++) {
            shopLocations.add(locationAPI.getLocation("shop." + i));
        }
    }

    public void spawnNPCs() {
        ShopNPC shopNPC = BedWars.getInstance().getShopNPC();
        shopLocations.forEach(location -> {
            if(location.getWorld().getName().equals(BedWars.getInstance().getMap())) {
                shopNPC.createNPC(location);
            }
        });
    }

    @EventHandler
    public void handleNPCShow(PlayerNPCShowEvent event) {
        NPC npc = event.getNPC();
        event.send(
                npc.metadata()
                        .queue(MetadataModifier.EntityMetadata.SKIN_LAYERS, true));
        Bukkit.getScheduler().runTaskTimer(BedWars.getInstance(), () -> {
            event.send(
                    npc.labymod()
                            .queue(LabyModModifier.LabyModAction.EMOTE, 78));
        }, 20L, 60L);

    }

    @EventHandler
    public void handleNPCHide(PlayerNPCHideEvent event) {
        Player player = event.getPlayer();
        NPC npc = event.getNPC();

        if (event.getReason() == PlayerNPCHideEvent.Reason.EXCLUDED) {
            npc.removeExcludedPlayer(player);
        }
    }
    @EventHandler
    public void handleNPCInteract(PlayerNPCInteractEvent event) throws SQLException {
        Player player = event.getPlayer();
        NPC npc = event.getNPC();
        shopLocations.forEach(location -> {
            if (event.getUseAction() == PlayerNPCInteractEvent.EntityUseAction.INTERACT && event.getNPC().getLocation().equals(location)) {
                player.openInventory(BedWars.getInstance().getShopHandler().getMainInventory(player));
            }
        });

    }
}

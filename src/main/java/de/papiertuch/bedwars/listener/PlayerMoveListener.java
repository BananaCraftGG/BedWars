package de.papiertuch.bedwars.listener;

import de.papiertuch.bedwars.BedWars;
import de.papiertuch.bedwars.enums.GameState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

/**
 * Created by Leon on 15.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (BedWars.getInstance().getGameState() == GameState.INGAME) {
            if (BedWars.getInstance().getGameHandler().getTeam(player) == null) {
                if (BedWars.getInstance().getSpectators().contains(player.getUniqueId())) {
                    for (Entity entity : player.getNearbyEntities(3, 3, 3)) {
                        if (entity instanceof Player) {
                            Player target = (Player) entity;
                            if (target != player) {
                                if (BedWars.getInstance().getPlayers().contains(target.getUniqueId())) {
                                    if (target.getLocation().distance(player.getLocation()) <= 3) {
                                        Vector vector = new Vector(player.getLocation().getX() - target.getLocation().getX(),
                                                player.getLocation().getY() - target.getLocation().getY(),
                                                player.getLocation().getZ() - target.getLocation().getZ());
                                        player.setVelocity(vector.normalize().multiply(1D).setY(0.3D));
                                    }
                                }
                            }
                        }
                    }
                }
                return;
            }
            String team = BedWars.getInstance().getGameHandler().getTeam(player).getName().toLowerCase();
            if (player.getLocation().getY() <= (BedWars.getInstance().getLocationAPI(BedWars.getInstance().getMap()).getLocation(team + ".spawn").getY() - 50)) {
                if (BedWars.getInstance().getPlayers().contains(player.getUniqueId())) {
                    if (!player.isDead()) {
                        player.setHealth(0);
                    }
                }
            }
        }
    }
}

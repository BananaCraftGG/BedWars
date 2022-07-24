package de.papiertuch.bedwars.game;

import de.papiertuch.bedwars.BedWars;
import de.papiertuch.bedwars.api.events.GameEndingEvent;
import de.papiertuch.bedwars.enums.GameState;
import de.papiertuch.nickaddon.NickAddon;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import xyz.haoshoku.nick.api.NickAPI;

/**
 * Created by Leon on 14.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class Ending {

    private int
            taskID,
            seconds = BedWars.getInstance().getBedWarsConfig().getInt("countDown.endingDuration") + 1;
    private float xp = 1.00f;

    public void startCountdown() {
        BedWars.getInstance().setGameState(GameState.ENDING);
        seconds = BedWars.getInstance().getBedWarsConfig().getInt("countDown.endingDuration") + 1;
        Bukkit.getPluginManager().callEvent(new GameEndingEvent());
        BedWars.getInstance().getScheduler().getGame().stopCountdown();
        BedWars.getInstance().getScheduler().getBoarder().stop();
        for (Player a : Bukkit.getOnlinePlayers()) {
            if (BedWars.getInstance().getPlayers().contains(a.getUniqueId())) {
                BedWars.getInstance().getGameHandler().setPlayer(a);
                a.playSound(a.getLocation(), BedWars.getInstance().getGameHandler().getSound("FIREWORK_TWINKLE"), 10F, 10F);
                BedWars.getInstance().getBoard().setScoreBoard(a);
                BedWars.getInstance().getBoard().updateNameTags(a);
                BedWars.getInstance().getPlayers().remove(a.getUniqueId());
            }
        }
        startFirework();
        BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.roundEnds"));
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(BedWars.getInstance(), () -> {
            BedWars.getInstance().getBoard().updateBoard();
            for (Player a : Bukkit.getOnlinePlayers()) {
                if (Bukkit.getPluginManager().getPlugin("NickAddon") != null) {
                    if (NickAPI.isNicked(a)) {
                        NickAddon.getInstance().getApi().setNick(a, false);
                        BedWars.getInstance().getBoard().addPlayerToBoard(a);
                    }
                }
                xp = (((float) 1 / 60) * seconds);
                a.setExp(xp);
                a.setLevel(seconds);
                a.showPlayer(a);
                a.setAllowFlight(false);
                a.setFlying(false);
                if (BedWars.getInstance().getSpectators().contains(a.getUniqueId())) {
                    BedWars.getInstance().getSpectators().remove(a.getUniqueId());
                }
                if (BedWars.getInstance().getPlayers().contains(a.getUniqueId())) {
                    BedWars.getInstance().getGameHandler().setPlayer(a);
                    a.playSound(a.getLocation(), BedWars.getInstance().getGameHandler().getSound("FIREWORK_TWINKLE"), 10F, 10F);
                    BedWars.getInstance().getBoard().setScoreBoard(a);
                    BedWars.getInstance().getBoard().updateNameTags(a);
                    BedWars.getInstance().getPlayers().remove(a.getUniqueId());
                }
                if (!a.getLocation().getWorld().getName().equalsIgnoreCase("world")) {
                    BedWars.getInstance().getGameHandler().setPlayer(a);
                }
            }
            switch (seconds) {
                case 15:
                    BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.serverStopIn")
                            .replace("%seconds%", String.valueOf(seconds)));
                    for (Player a : Bukkit.getOnlinePlayers()) {
                        a.playSound(a.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.endingCountdown")), 1, 1);
                    }
                    break;
                case 10:
                case 5:
                case 4:
                case 3:
                case 2:
                    BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.serverStopIn")
                            .replace("%seconds%", String.valueOf(seconds)));
                    for (Player a : Bukkit.getOnlinePlayers()) {
                        a.playSound(a.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.endingCountdown")), 1, 1);
                    }
                    break;
                case 1:
                    BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.serverStopInOneSecond"));
                    for (Player a : Bukkit.getOnlinePlayers())
                        a.playSound(a.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.endingCountdown")), 1, 1);
                    break;
                case 0:
                    BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.serverStop"));
                    for (Player a : Bukkit.getOnlinePlayers()) {
                        BedWars.getInstance().getGameHandler().sendToFallback(a);
                    }
                    Bukkit.getScheduler().runTaskLater(BedWars.getInstance(), () -> {
                        if (BedWars.getInstance().getBedWarsConfig().getBoolean("settings.stopServerAfterRound")) {
                            Bukkit.getServer().shutdown();
                            return;
                        }
                        BedWars.getInstance().loadGame();
                    }, 10);
                    break;
            }
            seconds--;

        }, 0, 20);

    }

    public void stopCountdown() {
        Bukkit.getScheduler().cancelTask(taskID);
    }

    public void startFirework() {
        Bukkit.getScheduler().runTaskLater(BedWars.getInstance(), () -> setFirework(), 20);
    }

    public int getSeconds() {
        return seconds;
    }

    private void setFirework() {
        Firework firework = BedWars.getInstance().getLocationAPI(BedWars.getInstance().getMap()).getLocation("lobby").getWorld().spawn(BedWars.getInstance().getLocationAPI(BedWars.getInstance().getMap()).getLocation("lobby"), Firework.class);
        FireworkMeta fireworkMeta = firework.getFireworkMeta();
        fireworkMeta.setPower(1);
        fireworkMeta.addEffect(FireworkEffect.builder().withFade(Color.BLUE).withColor(Color.YELLOW).flicker(true).withColor(Color.LIME).trail(true).build());
        firework.setFireworkMeta(fireworkMeta);
    }
}

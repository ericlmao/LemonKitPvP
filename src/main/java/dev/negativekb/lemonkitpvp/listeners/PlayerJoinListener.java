package dev.negativekb.lemonkitpvp.listeners;

import dev.negativekb.lemonkitpvp.core.data.KitPvPPlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        KitPvPPlayerManager manager = KitPvPPlayerManager.getInstance();

        if (manager.getPlayerByPlayer(player) != null)
            return;

        manager.createPlayer(player.getUniqueId());
    }

}

package dev.negativekb.lemonkitpvp.listeners;

import dev.negativekb.lemonkitpvp.core.data.ClanManager;
import dev.negativekb.lemonkitpvp.core.data.KitPvPPlayerManager;
import dev.negativekb.lemonkitpvp.core.structure.KitPvPPlayer;
import dev.negativekb.lemonkitpvp.core.structure.clans.Clan;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        KitPvPPlayer player = KitPvPPlayerManager.getInstance().getPlayerByPlayer(event.getPlayer());
        Clan clan = ClanManager.getInstance().getClanByPlayer(event.getPlayer());

        if (clan == null)
            event.setFormat(translate("&8[&f" + player.getLevel() + "✮&8] " + event.getFormat()));
        else
            event.setFormat(translate("&8[&f" + clan.getName() + "&8] [&f" + player.getLevel() + "✮&8] ") + event.getFormat());
    }

    private String translate(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}

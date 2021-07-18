package dev.negativekb.lemonkitpvp.listeners;

import dev.negativekb.lemonkitpvp.core.data.ClanManager;
import dev.negativekb.lemonkitpvp.core.structure.clans.Clan;
import dev.negativekb.lemonkitpvp.core.structure.clans.ClanUpgrade;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import java.util.concurrent.ThreadLocalRandom;

public class PlayerSaturationListener implements Listener {

    @EventHandler
    public void onSaturationChange(FoodLevelChangeEvent event) {
        if (!(event.getEntity() instanceof Player))
            return;

        Player player = (Player) event.getEntity();
        ClanManager clanManager = ClanManager.getInstance();
        Clan clan = clanManager.getClanByPlayer(player);
        if (clan == null)
            return;

        boolean has = clan.hasUpgrade(ClanUpgrade.SATURATION);
        if (!has)
            return;

        int level = clan.getUpgradeLevel(ClanUpgrade.SATURATION);
        double chance = level * 10;
        ThreadLocalRandom random = ThreadLocalRandom.current();
        double v = random.nextDouble(0, 100);

        boolean win = (v <= chance);

        if (win) {
            event.setCancelled(true);
            player.setSaturation(20);
        }
    }
}

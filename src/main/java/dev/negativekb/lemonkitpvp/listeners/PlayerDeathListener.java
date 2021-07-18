package dev.negativekb.lemonkitpvp.listeners;

import dev.negativekb.lemonkitpvp.core.data.KitPvPPlayerManager;
import dev.negativekb.lemonkitpvp.core.managers.KitPvPLevelManager;
import dev.negativekb.lemonkitpvp.core.structure.KitPvPPlayer;
import dev.negativekb.lemonkitpvp.core.util.Message;
import dev.negativekb.lemonkitpvp.core.util.UtilPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.concurrent.ThreadLocalRandom;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        event.getDrops().clear();

        Player player = event.getEntity();

        KitPvPPlayerManager manager = KitPvPPlayerManager.getInstance();
        KitPvPPlayer victim = manager.getPlayerByPlayer(player);
        if (victim.getKillStreak() > 10)
            new Message("&6&l" + player.getName() + " &ehas been killed on a streak of &c&l" + victim.getKillStreak()).broadcast();

        victim.setDeaths(victim.getDeaths() + 1);
        victim.setKillStreak(0);
        event.setDeathMessage("");
        if (player.getKiller() == null)
            return;

        Player killer = player.getKiller();

        new Message("&c" + player.getName() + " &7has been killed by &c" + killer.getName()).broadcast();

        KitPvPPlayer attacker = manager.getPlayerByPlayer(killer);
        attacker.setKills(attacker.getKills() + 1);

        attacker.setKillStreak(attacker.getKillStreak() + 1);
        int killStreak = attacker.getKillStreak();
        if (killStreak > attacker.getBestKillStreak())
            attacker.setBestKillStreak(killStreak);

        if (killStreak % 10 == 0)
            new Message("&6&l" + killer.getName() + " &eis on a killstreak of &6&l" + killStreak).broadcast();

        attacker.setXp(attacker.getXp() + (victim.getLevel() + 1));
        checkLevel(attacker);

        ThreadLocalRandom random = ThreadLocalRandom.current();
        int amount = random.nextInt(25) + 1;
        attacker.setCoins(attacker.getCoins() + amount);

        player.spigot().respawn();
        UtilPlayer.reset(player);
    }

    private void checkLevel(KitPvPPlayer player) {
        double xp = player.getXp();
        double required = KitPvPLevelManager.getInstance().getRequiredExperience((player.getLevel() + 1));

        if (xp >= required) {
            player.setXp(0);
            player.setLevel(player.getLevel() + 1);

            new Message("&a&lYou have levelled up to level &6&l" + player.getLevel()).send(Bukkit.getPlayer(player.getUuid()));
        }
    }
}

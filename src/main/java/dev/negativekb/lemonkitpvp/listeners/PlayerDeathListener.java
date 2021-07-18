package dev.negativekb.lemonkitpvp.listeners;

import dev.negativekb.lemonkitpvp.core.data.ClanManager;
import dev.negativekb.lemonkitpvp.core.data.KitPvPPlayerManager;
import dev.negativekb.lemonkitpvp.core.managers.KitPvPLevelManager;
import dev.negativekb.lemonkitpvp.core.structure.KitPvPPlayer;
import dev.negativekb.lemonkitpvp.core.structure.clans.Clan;
import dev.negativekb.lemonkitpvp.core.structure.clans.ClanUpgrade;
import dev.negativekb.lemonkitpvp.core.util.ItemBuilder;
import dev.negativekb.lemonkitpvp.core.util.Message;
import dev.negativekb.lemonkitpvp.core.util.UtilPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

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

        ClanManager clanManager = ClanManager.getInstance();
        Clan killerClan = clanManager.getClanByPlayer(killer);
        if (killerClan != null) {
            killerClan.setKills(killerClan.getKills() + 1);
        }

        Clan victimClan = clanManager.getClanByPlayer(player);
        if (victimClan != null)
            victimClan.setDeaths(victimClan.getDeaths() + 1);

        new Message("&c" + player.getName() + " &7has been killed by &c" + killer.getName()).broadcast();

        KitPvPPlayer attacker = manager.getPlayerByPlayer(killer);
        attacker.setKills(attacker.getKills() + 1);

        attacker.setKillStreak(attacker.getKillStreak() + 1);
        int killStreak = attacker.getKillStreak();
        if (killStreak > attacker.getBestKillStreak())
            attacker.setBestKillStreak(killStreak);

        if (killStreak % 10 == 0)
            new Message("&6&l" + killer.getName() + " &eis on a killstreak of &6&l" + killStreak).broadcast();

        double initialXP = attacker.getXp() + (victim.getLevel() + 1);
        double xpToGive = initialXP + +attemptApplyDoubleXPClanUpgrade(attacker, initialXP);
        attacker.setXp(attacker.getXp() + (victim.getLevel() + 1));
        checkLevel(attacker);

        ThreadLocalRandom random = ThreadLocalRandom.current();
        int amount = random.nextInt(25) + 1;
        double reward = amount + attemptApplyDoubleCoinClanUpgrade(attacker, amount);
        attacker.setCoins(attacker.getCoins() + reward);

        if (victim.getBounty() != 0) {
            attacker.setCoins(attacker.getCoins() + victim.getBounty());
            new Message("&c&l" + killer.getName() + " &ehas claimed a &6&l" +
                    victim.getBounty() + " coin &ebounty on &c" + player.getName())
                    .broadcast();
            victim.setBounty(0);
        }

        attemptPotionKillUpgrade(attacker);

        player.spigot().respawn();
        UtilPlayer.reset(player);
    }

    private double attemptApplyDoubleXPClanUpgrade(KitPvPPlayer player, double initial) {
        ClanManager clanManager = ClanManager.getInstance();
        Clan clan = clanManager.getClanByPlayer(player.getPlayer());
        if (clan == null)
            return 0;

        boolean has = clan.hasUpgrade(ClanUpgrade.DOUBLE_XP);
        if (!has)
            return 0;

        int level = clan.getUpgradeLevel(ClanUpgrade.DOUBLE_XP);
        double chance = level * 10;
        ThreadLocalRandom random = ThreadLocalRandom.current();
        double v = random.nextDouble(0, 100);

        boolean win = (v <= chance);

        if (win)
            // This will just return the same value because it would be initial + initial, aka double
            return initial;
        else
            return 0;
    }

    private double attemptApplyDoubleCoinClanUpgrade(KitPvPPlayer player, double initial) {
        ClanManager clanManager = ClanManager.getInstance();
        Clan clan = clanManager.getClanByPlayer(player.getPlayer());
        if (clan == null)
            return 0;

        boolean has = clan.hasUpgrade(ClanUpgrade.DOUBLE_COINS);
        if (!has)
            return 0;

        int level = clan.getUpgradeLevel(ClanUpgrade.DOUBLE_COINS);
        double chance = level * 10;
        ThreadLocalRandom random = ThreadLocalRandom.current();
        double v = random.nextDouble(0, 100);

        boolean win = (v <= chance);

        if (win)
            // This will just return the same value because it would be initial + initial, aka double
            return initial;
        else
            return 0;
    }

    private void attemptPotionKillUpgrade(KitPvPPlayer player) {
        ClanManager clanManager = ClanManager.getInstance();
        Clan clan = clanManager.getClanByPlayer(player.getPlayer());
        if (clan == null)
            return;

        boolean has = clan.hasUpgrade(ClanUpgrade.POTS_ON_KILL);
        if (!has)
            return;

        int level = clan.getUpgradeLevel(ClanUpgrade.POTS_ON_KILL);
        double chance = level * 10;
        ThreadLocalRandom random = ThreadLocalRandom.current();
        double v = random.nextDouble(0, 100);

        boolean win = (v <= chance);

        if (win) {
            int amount = level * 2;

            ItemStack potion = new ItemBuilder(Material.POTION).setDurability((short) 16421).build();
            for (int i = 0; i < amount; i++) {
                player.getPlayer().getInventory().addItem(potion);
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player))
            return;

        if (event.getDamager() instanceof Arrow) {
            boolean deflect = attemptDeflection((Player) event.getEntity());
            if (deflect) {
                event.setCancelled(true);
                return;
            }
        }

        if (!(event.getDamager() instanceof Player))
            return;

        Player attacker = (Player) event.getDamager();
        Player victim = (Player) event.getEntity();

        Clan clan = ClanManager.getInstance().getClanByPlayer(attacker);
        if (clan == null)
            return;

        if (clan.getMembers().containsKey(victim.getUniqueId()))
            event.setDamage(event.getDamage() / 2);

    }

    private boolean attemptDeflection(Player player) {
        ClanManager clanManager = ClanManager.getInstance();
        Clan clan = clanManager.getClanByPlayer(player.getPlayer());
        if (clan == null)
            return false;

        boolean has = clan.hasUpgrade(ClanUpgrade.DEFLECTION);
        if (!has)
            return false;

        int level = clan.getUpgradeLevel(ClanUpgrade.DEFLECTION);
        double chance = level * 10;
        ThreadLocalRandom random = ThreadLocalRandom.current();
        double v = random.nextDouble(0, 100);

        return (v <= chance);
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

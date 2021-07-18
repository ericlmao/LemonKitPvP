package dev.negativekb.lemonkitpvp.commands.stats;

import dev.negativekb.lemonkitpvp.core.api.command.Command;
import dev.negativekb.lemonkitpvp.core.api.command.SubCommand;
import dev.negativekb.lemonkitpvp.core.api.command.annotation.CommandInfo;
import dev.negativekb.lemonkitpvp.core.data.KitPvPPlayerManager;
import dev.negativekb.lemonkitpvp.core.structure.KitPvPPlayer;
import dev.negativekb.lemonkitpvp.core.util.Message;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@CommandInfo(name = "setstats", permission = "kitpvp.admin")
public class CommandSetStats extends Command {

    private final KitPvPPlayerManager manager;

    public CommandSetStats() {
        manager = KitPvPPlayerManager.getInstance();

        addSubCommands(
                new Kills(),
                new Deaths(),
                new Coins(),
                new KillStreak(),
                new BestKillStreak(),
                new Level(),
                new XP()
        );

        setTabComplete((sender, args) -> {
            if (args.length == 1) {
                List<String> list = Arrays.asList("kills", "deaths", "coins", "killstreak", "ks", "bestkillstreak", "bestks", "level", "xp");
                String lastWord = args[args.length - 1];
                return list.stream().filter(s -> StringUtil.startsWithIgnoreCase(s, lastWord)).collect(Collectors.toList());
            }

            if (args.length == 2) {
                String lastWord = args[args.length - 1];
                Player senderPlayer = sender instanceof Player ? (Player) sender : null;
                ArrayList<String> matchedPlayers = new ArrayList<String>();
                for (Player player : sender.getServer().getOnlinePlayers()) {
                    String name = player.getName();
                    if ((sender == null || senderPlayer.canSee(player)) && StringUtil.startsWithIgnoreCase(name, lastWord)) {
                        matchedPlayers.add(name);
                    }
                }

                matchedPlayers.sort(String.CASE_INSENSITIVE_ORDER);
                return matchedPlayers;
            }

            if (args.length == 3) {
                ThreadLocalRandom random = ThreadLocalRandom.current();
                int i = random.nextInt(0, 1000);
                return Collections.singletonList(String.valueOf(i));
            }

            return null;
        });
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {

    }


    @CommandInfo(name = "kills", permission = "kitpvp.admin")
    private class Kills extends SubCommand {

        @Override
        public void runCommand(CommandSender sender, String[] args) {
            if (args.length < 2) {
                new Message("&c/setstats <stat> <player> <value>").send(sender);
                return;
            }

            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
            KitPvPPlayer player = manager.getPlayerByOfflinePlayer(target);
            if (player == null) {
                new Message("&cThis player has not played before!").send(sender);
                return;
            }

            double amount;
            try {
                amount = Double.parseDouble(args[1]);
            } catch (Exception e) {
                new Message("&c&lERROR! &7Please use a valid number!").send(sender);
                return;
            }

            String stat = "Kills";
            player.setKills((int) amount);
            System.out.println(sender.getName() + " has set " + target.getName() + "'s " + stat + " to " + amount);
            new Message("&aYou have set &e" + target.getName() + "&a's &6" + stat + " &ato &6&l" + amount + "&a!").send(sender);
        }
    }


    @CommandInfo(name = "deaths", permission = "kitpvp.admin")
    private class Deaths extends SubCommand {

        @Override
        public void runCommand(CommandSender sender, String[] args) {
            if (args.length < 2) {
                new Message("&c/setstats <stat> <player> <value>").send(sender);
                return;
            }

            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
            KitPvPPlayer player = manager.getPlayerByOfflinePlayer(target);
            if (player == null) {
                new Message("&cThis player has not played before!").send(sender);
                return;
            }

            double amount;
            try {
                amount = Double.parseDouble(args[1]);
            } catch (Exception e) {
                new Message("&c&lERROR! &7Please use a valid number!").send(sender);
                return;
            }

            String stat = "Deaths";
            player.setDeaths((int) amount);
            System.out.println(sender.getName() + " has set " + target.getName() + "'s " + stat + " to " + amount);
            new Message("&aYou have set &e" + target.getName() + "&a's &6" + stat + " &ato &6&l" + amount + "&a!").send(sender);
        }
    }

    @CommandInfo(name = "coins", permission = "kitpvp.admin")
    private class Coins extends SubCommand {

        @Override
        public void runCommand(CommandSender sender, String[] args) {
            if (args.length < 2) {
                new Message("&c/setstats <stat> <player> <value>").send(sender);
                return;
            }

            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
            KitPvPPlayer player = manager.getPlayerByOfflinePlayer(target);
            if (player == null) {
                new Message("&cThis player has not played before!").send(sender);
                return;
            }

            double amount;
            try {
                amount = Double.parseDouble(args[1]);
            } catch (Exception e) {
                new Message("&c&lERROR! &7Please use a valid number!").send(sender);
                return;
            }

            String stat = "Coins";
            player.setCoins(amount);
            System.out.println(sender.getName() + " has set " + target.getName() + "'s " + stat + " to " + amount);
            new Message("&aYou have set &e" + target.getName() + "&a's &6" + stat + " &ato &6&l" + amount + "&a!").send(sender);
        }
    }

    @CommandInfo(name = "killstreak", aliases = {"ks"}, permission = "kitpvp.admin")
    private class KillStreak extends SubCommand {

        @Override
        public void runCommand(CommandSender sender, String[] args) {
            if (args.length < 2) {
                new Message("&c/setstats <stat> <player> <value>").send(sender);
                return;
            }

            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
            KitPvPPlayer player = manager.getPlayerByOfflinePlayer(target);
            if (player == null) {
                new Message("&cThis player has not played before!").send(sender);
                return;
            }

            double amount;
            try {
                amount = Double.parseDouble(args[1]);
            } catch (Exception e) {
                new Message("&c&lERROR! &7Please use a valid number!").send(sender);
                return;
            }

            String stat = "KillStreak";
            player.setKillStreak((int) amount);
            System.out.println(sender.getName() + " has set " + target.getName() + "'s " + stat + " to " + amount);
            new Message("&aYou have set &e" + target.getName() + "&a's &6" + stat + " &ato &6&l" + amount + "&a!").send(sender);
        }
    }

    @CommandInfo(name = "bestkillstreak", aliases = {"bestks"}, permission = "kitpvp.admin")
    private class BestKillStreak extends SubCommand {

        @Override
        public void runCommand(CommandSender sender, String[] args) {
            if (args.length < 2) {
                new Message("&c/setstats <stat> <player> <value>").send(sender);
                return;
            }

            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
            KitPvPPlayer player = manager.getPlayerByOfflinePlayer(target);
            if (player == null) {
                new Message("&cThis player has not played before!").send(sender);
                return;
            }

            double amount;
            try {
                amount = Double.parseDouble(args[1]);
            } catch (Exception e) {
                new Message("&c&lERROR! &7Please use a valid number!").send(sender);
                return;
            }

            String stat = "Best KillStreak";
            player.setBestKillStreak((int) amount);
            System.out.println(sender.getName() + " has set " + target.getName() + "'s " + stat + " to " + amount);
            new Message("&aYou have set &e" + target.getName() + "&a's &6" + stat + " &ato &6&l" + amount + "&a!").send(sender);
        }
    }

    @CommandInfo(name = "level", permission = "kitpvp.admin")
    private class Level extends SubCommand {

        @Override
        public void runCommand(CommandSender sender, String[] args) {
            if (args.length < 2) {
                new Message("&c/setstats <stat> <player> <value>").send(sender);
                return;
            }

            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
            KitPvPPlayer player = manager.getPlayerByOfflinePlayer(target);
            if (player == null) {
                new Message("&cThis player has not played before!").send(sender);
                return;
            }

            double amount;
            try {
                amount = Double.parseDouble(args[1]);
            } catch (Exception e) {
                new Message("&c&lERROR! &7Please use a valid number!").send(sender);
                return;
            }

            String stat = "Level";
            player.setLevel((int) amount);
            System.out.println(sender.getName() + " has set " + target.getName() + "'s " + stat + " to " + amount);
            new Message("&aYou have set &e" + target.getName() + "&a's &6" + stat + " &ato &6&l" + amount + "&a!").send(sender);
        }
    }

    @CommandInfo(name = "xp", permission = "kitpvp.admin")
    private class XP extends SubCommand {

        @Override
        public void runCommand(CommandSender sender, String[] args) {
            if (args.length < 2) {
                new Message("&c/setstats <stat> <player> <value>").send(sender);
                return;
            }

            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
            KitPvPPlayer player = manager.getPlayerByOfflinePlayer(target);
            if (player == null) {
                new Message("&cThis player has not played before!").send(sender);
                return;
            }

            double amount;
            try {
                amount = Double.parseDouble(args[1]);
            } catch (Exception e) {
                new Message("&c&lERROR! &7Please use a valid number!").send(sender);
                return;
            }

            String stat = "XP";
            player.setXp(amount);
            System.out.println(sender.getName() + " has set " + target.getName() + "'s " + stat + " to " + amount);
            new Message("&aYou have set &e" + target.getName() + "&a's &6" + stat + " &ato &6&l" + amount + "&a!").send(sender);
        }
    }
}

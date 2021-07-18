package dev.negativekb.lemonkitpvp.commands.clans.subcommands.staff;

import dev.negativekb.lemonkitpvp.core.api.command.SubCommand;
import dev.negativekb.lemonkitpvp.core.api.command.annotation.CommandInfo;
import dev.negativekb.lemonkitpvp.core.data.ClanManager;
import dev.negativekb.lemonkitpvp.core.structure.clans.Clan;
import dev.negativekb.lemonkitpvp.core.util.Message;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Member;

@CommandInfo(name = "setstats", permission = "kitpvp.admin")
public class SubSetStats extends SubCommand {

    private final ClanManager manager;
    public SubSetStats() {
        this.manager = ClanManager.getInstance();

        addSubCommands(
                new Kills(),
                new Deaths()
        );
    }

    @Override
    public void runCommand(CommandSender sender, String[] args) {
        new Message("&cInvalid statistic! Try:",
                "&eKills&c, &eDeaths").send(sender);
    }

    @CommandInfo(name = "kills", permission = "kitpvp.admin")
    private class Kills extends SubCommand {

        @Override
        public void runCommand(CommandSender sender, String[] args) {
            if (args.length < 2) {
                new Message("&c/clans setstats <stat type> <clan name> <value>").send(sender);
                return;
            }

            Clan clan = manager.getClanByName(args[0]);
            double amount;
            try {
                amount = Double.parseDouble(args[1]);
            } catch (Exception e) {
                new Message("&c&lERROR! &7Use a valid number!").send(sender);
                return;
            }

            System.out.println("[Force Stats Change] " + sender.getName() +
                    " has changed " + clan.getName() + "'s kills from "
                    + clan.getKills() + " to " + amount);

            clan.setKills((int) amount);
            new Message("&aYou have changed &e" + clan.getName() + "&a's kills to &e%kills%")
                    .replace("%kills%", clan.getKills()).send(sender);

            clan.sendAnnouncement("&7The Clan's kill amount has been forcefully set to &b" + clan.getKills() + "&7.");
        }
    }

    @CommandInfo(name = "deaths", permission = "kitpvp.admin")
    private class Deaths extends SubCommand {

        @Override
        public void runCommand(CommandSender sender, String[] args) {
            if (args.length < 2) {
                new Message("&c/clans setstats <stat type> <clan name> <value>").send(sender);
                return;
            }

            Clan clan = manager.getClanByName(args[0]);
            double amount;
            try {
                amount = Double.parseDouble(args[1]);
            } catch (Exception e) {
                new Message("&c&lERROR! &7Use a valid number!").send(sender);
                return;
            }

            System.out.println("[Force Stats Change] " + sender.getName() +
                    " has changed " + clan.getName() + "'s deaths from "
                    + clan.getDeaths() + " to " + amount);

            clan.setDeaths((int) amount);
            new Message("&aYou have changed &e" + clan.getName() + "&a's deaths to &e%deaths%")
                    .replace("%deaths%", clan.getDeaths()).send(sender);

            clan.sendAnnouncement("&7The Clan's death amount has been forcefully set to &b" + clan.getDeaths() + "&7.");
        }
    }
}

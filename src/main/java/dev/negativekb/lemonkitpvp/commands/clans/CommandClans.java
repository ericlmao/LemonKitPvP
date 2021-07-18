package dev.negativekb.lemonkitpvp.commands.clans;

import dev.negativekb.lemonkitpvp.commands.clans.subcommands.*;
import dev.negativekb.lemonkitpvp.commands.clans.subcommands.invite.SubInvite;
import dev.negativekb.lemonkitpvp.commands.clans.subcommands.invite.SubUnInvite;
import dev.negativekb.lemonkitpvp.commands.clans.subcommands.leader.SubDisband;
import dev.negativekb.lemonkitpvp.commands.clans.subcommands.leader.SubTransfer;
import dev.negativekb.lemonkitpvp.commands.clans.subcommands.staff.SubForceDisband;
import dev.negativekb.lemonkitpvp.commands.clans.subcommands.staff.SubSetStats;
import dev.negativekb.lemonkitpvp.core.api.command.Command;
import dev.negativekb.lemonkitpvp.core.api.command.annotation.CommandInfo;
import dev.negativekb.lemonkitpvp.core.data.ClanManager;
import dev.negativekb.lemonkitpvp.core.structure.clans.Clan;
import dev.negativekb.lemonkitpvp.core.util.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@CommandInfo(name = "clans", aliases = {"clan", "team", "teams", "group", "groups", "faction", "f", "gang", "gangs"})
public class CommandClans extends Command {

    public CommandClans() {
        addSubCommands(
                new SubInvite(),
                new SubUnInvite(),
                new SubCreate(),
                new SubDisband(),
                new SubJoin(),
                new SubLeave(),
                new SubMembers(),
                new SubStats(),
                new SubTransfer(),
                new SubKick(),
                new SubSetRank(),
                // Admin Commands
                new SubForceDisband(),
                new SubSetStats()
        );

        setTabComplete((sender, args) -> {
            if (args.length == 1) {
                List<String> list = new ArrayList<>();

                if (sender.hasPermission("kitpvp.admin"))
                    list.addAll(Arrays.asList("forcedisband", "setstats"));

                list.addAll(Arrays.asList("create", "disband", "join", "leave", "members", "stats", "invite", "uninvite", "transfer", "kick"));

                return list;
            }

            if (is(args[0], "stats", "join", "invite", "uninvite")
                    || (sender.hasPermission("kitpvp.admin") && is(args[0], "forcedisband"))) {
                List<String> list = new ArrayList<>();
                ClanManager.getInstance().getClans().forEach(clan -> list.add(clan.getName()));
                return list;
            }

            if (is(args[0], "kick", "transfer")) {
                Clan clan = ClanManager.getInstance().getClanByPlayer((Player) sender);
                if (clan == null)
                    return null;

                List<String> list = new ArrayList<>();
                clan.getMembers().forEach((uuid, s) -> list.add(Bukkit.getOfflinePlayer(uuid).getName()));
                return list;
            }

            if (is(args[0], "setrank")) {
                if (args.length == 3)
                    return Arrays.asList("Officer", "Mod", "Member", "Recruit");

                Clan clan = ClanManager.getInstance().getClanByPlayer((Player) sender);
                if (clan == null)
                    return null;

                List<String> list = new ArrayList<>();
                clan.getMembers().forEach((uuid, s) -> list.add(Bukkit.getOfflinePlayer(uuid).getName()));
                return list;
            }

            return null;
        });
    }

    private boolean is(String arg, String... possible) {
        String output = Arrays.stream(possible).filter(arg::equalsIgnoreCase).findFirst().orElse(null);
        return output != null;
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        new Message(" ",
                "&b/clan create <name> &7- Create a Clan",
                "&b/clan disband &7- Disband your Clan &9(Clan Leader)",
                "&b/clan join <clan> &7- Join a Clan that you are invited to",
                "&b/clan leave &7- Leave your current Clan",
                "&b/clan members &7- Look at the members of your Clan",
                "&b/clan stats [<clan>] &7- Shows the statistics of a Clan",
                "&b/clan invite <player> &7- Invites a player to the Clan &3(Clan Mod+)",
                "&b/clan uninvite <player> &7- UnInvite a player from the Clan &3(Clan Mod+)",
                "&b/clan transfer <player> &7- Transfer Clan Leader to another player &9(Clan Leader)",
                "&b/clan kick <player> &7- Kick a player from your Clan &3(Clan Mod+)",
                "&b/clan setrank <player> <rank> &7- Set a player's Clan rank &3(Clan Mod+)",
                "&b/clan forcedisband <clan> &7- Forcefully disbands a Clan &c(Admin+)",
                "&b/clan setstats <stat> <clan> <value> &7- Modifies a Clan Statistic &c(Admin+)",
                " "
        ).send(sender);
    }
}

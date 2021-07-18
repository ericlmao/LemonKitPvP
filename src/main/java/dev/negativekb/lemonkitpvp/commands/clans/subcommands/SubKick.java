package dev.negativekb.lemonkitpvp.commands.clans.subcommands;

import dev.negativekb.lemonkitpvp.core.api.command.SubCommand;
import dev.negativekb.lemonkitpvp.core.api.command.annotation.CommandInfo;
import dev.negativekb.lemonkitpvp.core.data.ClanManager;
import dev.negativekb.lemonkitpvp.core.structure.clans.Clan;
import dev.negativekb.lemonkitpvp.core.structure.clans.ClanRank;
import dev.negativekb.lemonkitpvp.core.util.Message;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandInfo(name = "kick", playerOnly = true)
public class SubKick extends SubCommand {

    private final ClanManager manager;

    public SubKick() {
        manager = ClanManager.getInstance();
    }

    @Override
    public void runCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        Clan clan = manager.getClanByPlayer(player);
        if (clan == null) {
            new Message("&cYou are not in a Clan.").send(player);
            return;
        }
        if (args.length == 0) {
            new Message("&c/clan kick <player>").send(player);
            return;
        }
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if (!clan.getMembers().containsKey(target.getUniqueId())) {
            new Message("&cThis player is not a part of this Clan!").send(player);
            return;
        }

        ClanRank rank1 = clan.getMemberRank(player.getUniqueId());
        if (rank1 == ClanRank.RECRUIT || rank1 == ClanRank.MEMBER) {
            new Message("&cYou do not have permission to kick memebrs.").send(player);
            return;
        }
        ClanRank rank2 = clan.getMemberRank(target.getUniqueId());

        if (!rank1.isAbove(rank2)) {
            new Message("&cYou cannot kick &e" + target.getName() + " &cas they out-rank you.").send(player);
            return;
        }

        clan.removeMember(target.getUniqueId());
        clan.sendAnnouncement("&e" + target.getName() + " &7has been kicked from the Clan by &6" + player.getName() + "&7.");
    }
}

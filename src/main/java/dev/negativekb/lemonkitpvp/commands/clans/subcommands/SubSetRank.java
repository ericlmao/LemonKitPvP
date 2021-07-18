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

@CommandInfo(name = "setrank", playerOnly = true)
public class SubSetRank extends SubCommand {

    private final ClanManager clanManager;

    public SubSetRank() {
        this.clanManager = ClanManager.getInstance();
    }

    @Override
    public void runCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        Clan clan = clanManager.getClanByPlayer(player);
        if (clan == null) {
            new Message("&cYou are not in a Clan.").send(player);
            return;
        }

        if (args.length < 2) {
            new Message("&c/clan setrank <player> <rank>").send(player);
            return;
        }

        ClanRank rank = clan.getMemberRank(player.getUniqueId());
        if (rank == ClanRank.MEMBER || rank == ClanRank.RECRUIT) {
            new Message("&cYou do not have permission to set ranks in your Clan").send(player);
            return;
        }
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if (!clan.getMembers().containsKey(target.getUniqueId())) {
            new Message("&cThis player is not in this Clan").send(player);
            return;
        }
        ClanRank targetRank = clan.getMemberRank(target.getUniqueId());
        if (!rank.isAbove(targetRank)) {
            new Message("&cYou cannot modify &e" + target.getName() + "&c's rank as they out-rank you.").send(player);
            return;
        }
        ClanRank rank1 = ClanRank.getByString(args[1]);
        if (rank1.isAbove(rank)) {
            new Message("&cYou cannot set someone's rank to a rank that is above yours.").send(player);
            return;
        }

        if (rank == ClanRank.LEADER && rank1 == ClanRank.LEADER) {
            new Message("&cYou cannot set someone's rank to Leader, transfer the Clan to them by doing /clan transfer <player>").send(player);
            return;
        }

        clan.setMemberRank(target.getUniqueId(), rank1);
        clan.sendAnnouncement("&e" + target.getName() + "&7's rank was updated to &6&l" + rank1.getName() + "&7.");
    }
}

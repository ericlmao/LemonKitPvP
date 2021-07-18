package dev.negativekb.lemonkitpvp.commands.clans.subcommands.leader;

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

@CommandInfo(name = "transfer", playerOnly = true)
public class SubTransfer extends SubCommand {

    private final ClanManager manager;

    public SubTransfer() {
        manager = ClanManager.getInstance();
    }

    @Override
    public void runCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        Clan clan = manager.getClanByPlayer(player);
        if (clan == null || clan.getMemberRank(player.getUniqueId()) != ClanRank.LEADER) {
            new Message("&cYou cannot transfer leadership of your Clan " +
                    "because you are not the Leader or you are not in a Clan.").send(player);
            return;
        }

        if (args.length == 0) {
            new Message("&c/clan transfer <player>").send(player);
            return;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if (!clan.getMembers().containsKey(target.getUniqueId())) {
            new Message("&cThis player is not in the Clan.").send(player);
            return;
        }

        clan.setMemberRank(player.getUniqueId(), ClanRank.OFFICER);
        clan.setMemberRank(target.getUniqueId(), ClanRank.LEADER);

        clan.sendAnnouncement("&e" + player.getName() + " &7has transferred leadership of the Clan to &e" + target.getName() + "&7.");
    }
}

package dev.negativekb.lemonkitpvp.commands.clans.subcommands;

import dev.negativekb.lemonkitpvp.core.api.command.SubCommand;
import dev.negativekb.lemonkitpvp.core.api.command.annotation.CommandInfo;
import dev.negativekb.lemonkitpvp.core.data.ClanManager;
import dev.negativekb.lemonkitpvp.core.structure.clans.Clan;
import dev.negativekb.lemonkitpvp.core.structure.clans.ClanRank;
import dev.negativekb.lemonkitpvp.core.util.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandInfo(name = "leave", playerOnly = true)
public class SubLeave extends SubCommand {

    private final ClanManager manager;
    public SubLeave() {
        manager = ClanManager.getInstance();
    }

    @Override
    public void runCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        Clan clan = manager.getClanByPlayer(player);
        if (clan == null) {
            new Message("&cYou are not in a Clan").send(player);
            return;
        }

        if (clan.getMemberRank(player.getUniqueId()) == ClanRank.LEADER) {
            new Message("&cIn order to leave your Clan, you must transfer leadership to another member or disband the Clan.").send(player);
            return;
        }

        clan.removeMember(player.getUniqueId());
        clan.sendAnnouncement("&e" + player.getName() + " &7has left the Clan.");
    }
}

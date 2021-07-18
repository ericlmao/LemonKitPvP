package dev.negativekb.lemonkitpvp.commands.clans.subcommands;

import dev.negativekb.lemonkitpvp.core.api.command.SubCommand;
import dev.negativekb.lemonkitpvp.core.api.command.annotation.CommandInfo;
import dev.negativekb.lemonkitpvp.core.data.ClanManager;
import dev.negativekb.lemonkitpvp.core.structure.clans.Clan;
import dev.negativekb.lemonkitpvp.core.util.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

@CommandInfo(name = "join", playerOnly = true)
public class SubJoin extends SubCommand {
    private final ClanManager manager;

    public SubJoin() {
        manager = ClanManager.getInstance();
    }

    @Override
    public void runCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (args.length == 0) {
            new Message("&c/clan join <clan name>").send(player);
            return;
        }

        Clan clan = manager.getClanByName(args[0]);
        if (clan == null) {
            new Message("&cThe Clan &e" + args[0] + " &cdoes not exist.").send(player);
            return;
        }

        UUID uuid = player.getUniqueId();
        if (!manager.isInvited(uuid, clan)) {
            new Message("&cYou have not been invited to this Clan.").send(player);
            return;
        }

        clan.addMember(player);
        clan.sendAnnouncement("&e" + player.getName() + " &7has joined the Clan!");
        manager.removeInvite(uuid, clan);
    }
}

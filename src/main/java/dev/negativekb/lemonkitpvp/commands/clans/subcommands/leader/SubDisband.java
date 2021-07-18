package dev.negativekb.lemonkitpvp.commands.clans.subcommands.leader;

import dev.negativekb.lemonkitpvp.core.api.command.SubCommand;
import dev.negativekb.lemonkitpvp.core.api.command.annotation.CommandInfo;
import dev.negativekb.lemonkitpvp.core.data.ClanManager;
import dev.negativekb.lemonkitpvp.core.structure.clans.Clan;
import dev.negativekb.lemonkitpvp.core.structure.clans.ClanRank;
import dev.negativekb.lemonkitpvp.core.util.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandInfo(name = "disband", playerOnly = true)
public class SubDisband extends SubCommand {


    private final ClanManager manager;

    public SubDisband() {
        this.manager = ClanManager.getInstance();
    }

    @Override
    public void runCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (manager.getClanByPlayer(player) == null) {
            new Message("&cYou are not in a Clan!").send(sender);
            return;
        }
        Clan clan = manager.getClanByPlayer(player);
        if (clan.getMemberRank(player.getUniqueId()) != ClanRank.LEADER) {
            new Message("&cOnly the Leader can disband this Clan!").send(sender);
            return;
        }

        new Message("&eThe Clan &6" + clan.getName() + " &ehas been disbanded!").broadcast();
        manager.deleteClan(clan);

    }
}

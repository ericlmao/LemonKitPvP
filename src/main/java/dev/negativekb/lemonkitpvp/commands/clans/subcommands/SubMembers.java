package dev.negativekb.lemonkitpvp.commands.clans.subcommands;

import dev.negativekb.lemonkitpvp.commands.clans.subcommands.menus.ClanMembersMenu;
import dev.negativekb.lemonkitpvp.core.api.command.SubCommand;
import dev.negativekb.lemonkitpvp.core.api.command.annotation.CommandInfo;
import dev.negativekb.lemonkitpvp.core.data.ClanManager;
import dev.negativekb.lemonkitpvp.core.structure.clans.Clan;
import dev.negativekb.lemonkitpvp.core.util.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandInfo(name = "members", playerOnly = true)
public class SubMembers extends SubCommand {

    private final ClanManager clanManager;

    public SubMembers() {
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

        new ClanMembersMenu(player, clan, 1).open(player);
    }
}

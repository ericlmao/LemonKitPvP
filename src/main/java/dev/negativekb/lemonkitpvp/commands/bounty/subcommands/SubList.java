package dev.negativekb.lemonkitpvp.commands.bounty.subcommands;

import dev.negativekb.lemonkitpvp.commands.bounty.subcommands.menu.BountyMenu;
import dev.negativekb.lemonkitpvp.core.api.command.SubCommand;
import dev.negativekb.lemonkitpvp.core.api.command.annotation.CommandInfo;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandInfo(name = "list", playerOnly = true)
public class SubList extends SubCommand {
    @Override
    public void runCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        new BountyMenu(1).open(player);
    }
}

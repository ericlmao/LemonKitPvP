package dev.negativekb.lemonkitpvp.commands.clans.subcommands;

import dev.negativekb.lemonkitpvp.core.api.command.SubCommand;
import dev.negativekb.lemonkitpvp.core.api.command.annotation.CommandInfo;
import dev.negativekb.lemonkitpvp.core.data.ClanManager;
import dev.negativekb.lemonkitpvp.core.structure.clans.Clan;
import dev.negativekb.lemonkitpvp.core.util.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandInfo(name = "create", playerOnly = true)
public class SubCreate extends SubCommand {

    private final ClanManager manager;

    public SubCreate() {
        this.manager = ClanManager.getInstance();
    }

    @Override
    public void runCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (manager.getClanByPlayer(player) != null) {
            new Message("&cYou are already in a Clan!").send(sender);
            return;
        }
        if (args.length == 0) {
            new Message("&c/clan create <name>").send(sender);
            return;
        }
        Clan clan = manager.getClanByName(args[0]);
        if (clan != null) {
            new Message("&cThe Clan with the name &e" + args[0] + " &calready exists!").send(sender);
            return;
        }

        if (args[0].length() > 16) {
            new Message("&cYou cannot have a Clan name longer than 16 characters.").send(sender);
            return;
        }

        manager.createClan(args[0], player);
        new Message("&6" + player.getName() + " &ehas created the Clan &6" + args[0] + "&e!").broadcast();
    }

}

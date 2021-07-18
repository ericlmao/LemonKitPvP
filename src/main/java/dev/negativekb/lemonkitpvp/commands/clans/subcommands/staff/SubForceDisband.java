package dev.negativekb.lemonkitpvp.commands.clans.subcommands.staff;

import dev.negativekb.lemonkitpvp.core.api.command.SubCommand;
import dev.negativekb.lemonkitpvp.core.api.command.annotation.CommandInfo;
import dev.negativekb.lemonkitpvp.core.data.ClanManager;
import dev.negativekb.lemonkitpvp.core.structure.clans.Clan;
import dev.negativekb.lemonkitpvp.core.util.Message;
import org.bukkit.command.CommandSender;

@CommandInfo(name = "forcedisband", permission = "kitpvp.admin")
public class SubForceDisband extends SubCommand {
    @Override
    public void runCommand(CommandSender sender, String[] args) {
        if (args.length == 0) {
            new Message("&c/clans forcedisband <clan name>").send(sender);
            return;
        }

        ClanManager manager = ClanManager.getInstance();
        Clan clan = manager.getClanByName(args[0]);
        if (clan == null) {
            new Message("&cThe clan &e" + args[0] + " &cdoes not exist").send(sender);
            return;
        }

        boolean silent = args[1] != null && args[1].equalsIgnoreCase("-s");

        if (!silent)
            new Message("&6&l" + clan.getName() + " &ehas been disbanded by &c&l" + sender.getName());

        System.out.println("[Force Disband] " + clan.getName() + " was disbanded by " + sender.getName());
        manager.deleteClan(clan);
    }
}

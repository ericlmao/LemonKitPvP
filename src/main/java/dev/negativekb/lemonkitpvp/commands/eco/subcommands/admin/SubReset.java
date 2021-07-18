package dev.negativekb.lemonkitpvp.commands.eco.subcommands.admin;

import dev.negativekb.lemonkitpvp.core.api.command.SubCommand;
import dev.negativekb.lemonkitpvp.core.api.command.annotation.CommandInfo;
import dev.negativekb.lemonkitpvp.core.data.KitPvPPlayerManager;
import dev.negativekb.lemonkitpvp.core.structure.KitPvPPlayer;
import dev.negativekb.lemonkitpvp.core.util.Message;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

@CommandInfo(name = "reset", permission = "kitpvp.admin")
public class SubReset extends SubCommand {
    @Override
    public void runCommand(CommandSender sender, String[] args) {
        if (args.length < 1) {
            new Message("/coins reset <player>").send(sender);
            return;
        }

        String p = args[0];
        OfflinePlayer target = Bukkit.getOfflinePlayer(p);

        KitPvPPlayer stats = KitPvPPlayerManager.getInstance().getPlayerByOfflinePlayer(target);
        if (stats == null) {
            new Message("&cThis player has never joined!").send(sender);
            return;
        }

        stats.setCoins(0);
    }
}

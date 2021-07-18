package dev.negativekb.lemonkitpvp.commands.eco.subcommands.player;

import dev.negativekb.lemonkitpvp.core.api.command.SubCommand;
import dev.negativekb.lemonkitpvp.core.api.command.annotation.CommandInfo;
import dev.negativekb.lemonkitpvp.core.data.KitPvPPlayerManager;
import dev.negativekb.lemonkitpvp.core.structure.KitPvPPlayer;
import dev.negativekb.lemonkitpvp.core.util.Message;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandInfo(name = "pay", playerOnly = true)
public class SubPay extends SubCommand {
    @Override
    public void runCommand(CommandSender sender, String[] args) {
        if (args.length < 2) {
            new Message("/coins pay <player> <amount>").send(sender);
            return;
        }

        String p = args[0];
        if (p.equalsIgnoreCase(sender.getName())) {
            new Message("&cYou cannot pay yourself!").send(sender);
            return;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(p);

        String a = args[1];
        double amount;
        try {
            amount = Double.parseDouble(a);
        } catch (Exception e) {
            new Message("&c&lERROR! &7Please use a valid number").send(sender);
            return;
        }

        KitPvPPlayer player = KitPvPPlayerManager.getInstance().getPlayerByPlayer((Player) sender);
        double current = player.getCoins();
        if (current < amount) {
            new Message("&cYou cannot afford to pay this amount!").send(sender);
            return;
        }

        KitPvPPlayer stats = KitPvPPlayerManager.getInstance().getPlayerByOfflinePlayer(target);
        if (stats == null) {
            new Message("&cThis player has never joined!").send(sender);
            return;
        }
        stats.setCoins(stats.getCoins() + amount);
        player.setCoins(player.getCoins() - amount);
    }
}

package dev.negativekb.lemonkitpvp.commands.bounty.subcommands;

import dev.negativekb.lemonkitpvp.core.api.command.SubCommand;
import dev.negativekb.lemonkitpvp.core.api.command.annotation.CommandInfo;
import dev.negativekb.lemonkitpvp.core.data.KitPvPPlayerManager;
import dev.negativekb.lemonkitpvp.core.structure.KitPvPPlayer;
import dev.negativekb.lemonkitpvp.core.util.Message;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandInfo(name = "set", aliases = {"add"}, playerOnly = true)
public class SubSet extends SubCommand {
    @Override
    public void runCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (args.length < 2) {
            new Message("&c/bounty set <player> <amount>").send(player);
            return;
        }
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        KitPvPPlayer stats = KitPvPPlayerManager.getInstance().getPlayerByOfflinePlayer(target);
        if (stats == null) {
            new Message("&cThis player has not joined before!").send(player);
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(args[1]);

            if (amount < 100) {
                new Message("&c&lERROR! &7Please use a number greater than or equal to 100!").send(player);
                return;
            }
        } catch (Exception e) {
            new Message("&c&lERROR! &7Please use a valid number!").send(player);
            return;
        }

        KitPvPPlayer playerStats = KitPvPPlayerManager.getInstance().getPlayerByPlayer(player);
        double coins = playerStats.getCoins();
        if (coins < amount) {
            new Message("&cYou cannot afford to pay this amount!").send(sender);
            return;
        }

        playerStats.setCoins(playerStats.getCoins() - amount);
        stats.setBounty(stats.getBounty() + amount);

        new Message("&eA bounty of &6&l" + amount + " coins &ewas placed on &c&l" + target.getName() + "&e!").broadcast();
    }
}

package dev.negativekb.lemonkitpvp.commands.eco;

import dev.negativekb.lemonkitpvp.LemonKitPvP;
import dev.negativekb.lemonkitpvp.commands.eco.subcommands.admin.SubGive;
import dev.negativekb.lemonkitpvp.commands.eco.subcommands.admin.SubRemove;
import dev.negativekb.lemonkitpvp.commands.eco.subcommands.admin.SubReset;
import dev.negativekb.lemonkitpvp.commands.eco.subcommands.admin.SubSet;
import dev.negativekb.lemonkitpvp.commands.eco.subcommands.player.SubPay;
import dev.negativekb.lemonkitpvp.core.api.command.Command;
import dev.negativekb.lemonkitpvp.core.api.command.annotation.CommandInfo;
import dev.negativekb.lemonkitpvp.core.data.KitPvPPlayerManager;
import dev.negativekb.lemonkitpvp.core.structure.KitPvPPlayer;
import dev.negativekb.lemonkitpvp.core.util.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@CommandInfo(name = "coins", aliases = {"money", "balance", "tokens"})
public class CommandCoins extends Command {

    public CommandCoins() {
        addSubCommands(
                new SubGive(),
                new SubRemove(),
                new SubReset(),
                new SubSet()
        );

        boolean payCommandEnabled = LemonKitPvP.getInstance().getConfig().getBoolean("enable-pay-command", true);
        if (payCommandEnabled)
            addSubCommands(new SubPay());
        setTabComplete((sender, args) -> {
            List<String> list = new ArrayList<>();
            if (payCommandEnabled)
                list.add("pay");

            if (sender.hasPermission("kitpvp.admin"))
                list.addAll(Arrays.asList("give", "add", "take", "remove", "set", "reset"));

            return list;
        });
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            new Message(
                    "&c/coins set <player> <amount>",
                    "&c/coins give/add <player> <amount>",
                    "&c/coins remove/take <player> <amount>",
                    "&c/coins reset <player>"
            ).send(sender);
            return;
        }

        KitPvPPlayerManager manager = KitPvPPlayerManager.getInstance();

        Player player = (Player) sender;
        if (args.length == 0) {
            KitPvPPlayer stats = manager.getPlayerByPlayer(player);
            double coins = stats.getCoins();
            new Message("&eYou have &6&l%amount% &ecoins!")
                    .replace("%amount%", coins).send(player);
            return;
        }

        String p = args[0];
        KitPvPPlayer stats = manager.getPlayerByOfflinePlayer(Bukkit.getOfflinePlayer(p));
        if (stats == null) {
            new Message("&cThis player has never joined!").send(sender);
            return;
        }

        double coins = stats.getCoins();
        new Message("&6%player% &ehas &6&l%amount% &ecoins!")
                .replace("%player%", p).replace("%amount%", coins).send(player);

    }
}

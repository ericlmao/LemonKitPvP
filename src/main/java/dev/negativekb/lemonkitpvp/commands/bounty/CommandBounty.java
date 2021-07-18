package dev.negativekb.lemonkitpvp.commands.bounty;

import dev.negativekb.lemonkitpvp.commands.bounty.subcommands.SubList;
import dev.negativekb.lemonkitpvp.commands.bounty.subcommands.SubSet;
import dev.negativekb.lemonkitpvp.core.api.command.Command;
import dev.negativekb.lemonkitpvp.core.api.command.annotation.CommandInfo;
import dev.negativekb.lemonkitpvp.core.util.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

@CommandInfo(name = "bounty", playerOnly = true)
public class CommandBounty extends Command {

    public CommandBounty() {
        addSubCommands(
                new SubSet(),
                new SubList()
        );

        setTabComplete((sender, args) -> {
            if (args.length == 1) {
                return Arrays.asList("set", "list");
            }

            if (args[0].equalsIgnoreCase("set")) {
                if (args.length == 3) {
                    ThreadLocalRandom random = ThreadLocalRandom.current();
                    int i = random.nextInt(100, 1000);
                    return Collections.singletonList(String.valueOf(i));
                }
                return null;
            }
            return null;
        });
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        new Message(" ",
                "&c/bounty set <player> <amount> &7- Set a bounty on a player",
                "&c/bounty list &7- View all current bounties"
        ).send(sender);
    }
}

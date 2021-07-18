package dev.negativekb.lemonkitpvp.commands.kit;

import dev.negativekb.lemonkitpvp.commands.kit.menu.KitMenu;
import dev.negativekb.lemonkitpvp.core.api.command.Command;
import dev.negativekb.lemonkitpvp.core.api.command.annotation.CommandInfo;
import dev.negativekb.lemonkitpvp.core.kit.Kit;
import dev.negativekb.lemonkitpvp.core.kit.KitManager;
import dev.negativekb.lemonkitpvp.core.util.Message;
import dev.negativekb.lemonkitpvp.core.util.UtilPlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@CommandInfo(name = "kit", playerOnly = true)
public class CommandKit extends Command {

    public CommandKit() {

        setTabComplete((sender, args) -> {
            List<String> names = new ArrayList<>();
            KitManager.getInstance().getKits().forEach((type, kit) -> names.add(type.getAlias()));
            return names;
        });
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (!UtilPlayer.isInSpawn(player)) {
            new Message("&c&lYou must be in spawn in order to use this command!").send(sender);
            return;
        }

        if (args.length == 0) {
            new KitMenu().open(player);
            return;
        }

        Kit kit = KitManager.getInstance().getKitByString(args[0]);
        if (kit == null) {
            new Message("&cInvalid kit!").send(sender);
            return;
        }

        kit.attemptApply((Player) sender);
    }
}

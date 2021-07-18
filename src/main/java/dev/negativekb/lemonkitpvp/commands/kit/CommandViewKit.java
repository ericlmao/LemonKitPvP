package dev.negativekb.lemonkitpvp.commands.kit;

import dev.negativekb.lemonkitpvp.commands.kit.menu.ViewKitMenu;
import dev.negativekb.lemonkitpvp.core.api.command.Command;
import dev.negativekb.lemonkitpvp.core.api.command.annotation.CommandInfo;
import dev.negativekb.lemonkitpvp.core.kit.Kit;
import dev.negativekb.lemonkitpvp.core.kit.KitManager;
import dev.negativekb.lemonkitpvp.core.util.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@CommandInfo(name = "viewkit", playerOnly = true)
public class CommandViewKit extends Command {

    public CommandViewKit() {

        setTabComplete((sender, args) -> {
            List<String> names = new ArrayList<>();
            KitManager.getInstance().getKits().forEach((type, kit) -> names.add(type.getAlias()));
            return names;
        });
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (args.length == 0) {
            new Message("&cInvalid arguments").send(sender);
            return;
        }

        Kit kit = KitManager.getInstance().getKitByString(args[0]);
        if (kit == null) {
            new Message("&cInvalid Kit!").send(sender);
            return;
        }

        new ViewKitMenu(kit).open((Player) sender);
    }
}

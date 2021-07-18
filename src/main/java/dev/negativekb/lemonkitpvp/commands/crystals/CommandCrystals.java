package dev.negativekb.lemonkitpvp.commands.crystals;

import dev.negativekb.lemonkitpvp.commands.crystals.menu.CrystalMenu;
import dev.negativekb.lemonkitpvp.core.api.command.Command;
import dev.negativekb.lemonkitpvp.core.api.command.SubCommand;
import dev.negativekb.lemonkitpvp.core.api.command.annotation.CommandInfo;
import dev.negativekb.lemonkitpvp.core.util.ItemBuilder;
import dev.negativekb.lemonkitpvp.core.util.Message;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

@CommandInfo(name = "crystal", aliases = {"crystals", "refillcrystal", "refillcrystals"}, permission = "kitpvp.admin")
public class CommandCrystals extends Command {

    public CommandCrystals() {
        addSubCommands(
                new List(),
                new Give()
        );
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        new Message("&c/crystal give",
                "&c/crystal list")
                .send(sender);
    }

    @CommandInfo(name = "list", playerOnly = true)
    private class List extends SubCommand {

        @Override
        public void runCommand(CommandSender sender, String[] args) {
            new CrystalMenu(1).open((Player) sender);
        }
    }

    @CommandInfo(name = "give", playerOnly = true)
    private class Give extends SubCommand {

        @Override
        public void runCommand(CommandSender sender, String[] args) {
            Player player = (Player) sender;
            ItemStack spawner = new ItemBuilder(Material.BEDROCK).addEnchant(Enchantment.DURABILITY, 1).addItemFlag(ItemFlag.HIDE_ENCHANTS)
                    .setName("&d&lRefill Crystal &7(Right Click)").build();
            player.getInventory().addItem(spawner);
        }
    }
}

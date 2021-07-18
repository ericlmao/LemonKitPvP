package dev.negativekb.lemonkitpvp.commands.kit.menu;

import dev.negativekb.lemonkitpvp.core.api.gui.GUI;
import dev.negativekb.lemonkitpvp.core.kit.Kit;
import dev.negativekb.lemonkitpvp.core.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class ViewKitMenu extends GUI {
    public ViewKitMenu(Kit kit) {
        super("&6&lViewing Kit &e&l" + kit.getKitType().getAlias(), 6);
        HashMap<Integer, ItemStack> contents = kit.kitContents();

        ItemStack potion = new ItemBuilder(Material.POTION).setDurability((short) 16421).build();
        for (int i = 0; i < (9 * 4); i++) {
            setItem(i, potion);
        }
        contents.forEach(this::setItem);

        ItemStack filler = new ItemBuilder(Material.STAINED_GLASS_PANE).setDurability((short) 15).setName("    ").build();
        for (int i = (9 * 4); i < (9 * 6); i++) {
            setItem(i, filler);
        }

        setItem(45, kit.getHelmet());
        setItem(46, kit.getChestplate());
        setItem(47, kit.getLeggings());
        setItem(48, kit.getBoots());
    }
}

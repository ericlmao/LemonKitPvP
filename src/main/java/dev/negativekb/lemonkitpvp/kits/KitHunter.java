package dev.negativekb.lemonkitpvp.kits;

import dev.negativekb.lemonkitpvp.core.kit.Kit;
import dev.negativekb.lemonkitpvp.core.kit.KitType;
import dev.negativekb.lemonkitpvp.core.util.ItemBuilder;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.HashMap;
import java.util.List;

public class KitHunter extends Kit {
    public KitHunter() {
        super(KitType.HUNTER);
    }

    @Override
    public ItemStack getHelmet() {
        return new ItemBuilder(Material.LEATHER_HELMET)
                .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3)
                .setInfinityDurability()
                .setLeatherArmorColor(Color.BLACK)
                .build();
    }

    @Override
    public ItemStack getChestplate() {
        return new ItemBuilder(Material.LEATHER_CHESTPLATE)
                .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5)
                .setInfinityDurability()
                .setLeatherArmorColor(Color.GREEN)
                .build();
    }

    @Override
    public ItemStack getLeggings() {
        return new ItemBuilder(Material.CHAINMAIL_LEGGINGS)
                .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
                .setInfinityDurability()
                .build();
    }

    @Override
    public ItemStack getBoots() {
        return new ItemBuilder(Material.CHAINMAIL_BOOTS)
                .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2)
                .setInfinityDurability()
                .build();
    }

    @Override
    public HashMap<Integer, ItemStack> kitContents() {
        HashMap<Integer, ItemStack> contents = new HashMap<>();
        ItemStack sword = new ItemBuilder(Material.STONE_SWORD).addEnchant(Enchantment.DAMAGE_ALL, 1)
                .setInfinityDurability()
                .build();

        ItemStack bow = new ItemBuilder(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE, 2).addEnchant(Enchantment.ARROW_INFINITE, 1)
                .setInfinityDurability().build();

        ItemStack arrow = new ItemBuilder(Material.ARROW, 1).build();
        ItemStack food = new ItemBuilder(Material.COOKED_BEEF, 64).build();

        contents.put(0, sword);
        contents.put(1, bow);
        contents.put(7, arrow);
        contents.put(8, food);

        return contents;
    }

    @Override
    public List<PotionEffect> getPotionEffects() {
        return null;
    }

    @Override
    public ItemStack getIcon() {
        return new ItemBuilder(Material.MONSTER_EGG)
                .setDurability((short) 51)
                .setName("&f&lHunter")
                .addLoreLine(" ")
                .addLoreLine("&9&lArmor")
                .addLoreLine("&8&l■ &fLeather Helmet &b(Protection 3)")
                .addLoreLine("&8&l■ &fLeather Chestplate &b(Protection 5)")
                .addLoreLine("&8&l■ &fChain Leggings &b(Protection 1)")
                .addLoreLine("&8&l■ &fChain Boots &b(Protection 2)")
                .addLoreLine(" ")
                .addLoreLine("&9&lWeapons")
                .addLoreLine("&8&l■ &fStone Sword &b(Sharpness 1)")
                .addLoreLine("&8&l■ &fBow &b(Power 2, Infinity 1)")
                .addLoreLine(" ")
                .addLoreLine("&9&lExtra Items")
                .addLoreLine("&8&l■ &f64 Steak")
                .addLoreLine("&8&l■ &f1 Arrow")
                .addLoreLine(" ")
                .build();
    }
}

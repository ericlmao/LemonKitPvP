package dev.negativekb.lemonkitpvp.kits;

import dev.negativekb.lemonkitpvp.core.kit.Kit;
import dev.negativekb.lemonkitpvp.core.kit.KitType;
import dev.negativekb.lemonkitpvp.core.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.HashMap;
import java.util.List;

public class KitGladiator extends Kit {
    public KitGladiator() {
        super(KitType.GLADIATOR);
    }

    @Override
    public ItemStack getHelmet() {
        return new ItemBuilder(Material.IRON_HELMET)
                .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
                .setInfinityDurability()
                .build();
    }

    @Override
    public ItemStack getChestplate() {
        return new ItemBuilder(Material.DIAMOND_CHESTPLATE)
                .setInfinityDurability()
                .build();
    }

    @Override
    public ItemStack getLeggings() {
        return new ItemBuilder(Material.IRON_LEGGINGS)
                .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2)
                .setInfinityDurability()
                .build();
    }

    @Override
    public ItemStack getBoots() {
        return new ItemBuilder(Material.DIAMOND_BOOTS)
                .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
                .setInfinityDurability()
                .build();
    }

    @Override
    public HashMap<Integer, ItemStack> kitContents() {
        HashMap<Integer, ItemStack> contents = new HashMap<>();
        ItemStack sword = new ItemBuilder(Material.IRON_SWORD).addEnchant(Enchantment.DAMAGE_ALL, 1).setInfinityDurability().build();
        ItemStack food = new ItemBuilder(Material.COOKED_BEEF, 64).build();
        contents.put(0, sword);
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
                .setDurability((short) 57)
                .setName("&d&lGladiator")
                .addLoreLine(" ")
                .addLoreLine("&9&lArmor")
                .addLoreLine("&8&l■ &fIron Helmet &b(Protection 1)")
                .addLoreLine("&8&l■ &fDiamond Chestplate")
                .addLoreLine("&8&l■ &fIron Leggings &b(Protection 2)")
                .addLoreLine("&8&l■ &fDiamond Boots &b(Protection 1)")
                .addLoreLine(" ")
                .addLoreLine("&9&lWeapons")
                .addLoreLine("&8&l■ &fIron Sword &b(Sharpness 1)")
                .addLoreLine(" ")
                .addLoreLine("&9&lExtra Items")
                .addLoreLine("&8&l■ &f64 Steak")
                .addLoreLine(" ")
                .build();
    }
}

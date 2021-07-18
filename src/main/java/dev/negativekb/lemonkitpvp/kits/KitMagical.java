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

public class KitMagical extends Kit {

    public KitMagical() {
        super(KitType.MAGICAL);
    }

    @Override
    public ItemStack getHelmet() {
        return new ItemBuilder(Material.LEATHER_HELMET)
                .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5)
                .setInfinityDurability()
                .setLeatherArmorColor(Color.PURPLE)
                .build();
    }

    @Override
    public ItemStack getChestplate() {
        return new ItemBuilder(Material.CHAINMAIL_CHESTPLATE)
                .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
                .setInfinityDurability()
                .build();
    }

    @Override
    public ItemStack getLeggings() {
        return new ItemBuilder(Material.IRON_LEGGINGS)
                .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
                .setInfinityDurability()
                .build();
    }

    @Override
    public ItemStack getBoots() {
        return new ItemBuilder(Material.DIAMOND_BOOTS)
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
                .setDurability((short) 66)
                .setName("&a&lMagical")
                .addLoreLine(" ")
                .addLoreLine("&9&lArmor")
                .addLoreLine("&8&l■ &fLeather Helmet &b(Protection 5)")
                .addLoreLine("&8&l■ &fChain Chestplate &b(Protection 1)")
                .addLoreLine("&8&l■ &fIron Leggings &b(Protection 1)")
                .addLoreLine("&8&l■ &fDiamond Boots")
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

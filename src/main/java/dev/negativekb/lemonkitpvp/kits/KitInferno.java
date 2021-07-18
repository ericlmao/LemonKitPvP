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

public class KitInferno extends Kit {

    public KitInferno() {
        super(KitType.INFERNO);
    }

    @Override
    public ItemStack getHelmet() {
        return new ItemBuilder(Material.LEATHER_HELMET)
                .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
                .setInfinityDurability()
                .setLeatherArmorColor(Color.YELLOW)
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
        return new ItemBuilder(Material.GOLD_LEGGINGS)
                .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2)
                .setInfinityDurability()
                .build();
    }

    @Override
    public ItemStack getBoots() {
        return new ItemBuilder(Material.LEATHER_BOOTS)
                .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
                .setInfinityDurability()
                .setLeatherArmorColor(Color.ORANGE)
                .build();
    }

    @Override
    public HashMap<Integer, ItemStack> kitContents() {
        HashMap<Integer, ItemStack> contents = new HashMap<>();
        ItemStack sword = new ItemBuilder(Material.WOOD_SWORD).addEnchant(Enchantment.DAMAGE_ALL, 5)
                .addEnchant(Enchantment.FIRE_ASPECT, 2)
                .setInfinityDurability()
                .build();

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
                .setDurability((short) 62)
                .setName("&6&lInferno")
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

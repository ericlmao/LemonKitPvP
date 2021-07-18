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

public class KitBeam extends Kit {

    public KitBeam() {
        super(KitType.BEAM);
    }

    @Override
    public ItemStack getHelmet() {
        return new ItemBuilder(Material.DIAMOND_HELMET)
                .setInfinityDurability()
                .build();
    }

    @Override
    public ItemStack getChestplate() {
        return new ItemBuilder(Material.LEATHER_CHESTPLATE)
                .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5)
                .setInfinityDurability()
                .setLeatherArmorColor(Color.ORANGE)
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
        return new ItemBuilder(Material.GOLD_BOOTS)
                .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
                .setInfinityDurability()
                .build();
    }

    @Override
    public HashMap<Integer, ItemStack> kitContents() {
        HashMap<Integer, ItemStack> contents = new HashMap<>();
        ItemStack sword = new ItemBuilder(Material.IRON_SWORD)
                .addEnchant(Enchantment.DAMAGE_ALL, 2)
                .addEnchant(Enchantment.KNOCKBACK, 1)
                .setInfinityDurability()
                .build();

        ItemStack pearls = new ItemBuilder(Material.ENDER_PEARL, 4).build();

        ItemStack food = new ItemBuilder(Material.COOKED_BEEF, 64).build();

        contents.put(0, sword);
        contents.put(1, pearls);
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
                .setDurability((short) 58)
                .setName("&5&lBeam")
                .addLoreLine(" ")
                .addLoreLine("&9&lArmor")
                .addLoreLine("&8&l■ &fDiamond Helmet")
                .addLoreLine("&8&l■ &fLeather Chestplate &b(Protection 5)")
                .addLoreLine("&8&l■ &fChain Leggings &b(Protection 1)")
                .addLoreLine("&8&l■ &fGolden Boots &b(Protection 1)")
                .addLoreLine(" ")
                .addLoreLine("&9&lWeapons")
                .addLoreLine("&8&l■ &fIron Sword &b(Sharpness 2)")
                .addLoreLine(" ")
                .addLoreLine("&9&lExtra Items")
                .addLoreLine("&8&l■ &f4 Ender Pearls")
                .addLoreLine("&8&l■ &f64 Steak")
                .addLoreLine(" ")
                .build();
    }
}

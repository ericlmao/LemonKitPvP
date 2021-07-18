package dev.negativekb.lemonkitpvp.core.kit;

import dev.negativekb.lemonkitpvp.LemonKitPvP;
import dev.negativekb.lemonkitpvp.core.util.ItemBuilder;
import dev.negativekb.lemonkitpvp.core.util.UtilPlayer;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;

import java.util.HashMap;
import java.util.List;

public abstract class Kit implements Listener {

    @Getter
    private final KitType kitType;

    public Kit(KitType type) {
        this.kitType = type;

        Bukkit.getPluginManager().registerEvents(this, LemonKitPvP.getInstance());
    }

    /**
     * @return - Returns helmet itemstack
     */
    public abstract ItemStack getHelmet();

    /**
     * @return - Returns chestplate itemstack
     */
    public abstract ItemStack getChestplate();

    /**
     * @return - Returns leggings itemstack
     */
    public abstract ItemStack getLeggings();

    /**
     * @return - Returns boots itemstack
     */
    public abstract ItemStack getBoots();

    /**
     * @return - Returns hashmap of kit contents, Integer is the index of the soon-to-be inventory
     */
    public abstract HashMap<Integer, ItemStack> kitContents();

    /**
     * @return - Returns a list of potion effects
     */
    public abstract List<PotionEffect> getPotionEffects();

    /**
     * @return - Returns icon of the kit for the Kit Selector
     */
    public abstract ItemStack getIcon();

    public void applyKit(Player player) {
        UtilPlayer.reset(player);

        PlayerInventory inv = player.getInventory();

        if (getHelmet().getType() != Material.AIR || getHelmet() != null)
            inv.setHelmet(getHelmet());

        if (getChestplate().getType() != Material.AIR || getChestplate() != null)
            inv.setChestplate(getChestplate());

        if (getLeggings().getType() != Material.AIR || getLeggings() != null)
            inv.setLeggings(getLeggings());

        if (getBoots().getType() != Material.AIR || getBoots() != null)
            inv.setBoots(getBoots());


        ItemStack healing = new ItemBuilder(Material.POTION).setDurability((short) 16421).build();

        for (int i = 0; i < 36; i++) {
            inv.setItem(i, healing);
        }

        if (!kitContents().isEmpty() || kitContents() != null)
            kitContents().forEach(inv::setItem);

        if (getPotionEffects() != null)
            getPotionEffects().forEach(player::addPotionEffect);
    }

    public boolean attemptApply(Player player) {
        // Checks if the user is in spawn, if so, apply kit.
        // If not, return false.
        if (UtilPlayer.isInSpawn(player)) {
            applyKit(player);
            return true;
        }
        return false;
    }
}

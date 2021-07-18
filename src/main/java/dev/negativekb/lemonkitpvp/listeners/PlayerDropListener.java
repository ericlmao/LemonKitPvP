package dev.negativekb.lemonkitpvp.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class PlayerDropListener implements Listener {

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        Item item = event.getItemDrop();
        ItemStack itemStack = item.getItemStack();
        Material type = itemStack.getType();
        PlayerInventory inventory = player.getInventory();
        switch (type) {

            case STONE_SWORD:
            case DIAMOND_SWORD:
            case GOLD_SWORD:
            case IRON_SWORD:
            case WOOD_SWORD:
            case DIAMOND_AXE:
            case GOLD_AXE:
            case IRON_AXE:
            case STONE_AXE:
            case WOOD_AXE:
            case BOW:
            case ARROW:
            case ENDER_PEARL:
            case FISHING_ROD:
            case POTION:
            case COOKED_BEEF: {
                event.setCancelled(true);
                player.updateInventory();
                return;
            }
            // Helmets
            case DIAMOND_HELMET:
            case CHAINMAIL_HELMET:
            case GOLD_HELMET:
            case IRON_HELMET:
            case LEATHER_HELMET: {
                inventory.setHelmet(itemStack);
                player.updateInventory();
                item.remove();
                return;
            }
            // Chestplates
            case CHAINMAIL_CHESTPLATE:
            case DIAMOND_CHESTPLATE:
            case GOLD_CHESTPLATE:
            case IRON_CHESTPLATE:
            case LEATHER_CHESTPLATE: {
                inventory.setChestplate(itemStack);
                player.updateInventory();
                item.remove();
                return;
            }
            // Leggings
            case LEATHER_LEGGINGS:
            case CHAINMAIL_LEGGINGS:
            case DIAMOND_LEGGINGS:
            case GOLD_LEGGINGS:
            case IRON_LEGGINGS: {
                inventory.setLeggings(itemStack);
                player.updateInventory();
                item.remove();
                return;
            }
            // Boots
            case CHAINMAIL_BOOTS:
            case DIAMOND_BOOTS:
            case GOLD_BOOTS:
            case IRON_BOOTS:
            case LEATHER_BOOTS: {
                inventory.setBoots(itemStack);
                player.updateInventory();
                item.remove();
            }
        }
    }
}

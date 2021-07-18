package dev.negativekb.lemonkitpvp.listeners;

import dev.negativekb.lemonkitpvp.LemonKitPvP;
import dev.negativekb.lemonkitpvp.core.data.CrystalManager;
import dev.negativekb.lemonkitpvp.core.util.ItemBuilder;
import dev.negativekb.lemonkitpvp.core.util.Message;
import dev.negativekb.lemonkitpvp.core.util.UtilPlayer;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RefillCrystalListener implements Listener {

    @Getter
    @Setter
    private Map<UUID, Long> map = new HashMap<>();

    public RefillCrystalListener() {

        new Timer().runTaskTimer(LemonKitPvP.getInstance(), 0, 20);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getItemInHand();
        if (item.getType() != Material.BEDROCK)
            return;

        if (!item.containsEnchantment(Enchantment.DURABILITY))
            return;

        event.setCancelled(true);
        CrystalManager instance = CrystalManager.getInstance();
        instance.createRefillCrystal(event.getBlock().getLocation());
        new Message("&aSuccessfully placed a RefillCrystal!").send(player);
        instance.removeCrystals();
        instance.spawnCrystals();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void interact(PlayerInteractAtEntityEvent event) {
        if (!(event.getRightClicked() instanceof EnderCrystal))
            return;

        event.setCancelled(true);
        Player player = event.getPlayer();
        if (isOnCooldown(player)) {
            long current = System.currentTimeMillis();
            long cd = map.get(player.getUniqueId());
            int sec = (int) ((cd - current) / 1000L);
            new Message("&cYou must wait &e" + sec + " seconds &cbefore using this!").send(player);
            return;
        }

        ItemStack pot = new ItemBuilder(Material.POTION).setDurability((short) 16421).build();
        UtilPlayer.fillInventory(player, pot);
        new Message("&6&lREFILL! &eYou have refilled on potions! &7(60 second cooldown)").send(player);

        map.put(player.getUniqueId(), (System.currentTimeMillis() + (1000L * 60)));
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onHit(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof EnderCrystal))
            return;

        if (!(event.getDamager() instanceof Player))
            return;

        event.setCancelled(true);
        Player player = (Player) event.getDamager();
        if (isOnCooldown(player)) {
            long current = System.currentTimeMillis();
            long cd = map.get(player.getUniqueId());
            int sec = (int) ((cd - current) / 1000L);
            new Message("&cYou must wait &e" + sec + " seconds &cbefore using this!").send(player);
            return;
        }

        ItemStack pot = new ItemBuilder(Material.POTION).setDurability((short) 16421).build();
        UtilPlayer.fillInventory(player, pot);
        new Message("&6&lREFILL! &eYou have refilled on potions! &7(60 second cooldown)").send(player);

        map.put(player.getUniqueId(), (System.currentTimeMillis() + (1000L * 60)));
    }

    private boolean isOnCooldown(Player player) {
        return map.containsKey(player.getUniqueId());
    }

    private class Timer extends BukkitRunnable {

        @Override
        public void run() {
            Map<UUID, Long> map = getMap();
            if (map.isEmpty())
                return;

            ArrayList<UUID> toRemove = new ArrayList<>();
            map.entrySet()
                    .stream()
                    .filter(uuidLongEntry -> System.currentTimeMillis() >= uuidLongEntry.getValue())
                    .forEach(uuidLongEntry -> toRemove.add(uuidLongEntry.getKey()));

            toRemove.forEach(map::remove);

            setMap(map);

        }
    }

}

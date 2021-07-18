package dev.negativekb.lemonkitpvp.commands.kit.menu;

import dev.negativekb.lemonkitpvp.core.api.gui.GUI;
import dev.negativekb.lemonkitpvp.core.kit.Kit;
import dev.negativekb.lemonkitpvp.core.kit.KitManager;
import dev.negativekb.lemonkitpvp.core.kit.KitType;
import dev.negativekb.lemonkitpvp.core.util.ItemBuilder;
import dev.negativekb.lemonkitpvp.core.util.Message;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;

public class KitMenu extends GUI {
    public KitMenu() {
        super("&6&lAll Kits &7(Free)", 3);

        ItemStack filler = new ItemBuilder(Material.STAINED_GLASS_PANE).setDurability((short) 15)
                .setName("        ").build();
        for (int i = 0; i < (3 * 9); i++) {
            setItem(i, filler);
        }

        KitManager manager = KitManager.getInstance();
        Kit inferno = manager.getKitByType(KitType.INFERNO);

        Message message = new Message("&aYou have successfully applied kit &e%kit%&a!");

        setItemClickEvent(9, inferno.getIcon(), (player, event) -> {
            inferno.attemptApply(player);
            message.replace("%kit%", inferno.getKitType().getAlias()).send(player);
            player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);
        });

        Kit hunter = manager.getKitByType(KitType.HUNTER);
        setItemClickEvent(11, hunter.getIcon(), (player, event) -> {
            hunter.attemptApply(player);
            message.replace("%kit%", hunter.getKitType().getAlias()).send(player);
            player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);
        });

        Kit gladiator = manager.getKitByType(KitType.GLADIATOR);
        setItemClickEvent(13, gladiator.getIcon(), (player, event) -> {
            gladiator.attemptApply(player);
            message.replace("%kit%", gladiator.getKitType().getAlias()).send(player);
            player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);
        });

        Kit magical = manager.getKitByType(KitType.MAGICAL);
        setItemClickEvent(15, magical.getIcon(), (player, event) -> {
            magical.attemptApply(player);
            message.replace("%kit%", magical.getKitType().getAlias()).send(player);
            player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);
        });

        Kit beam = manager.getKitByType(KitType.BEAM);
        setItemClickEvent(17, beam.getIcon(), (player, event) -> {
            beam.attemptApply(player);
            message.replace("%kit%", beam.getKitType().getAlias()).send(player);
            player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);
        });
    }
}

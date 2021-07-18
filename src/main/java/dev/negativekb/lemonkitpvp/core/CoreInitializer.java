package dev.negativekb.lemonkitpvp.core;

import dev.negativekb.lemonkitpvp.commands.bounty.CommandBounty;
import dev.negativekb.lemonkitpvp.commands.clans.CommandClans;
import dev.negativekb.lemonkitpvp.commands.crystals.CommandCrystals;
import dev.negativekb.lemonkitpvp.commands.eco.CommandCoins;
import dev.negativekb.lemonkitpvp.commands.kit.CommandKit;
import dev.negativekb.lemonkitpvp.commands.kit.CommandViewKit;
import dev.negativekb.lemonkitpvp.commands.kit.CommandWhatKit;
import dev.negativekb.lemonkitpvp.commands.stats.CommandSetStats;
import dev.negativekb.lemonkitpvp.commands.stats.CommandStats;
import dev.negativekb.lemonkitpvp.core.api.gui.GUIListener;
import dev.negativekb.lemonkitpvp.core.data.ClanManager;
import dev.negativekb.lemonkitpvp.core.data.CrystalManager;
import dev.negativekb.lemonkitpvp.core.data.KitPvPPlayerManager;
import dev.negativekb.lemonkitpvp.core.impl.PlaceholderAPIImpl;
import dev.negativekb.lemonkitpvp.core.kit.KitManager;
import dev.negativekb.lemonkitpvp.core.managers.KitPvPLevelManager;
import org.bukkit.Bukkit;

public class CoreInitializer {

    public CoreInitializer() {
        new GUIListener();
        new KitPvPLevelManager();
        new KitPvPPlayerManager();
        new ClanManager();
        new CrystalManager();
        new KitManager();

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PlaceholderAPIImpl().register();
            Bukkit.getLogger().info("[Lemon KitPvP] Successfully hooked into PlaceholderAPI");
        }

        registerCommands();
    }

    private void registerCommands() {
        new CommandBounty();
        new CommandClans();
        new CommandCoins();
        new CommandKit();
        new CommandViewKit();
        new CommandWhatKit();
        new CommandSetStats();
        new CommandStats();
        new CommandCrystals();
    }
}

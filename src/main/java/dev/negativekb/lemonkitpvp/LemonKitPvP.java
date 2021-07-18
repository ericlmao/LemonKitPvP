package dev.negativekb.lemonkitpvp;

import dev.negativekb.lemonkitpvp.core.CoreInitializer;
import dev.negativekb.lemonkitpvp.core.data.ClanManager;
import dev.negativekb.lemonkitpvp.core.data.CrystalManager;
import dev.negativekb.lemonkitpvp.core.data.KitPvPPlayerManager;
import dev.negativekb.lemonkitpvp.listeners.*;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.Arrays;

public final class LemonKitPvP extends JavaPlugin {

    @Getter
    private static LemonKitPvP instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        new CoreInitializer();

        registerListeners(
                new PlayerDeathListener(),
                new PlayerDropListener(),
                new PlayerJoinListener(),
                new PlayerChatListener(),
                new RefillCrystalListener()
        );
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        try {
            KitPvPPlayerManager.getInstance().save();
            ClanManager.getInstance().save();

            CrystalManager crystalManager = CrystalManager.getInstance();
            crystalManager.save();
            crystalManager.removeCrystals();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void registerListeners(Listener... listeners) {
        PluginManager pluginManager = Bukkit.getPluginManager();
        Arrays.stream(listeners).forEach(listener -> pluginManager.registerEvents(listener, this));
    }
}

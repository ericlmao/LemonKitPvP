package dev.negativekb.lemonkitpvp;

import dev.negativekb.lemonkitpvp.core.CoreInitializer;
import dev.negativekb.lemonkitpvp.core.data.ClanManager;
import dev.negativekb.lemonkitpvp.core.data.KitPvPPlayerManager;
import dev.negativekb.lemonkitpvp.listeners.PlayerChatListener;
import dev.negativekb.lemonkitpvp.listeners.PlayerDeathListener;
import dev.negativekb.lemonkitpvp.listeners.PlayerDropListener;
import dev.negativekb.lemonkitpvp.listeners.PlayerJoinListener;
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
                new PlayerChatListener()
        );
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        try {
            KitPvPPlayerManager.getInstance().save();
            ClanManager.getInstance().save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void registerListeners(Listener... listeners) {
        PluginManager pluginManager = Bukkit.getPluginManager();
        Arrays.stream(listeners).forEach(listener -> pluginManager.registerEvents(listener, this));
    }
}

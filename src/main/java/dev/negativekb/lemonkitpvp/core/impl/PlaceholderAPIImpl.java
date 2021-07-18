package dev.negativekb.lemonkitpvp.core.impl;

import dev.negativekb.lemonkitpvp.LemonKitPvP;
import dev.negativekb.lemonkitpvp.core.data.KitPvPPlayerManager;
import dev.negativekb.lemonkitpvp.core.managers.KitPvPLevelManager;
import dev.negativekb.lemonkitpvp.core.structure.KitPvPPlayer;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

public class PlaceholderAPIImpl extends PlaceholderExpansion {
    private final LemonKitPvP plugin;

    public PlaceholderAPIImpl() {
        plugin = LemonKitPvP.getInstance();
    }

    /**
     * Because this is an internal class,
     * you must override this method to let PlaceholderAPI know to not unregister your expansion class when
     * PlaceholderAPI is reloaded
     *
     * @return true to persist through reloads
     */
    @Override
    public boolean persist() {
        return true;
    }

    /**
     * Because this is a internal class, this check is not needed
     * and we can simply return {@code true}
     *
     * @return Always true since it's an internal class.
     */
    @Override
    public boolean canRegister() {
        return true;
    }

    /**
     * The name of the person who created this expansion should go here.
     * <br>For convienience do we return the author from the plugin.yml
     *
     * @return The name of the author as a String.
     */
    @Override
    public @NotNull String getAuthor() {
        return plugin.getDescription().getAuthors().toString();
    }

    /**
     * The placeholder identifier should go here.
     * <br>This is what tells PlaceholderAPI to call our onRequest
     * method to obtain a value if a placeholder starts with our
     * identifier.
     * <br>The identifier has to be lowercase and can't contain _ or %
     *
     * @return The identifier in {@code %<identifier>_<value>%} as String.
     */
    @Override
    public @NotNull String getIdentifier() {
        return "kitpvp";
    }

    /**
     * This is the version of the expansion.
     * <br>You don't have to use numbers, since it is set as a String.
     * <p>
     * For convienience do we return the version from the plugin.yml
     *
     * @return The version as a String.
     */
    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    /**
     * This is the method called when a placeholder with our identifier
     * is found and needs a value.
     * <br>We specify the value identifier in this method.
     * <br>Since version 2.9.1 can you use OfflinePlayers in your requests.
     *
     * @param identifier A String containing the identifier/value.
     * @return possibly-null String of the requested identifier.
     */
    @Override
    public String onPlaceholderRequest(Player player, @NotNull String identifier) {

        if (player == null) {
            return "";
        }

        String[] paths = identifier.split("_");
        DecimalFormat df = new DecimalFormat("###,###,###,###,###.##");
        KitPvPPlayer stats = KitPvPPlayerManager.getInstance().getPlayerByPlayer(player);

        if (paths[0].equalsIgnoreCase("kills"))
            return df.format(stats.getKills());

        if (paths[0].equalsIgnoreCase("deaths"))
            return df.format(stats.getDeaths());

        if (paths[0].equalsIgnoreCase("kdr"))
            return df.format(stats.getKDR());

        if (paths[0].equalsIgnoreCase("level"))
            return df.format(stats.getLevel());

        if (paths[0].equalsIgnoreCase("xp"))
            return df.format(stats.getXp());

        if (paths[0].equalsIgnoreCase("requiredxp"))
            return df.format(KitPvPLevelManager.getInstance().getRequiredExperience(stats.getLevel() + 1));

        if (paths[0].equalsIgnoreCase("bounty"))
            return df.format(stats.getBounty());

        if (paths[0].equalsIgnoreCase("killstreak"))
            return df.format(stats.getKillStreak());

        if (paths[0].equalsIgnoreCase("best-killstreak"))
            return df.format(stats.getBestKillStreak());

        if (paths[0].equalsIgnoreCase("coins"))
            return df.format(stats.getCoins());

        if (paths[0].equalsIgnoreCase("kit"))
            return (stats.getKit() == null ? "None" : stats.getKit());

        return null;
    }
}

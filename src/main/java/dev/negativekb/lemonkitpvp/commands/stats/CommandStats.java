package dev.negativekb.lemonkitpvp.commands.stats;

import dev.negativekb.lemonkitpvp.core.api.command.Command;
import dev.negativekb.lemonkitpvp.core.api.command.annotation.CommandInfo;
import dev.negativekb.lemonkitpvp.core.data.KitPvPPlayerManager;
import dev.negativekb.lemonkitpvp.core.managers.KitPvPLevelManager;
import dev.negativekb.lemonkitpvp.core.structure.KitPvPPlayer;
import dev.negativekb.lemonkitpvp.core.util.Message;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandInfo(name = "stats", aliases = {"viewstats", "seestats"}, playerOnly = true)
public class CommandStats extends Command {
    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (args.length == 0) {
            sendStats(player, player);
            return;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        sendStats(player, target);
    }

    private void sendStats(Player player, OfflinePlayer target) {
        KitPvPPlayer stats = KitPvPPlayerManager.getInstance().getPlayerByOfflinePlayer(target);
        if (stats == null) {
            new Message("&cThis player has never joined!").send(player);
            return;
        }

        int kills = stats.getKills();
        int deaths = stats.getDeaths();
        double kdr = stats.getKDR();
        double bounty = stats.getBounty();
        int killStreak = stats.getKillStreak();
        int bestKillStreak = stats.getBestKillStreak();
        int level = stats.getLevel();
        double xp = stats.getXp();
        String kit = stats.getKit();
        double coins = stats.getCoins();

        new Message(
                "&f&m------------------------------",
                " ",
                "&e&lStats of &6&l" + target.getName(),
                " ",
                "&eKills: &f%kills%",
                "&eDeaths: &f%deaths%",
                "&eKDR: &f%kdr%",
                " ",
                "&eCoins: &f%coins%",
                "&eBounty: &f%bounty%",
                " ",
                "&eKillStreak: &f%killstreak%",
                "&eBest KillStreak: &f%best-killstreak%",
                " ",
                "&eLevel: &f%level%",
                "&eExperience: &f%xp%&8/&f%required%",
                " ",
                "&eCurrent Kit: &f%kit%",
                " ",
                "&f&m------------------------------"
        )
        .replace("%kills%", kills)
        .replace("%deaths%", deaths)
        .replace("%kdr%", kdr)
        .replace("%coins%", coins)
        .replace("%bounty%", (bounty == 0 ? "No Bounty" : bounty))
        .replace("%killstreak%", killStreak)
        .replace("%best-killstreak%", bestKillStreak)
        .replace("%level%", level)
        .replace("%xp%", xp)
        .replace("%required%", KitPvPLevelManager.getInstance().getRequiredExperience((level + 1)))
        .replace("%kit%", (kit == null ? "None" : kit))
        .send(player);
    }
}

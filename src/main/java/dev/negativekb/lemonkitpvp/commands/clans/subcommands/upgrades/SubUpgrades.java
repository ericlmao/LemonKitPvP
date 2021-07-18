package dev.negativekb.lemonkitpvp.commands.clans.subcommands.upgrades;

import dev.negativekb.lemonkitpvp.core.api.command.SubCommand;
import dev.negativekb.lemonkitpvp.core.api.command.annotation.CommandInfo;
import dev.negativekb.lemonkitpvp.core.data.ClanManager;
import dev.negativekb.lemonkitpvp.core.data.KitPvPPlayerManager;
import dev.negativekb.lemonkitpvp.core.util.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandInfo(name = "upgrades", playerOnly = true)
public class SubUpgrades extends SubCommand {

    private final ClanManager manager;

    public SubUpgrades() {
        this.manager = ClanManager.getInstance();
    }

    @Override
    public void runCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (manager.getClanByPlayer(player) == null) {
            new Message("&cYou are not in a Clan!").send(sender);
            return;
        }

        new ClanUpgradeMenu(KitPvPPlayerManager.getInstance().getPlayerByPlayer(player)
                , manager.getClanByPlayer(player)).open(player);
    }
}

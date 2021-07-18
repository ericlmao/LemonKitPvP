package dev.negativekb.lemonkitpvp.commands.clans.subcommands;

import dev.negativekb.lemonkitpvp.core.api.command.SubCommand;
import dev.negativekb.lemonkitpvp.core.api.command.annotation.CommandInfo;
import dev.negativekb.lemonkitpvp.core.data.ClanManager;
import dev.negativekb.lemonkitpvp.core.structure.clans.Clan;
import dev.negativekb.lemonkitpvp.core.util.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandInfo(name = "stats", playerOnly = true)
public class SubStats extends SubCommand {

    private final ClanManager clanManager;

    public SubStats() {
        this.clanManager = ClanManager.getInstance();
    }


    @Override
    public void runCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        Clan clan = null;
        if (args.length == 0)
            clan = clanManager.getClanByPlayer(player);

        if (args.length > 0)
            clan = clanManager.getClanByName(args[0]);

        if (clan == null) {
            new Message("&cCould not find Clan.").send(player);
            return;
        }

        new Message(
                "&f&m------------------------------",
                " ",
                "&e&lClan Stats of &6&l" + clan.getName(),
                " ",
                "&eKills: &f%kills%",
                "&eDeaths: &f%deaths%",
                "&eKDR: &f%kdr%",
                " ",
                "&f&m------------------------------"
        )
        .replace("%kills%", clan.getKills())
        .replace("%deaths%", clan.getDeaths())
        .replace("%kdr%", (double) clan.getKills() / (double) clan.getDeaths())
        .send(player);
    }

}

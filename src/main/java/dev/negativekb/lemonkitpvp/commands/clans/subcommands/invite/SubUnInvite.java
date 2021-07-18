package dev.negativekb.lemonkitpvp.commands.clans.subcommands.invite;

import dev.negativekb.lemonkitpvp.core.api.command.SubCommand;
import dev.negativekb.lemonkitpvp.core.api.command.annotation.CommandInfo;
import dev.negativekb.lemonkitpvp.core.data.ClanManager;
import dev.negativekb.lemonkitpvp.core.structure.clans.Clan;
import dev.negativekb.lemonkitpvp.core.structure.clans.ClanRank;
import dev.negativekb.lemonkitpvp.core.util.Message;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandInfo(name = "uninvite", playerOnly = true)
public class SubUnInvite extends SubCommand {

    private final ClanManager clanManager;

    public SubUnInvite() {
        this.clanManager = ClanManager.getInstance();
    }

    @Override
    public void runCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (!qualify(player)) {
            new Message("&cYou do not meet qualifications to invite someone to your Clan.",
                    "&f* &cYou do not have a Clan",
                    "&f* &cYou are not a Mod or above")
                    .send(player);
            return;
        }

        if (args.length == 0) {
            new Message("/clan uninvite <player>").send(player);
            return;
        }
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);

        Clan clan = clanManager.getClanByPlayer(player);
        if (!clanManager.isInvited(target.getUniqueId(), clan)) {
            new Message("&cThis person has not been invited to your Clan!").send(player);
            return;
        }

        if (clan.getMembers().containsKey(target.getUniqueId())) {
            new Message("&cThis person is already a part of your Clan!").send(player);
            return;
        }

        if (clanManager.getClanByOfflinePlayer(target) != null) {
            new Message("&cThis person is already in a Clan!").send(player);
            return;
        }

        clanManager.removeInvite(target.getUniqueId(), clan);

        clan.sendAnnouncement("&e" + player.getName() + " &7has removed &b" + target.getName() + "&7's invite to the Clan!");
    }

    private boolean qualify(Player player) {
        Clan clan = clanManager.getClanByPlayer(player);
        if (clan == null)
            return false;

        ClanRank rank = clan.getMemberRank(player.getUniqueId());
        // Kinda dirty code, could find a better way to do this maybe
        // Required to be Mod+
        return rank != null && rank != ClanRank.RECRUIT && rank != ClanRank.MEMBER;

        // Any other circumstance needed?
    }
}

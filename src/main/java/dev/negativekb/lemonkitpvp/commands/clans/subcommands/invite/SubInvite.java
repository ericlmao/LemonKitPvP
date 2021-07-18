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

@CommandInfo(name = "invite", playerOnly = true)
public class SubInvite extends SubCommand {

    private final ClanManager clanManager;

    public SubInvite() {
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
            new Message("/clan invite <player>").send(player);
            return;
        }
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);

        Clan clan = clanManager.getClanByPlayer(player);
        if (clan.getMembers().containsKey(target.getUniqueId())) {
            new Message("&cThis person is already a part of your Clan!").send(player);
            return;
        }

        if (clanManager.getClanByOfflinePlayer(target) != null) {
            new Message("&cThis person is already in a Clan!").send(player);
            return;
        }

        if (clanManager.isInvited(target.getUniqueId(), clan)) {
            new Message("&cThis person has already been invited to your Clan!").send(player);
            return;
        }

        clanManager.invite(target.getUniqueId(), clan);
        if (target.isOnline())
            new Message("&aYou have been invited to the Clan &6&l" + clan.getName() + " &aby &e" + player.getName() + "&a.").send((Player) target);

        clan.sendAnnouncement("&e" + player.getName() + " &7has invited &b" + target.getName() + " &7to the Clan!");
    }

    private boolean qualify(Player player) {
        Clan clan = clanManager.getClanByPlayer(player);
        if (clan == null)
            return false;

        ClanRank rank = clan.getMemberRank(player.getUniqueId());
        // Kinda dirty code, could find a better way to do this maybe
        // Required to be Mod+
        if (rank == null || rank == ClanRank.RECRUIT || rank == ClanRank.MEMBER)
            return false;

        // Any other circumstance needed?
        return true;
    }
}

package joserodpt.realmurdermystery.plugin.commands;

/*
 *   _____            _ __  __               _           __  __           _                  
 *  |  __ \          | |  \/  |             | |         |  \/  |         | |                 
 *  | |__) |___  __ _| | \  / |_   _ _ __ __| | ___ _ __| \  / |_   _ ___| |_ ___ _ __ _   _ 
 *  |  _  // _ \/ _` | | |\/| | | | | '__/ _` |/ _ \ '__| |\/| | | | / __| __/ _ \ '__| | | |
 *  | | \ \  __/ (_| | | |  | | |_| | | | (_| |  __/ |  | |  | | |_| \__ \ ||  __/ |  | |_| |
 *  |_|  \_\___|\__,_|_|_|  |_|\__,_|_|  \__,_|\___|_|  |_|  |_|\__, |___/\__\___|_|   \__, |
 *                                                               __/ |                  __/ |
 *                                                              |___/                  |___/ 
 * Licensed under the MIT License
 * @author José Rodrigues © 2024-2025
 * @link https://github.com/joserodpt/RealMurderMystery
 */

import joserodpt.realskywars.api.RealSkywarsAPI;
import joserodpt.realskywars.api.config.TranslatableLine;
import joserodpt.realskywars.api.player.RSWPlayer;
import joserodpt.realskywars.api.utils.Text;
import me.mattstudios.mf.annotations.Alias;
import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Default;
import me.mattstudios.mf.annotations.Permission;
import me.mattstudios.mf.annotations.SubCommand;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

@Command("party")
@Alias({"p", "festa", "f", "swp", "rswparty"})
public class PartyCMD extends CommandBase {

    public RealSkywarsAPI rs;
    private final String onlyPlayer = "[RealSkywars] Only players can run this command.";

    public PartyCMD(RealSkywarsAPI rs) {
        this.rs = rs;
    }

    @Default
    @SuppressWarnings("unused")
    public void defaultCommand(final CommandSender commandSender) {
        if (commandSender instanceof Player) {
            Text.sendList(commandSender, Arrays.asList(rs.getLanguageManagerAPI().getPrefix(), " &3/party create", " &3/party disband", " &3/party invite <player>", " &3/party accept <player>", " &3/party kick <player>", " &3/party leave"));
        } else {
            commandSender.sendMessage(onlyPlayer);
        }
    }

    @SubCommand("create")
    @Permission("rsw.party.owner")
    @SuppressWarnings("unused")
    public void createcmd(final CommandSender commandSender) {
        if (commandSender instanceof Player) {
            RSWPlayer p = rs.getPlayerManagerAPI().getPlayer(((Player) commandSender));
            if (!p.hasParty()) {
                p.createParty();
                TranslatableLine.PARTY_CREATED.send(p, true);
            } else {
                TranslatableLine.PARTY_ALREADYCREATED.send(p, true);
            }
        } else {
            commandSender.sendMessage(onlyPlayer);
        }
    }

    @SubCommand("disband")
    @Permission("rsw.party.owner")
    @SuppressWarnings("unused")
    public void disbandcmd(final CommandSender commandSender) {
        if (commandSender instanceof Player) {
            RSWPlayer p = rs.getPlayerManagerAPI().getPlayer(((Player) commandSender));
            if (p.hasParty()) {
                if (p.getParty().isOwner(p)) {
                    p.disbandParty();
                } else {
                    TranslatableLine.PARTY_NOT_OWNER.send(p, true);
                }
            } else {
                TranslatableLine.PARTY_NOTINPARTY.send(p, true);
            }
        } else {
            commandSender.sendMessage(onlyPlayer);
        }
    }

    @SubCommand("invite")
    @Permission("rsw.party.invite")
    @SuppressWarnings("unused")
    public void invitecmd(final CommandSender commandSender, final Player player) {
        if (commandSender instanceof Player) {
            RSWPlayer p = rs.getPlayerManagerAPI().getPlayer(((Player) commandSender));
            if (player != null) {
                rs.getPartiesManagerAPI().sendInvite(p, rs.getPlayerManagerAPI().getPlayer(player));
            } else {
                TranslatableLine.NO_PLAYER_FOUND.send(p, true);
            }
        } else {
            commandSender.sendMessage(onlyPlayer);
        }
    }

    @SubCommand("accept")
    @Permission("rsw.party.accept")
    @SuppressWarnings("unused")
    public void acceptcmd(final CommandSender commandSender) {
        if (commandSender instanceof Player) {
            RSWPlayer p = rs.getPlayerManagerAPI().getPlayer(((Player) commandSender));
            if (!p.hasParty()) {
                if (rs.getPartiesManagerAPI().hasInvite(p)) {
                    rs.getPartiesManagerAPI().acceptInvite(p);
                } else {
                    TranslatableLine.PARTY_INVITENOTFOUND.send(p, true);
                }
            } else {
                TranslatableLine.PARTY_ALREADYIN.send(p, true);
            }
        } else {
            commandSender.sendMessage(onlyPlayer);
        }
    }

    @SubCommand("kick")
    @Permission("rsw.party.owner")
    @SuppressWarnings("unused")
    public void kickcmd(final CommandSender commandSender, final Player player) {
        if (commandSender instanceof Player) {
            RSWPlayer p = rs.getPlayerManagerAPI().getPlayer(((Player) commandSender));
            if (p.hasParty()) {
                if (p.getParty().isOwner(p)) {
                    if (player != null && p.getPlayer() != player) {
                        p.getParty().kick(rs.getPlayerManagerAPI().getPlayer(player));
                    } else {
                        TranslatableLine.NO_PLAYER_FOUND.send(p, true);
                    }
                } else {
                    TranslatableLine.PARTY_NOT_OWNER.send(p, true);
                }
            } else {
                TranslatableLine.PARTY_NOTINPARTY.send(p, true);
            }
        } else {
            commandSender.sendMessage(onlyPlayer);
        }
    }

    @SubCommand("leave")
    @Permission("rsw.party.leave")
    @SuppressWarnings("unused")
    public void leavecmd(final CommandSender commandSender) {
        if (commandSender instanceof Player) {
            RSWPlayer p = rs.getPlayerManagerAPI().getPlayer(((Player) commandSender));
            if (p.hasParty()) {
                if (p.getParty().isOwner(p)) {
                    p.disbandParty();
                } else {
                    p.getParty().playerLeave(p);
                }
            } else {
                TranslatableLine.PARTY_NOTINPARTY.send(p, true);
            }
        } else {
            commandSender.sendMessage(onlyPlayer);
        }
    }
}
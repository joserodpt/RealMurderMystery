package joserodpt.realmurdermystery.api.map.modes.teams;

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

import joserodpt.realskywars.api.cages.RSWCage;
import joserodpt.realskywars.api.cages.RSWTeamCage;
import joserodpt.realmurdermystery.api.config.TranslatableLine;
import joserodpt.realmurdermystery.api.player.RSWPlayer;
import joserodpt.realmurdermystery.api.utils.Text;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class RSWTeam {

    private final int id;
    private final int maxMembers;
    private final RSWTeamCage tc;
    private final List<RSWPlayer> members = new ArrayList<>();
    private Boolean eliminated = false, playing = false;

    public RSWTeam(int i, int maxMemb, Location c) {
        this.id = i;
        this.tc = new RSWTeamCage(i, c.getBlockX(), c.getBlockY(), c.getBlockZ());
        this.maxMembers = maxMemb;
    }

    public void addPlayer(RSWPlayer p) {
        this.members.forEach(rswPlayer -> rswPlayer.sendMessage(TranslatableLine.TEAM_BROADCAST_JOIN.get(p, true).replace("%player%", p.getName())));

        this.members.add(p);
        p.setTeam(this);
        if (members.size() == 1 && p.getPlayer() != null) {
            this.tc.addPlayer(p);
        }

        p.teleport(this.tc.getLocation());
        p.sendMessage(TranslatableLine.TEAM_JOIN.get(p, true).replace("%team%", getName()));
    }

    public void removeMember(RSWPlayer p) {
        this.members.remove(p);

        this.members.forEach(rswPlayer -> rswPlayer.sendMessage(TranslatableLine.TEAM_BROADCAST_LEAVE.get(p, true).replace("%player%", p.getName())));

        if (this.playing && members.isEmpty()) {
            this.eliminated = true;
        }
        p.setTeam(null);
        p.sendMessage(TranslatableLine.TEAM_LEAVE.get(p, true).replace("%team%", getName()));
    }

    public Boolean isTeamFull() {
        return this.maxMembers == this.getMembers().size();
    }

    public void sendMessage(String s) {
        this.members.forEach(rswPlayer -> rswPlayer.sendCenterMessage(Text.color(s)));
    }

    public String getName() {
        return "Team " + id;
    }

    public String getNames() {
        List<String> list = new ArrayList<>();
        this.members.forEach(rswPlayer -> list.add(rswPlayer.getDisplayName()));
        return String.join(", ", list);
    }

    public void openCage() {
        this.tc.open();
    }

    public void reset() {
        this.playing = false;
        this.eliminated = false;
        this.members.clear();
    }

    public int getMaxMembers() {
        return this.maxMembers;
    }

    public List<RSWPlayer> getMembers() {
        return this.members;
    }

    public RSWCage getTeamCage() {
        return this.tc;
    }

    public boolean isEliminated() {
        return this.eliminated;
    }

    public int getMemberCount() {
        return this.getMembers().size();
    }
}

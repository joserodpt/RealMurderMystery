package joserodpt.realmurdermystery.api.map.modes;

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
import joserodpt.realmurdermystery.api.map.RSWMap;
import joserodpt.realmurdermystery.api.map.modes.teams.RSWTeam;
import joserodpt.realmurdermystery.api.player.RSWPlayer;
import org.bukkit.Location;

import java.util.Collection;

public class PlaceholderMode extends RSWMap {
    public PlaceholderMode(String nome) {
        super(nome);
    }

    @Override
    public String forceStart(RSWPlayer p) {
        return null;
    }

    @Override
    public boolean canStartMap() {
        return false;
    }

    @Override
    public void removePlayer(RSWPlayer p) {
    }

    @Override
    public void addPlayer(RSWPlayer gp) {
    }

    @Override
    public void resetArena(OperationReason rr) {
    }

    @Override
    public void checkWin() {
    }

    @Override
    public GameMode getGameMode() {
        return null;
    }

    @Override
    public Collection<RSWCage> getCages() {
        return null;
    }

    @Override
    public Collection<RSWTeam> getTeams() {
        return null;
    }

    @Override
    public int getMaxTeamsNumber() {
        return 0;
    }

    @Override
    public int getMaxTeamsMembers() {
        return 0;
    }

    @Override
    public int getMaxGameTime() {
        return 0;
    }

    @Override
    public void forceStartMap() {
    }

    @Override
    public int minimumPlayersToStartMap() {
        return 0;
    }

    @Override
    public void removeCage(Location loc) {
    }

    @Override
    public void addCage(Location location) {
    }
}

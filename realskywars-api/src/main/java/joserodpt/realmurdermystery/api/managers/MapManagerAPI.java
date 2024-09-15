package joserodpt.realmurdermystery.api.managers;

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
import joserodpt.realskywars.api.chests.RSWChest;
import joserodpt.realskywars.api.managers.world.RSWWorld;
import joserodpt.realmurdermystery.api.map.RSWMap;
import joserodpt.realmurdermystery.api.player.RSWPlayer;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class MapManagerAPI {
    public Boolean endMaps = false;

    public abstract void loadMaps();

    public abstract void deleteMap(RSWMap map);

    public abstract RSWMap getMap(World w);

    public abstract RSWMap getMap(String s);

    public abstract void endMaps();

    public abstract List<RSWMap> getMapsForPlayer(RSWPlayer rswPlayer);

    public abstract Collection<RSWMap> getMaps(MapGamemodes pt);

    protected abstract Map<Location, RSWCage> getMapCages(String s, World w);

    protected abstract Map<Location, RSWChest> getMapChests(String worldName, String section);

    public abstract void setupSolo(RSWPlayer p, String mapname, String displayName, RSWWorld.WorldType wt, int maxP);

    public abstract void setupTeams(RSWPlayer p, String mapname, String displayName, RSWWorld.WorldType wt, int teams, int pperteam);

    public abstract void finishMap(RSWPlayer p);

    protected abstract RSWWorld.WorldType getWorldType(String s);

    protected abstract Boolean isInstantEndingEnabled(String s);

    protected abstract Location getPOS1(World w, String s);

    protected abstract Location getPOS2(World w, String s);

    public abstract Boolean isSpecEnabled(String s);

    public abstract Location getSpecLoc(String nome);

    protected abstract Boolean isRanked(String s);

    public abstract void findMap(RSWPlayer player, RSWMap.GameMode type);

    public abstract Optional<RSWMap> findSuitableGame(RSWMap.GameMode type);

    public abstract void clearMaps();

    public abstract void addMap(RSWMap s);

    public abstract Collection<String> getMapNames();

    public abstract void editMap(RSWPlayer p, RSWMap sw);

    public enum MapGamemodes {SOLO, SOLO_RANKED, TEAMS, TEAMS_RANKED, RANKED, ALL}
}

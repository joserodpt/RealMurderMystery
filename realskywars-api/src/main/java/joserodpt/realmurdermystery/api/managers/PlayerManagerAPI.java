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

import joserodpt.realskywars.api.config.RSWLanguage;
import joserodpt.realmurdermystery.api.map.RSWMap;
import joserodpt.realmurdermystery.api.player.RSWPlayer;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public abstract class PlayerManagerAPI {
    public abstract void loadPlayer(Player p);

    public abstract RSWPlayer getPlayer(Player p);

    public abstract void savePlayer(RSWPlayer p, RSWPlayer.PlayerData pd);

    public abstract void setLanguage(RSWPlayer player, RSWLanguage lang);

    public abstract void loadPlayers();

    public abstract int getPlayingPlayers(MapManagerAPI.MapGamemodes pt);

    public abstract void stopScoreboards();

    public abstract Collection<RSWPlayer> getPlayers();

    public abstract void addPlayer(RSWPlayer rswPlayer);

    public abstract void removePlayer(RSWPlayer rswPlayer);

    public abstract void trackPlayer(RSWPlayer gp);

    public abstract List<UUID> getTeleporting();

    public abstract Map<UUID, RSWMap> getFastJoin();
}

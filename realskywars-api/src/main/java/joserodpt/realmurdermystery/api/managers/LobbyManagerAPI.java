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

import joserodpt.realmurdermystery.api.player.RSWPlayer;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public abstract class LobbyManagerAPI {
    public abstract void loadLobby();

    public abstract void tpToLobby(RSWPlayer p);

    public abstract Location getLobbyLocation();

    public abstract boolean scoreboardInLobby();

    public abstract void setLobbyLoc(Location location);

    public abstract boolean tpLobbyOnJoin();

    public abstract boolean isInLobby(World w);

    public abstract void tpToLobby(Player player);
}

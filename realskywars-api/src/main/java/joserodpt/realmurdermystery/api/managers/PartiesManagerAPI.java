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

import joserodpt.realmurdermystery.api.map.RSWMap;
import joserodpt.realmurdermystery.api.player.RSWPlayer;

import java.util.HashMap;
import java.util.Map;

public abstract class PartiesManagerAPI {
    public Map<RSWPlayer, RSWPlayer> invites = new HashMap<>();

    public Boolean hasInvite(RSWPlayer p) {
        return invites.containsKey(p);
    }

    public RSWPlayer getInvite(RSWPlayer p) {
        return invites.get(p);
    }

    public abstract void sendInvite(RSWPlayer emissor, RSWPlayer recetor);

    public abstract void acceptInvite(RSWPlayer p);

    public abstract boolean checkForParties(RSWPlayer p, RSWMap swgm);
}

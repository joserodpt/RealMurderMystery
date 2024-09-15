package joserodpt.realmurdermystery.api.player.tab;

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

import org.bukkit.entity.Player;

import java.util.List;

public class RSWPlayerNoTab implements RSWPlayerTabInterface {

    public RSWPlayerNoTab() {
    }

    @Override
    public void addPlayers(Player p) {
    }

    @Override
    public void addPlayers(List<Player> p) {
    }

    @Override
    public void removePlayers(Player p) {
    }

    @Override
    public void reset() {
    }

    @Override
    public void clear() {
    }

    @Override
    public void setHeaderFooter(String h, String f) {
    }

    @Override
    public void updateRoomTAB() {
    }
}

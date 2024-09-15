package joserodpt.realmurdermystery.plugin.managers;

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
import joserodpt.realskywars.api.managers.HologramManagerAPI;
import joserodpt.realskywars.api.managers.holograms.RSWHologram;
import org.bukkit.Bukkit;

public class HologramManager extends HologramManagerAPI {

    private RSWHologram.HType selected = RSWHologram.HType.NONE;

    public HologramManager(RealSkywarsAPI rsa) {

        //select scoreboard plugin
        if (Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays")) {
            this.selected = RSWHologram.HType.HOLOGRAPHIC_DISPLAYS;
        }
        if (Bukkit.getPluginManager().isPluginEnabled("DecentHolograms")) {
            this.selected = RSWHologram.HType.DECENT_HOLOGRAMS;
        }

        switch (this.selected) {
            case DECENT_HOLOGRAMS:
                rsa.getLogger().info("Hooked on Decent Holograms!");
                break;
            case HOLOGRAPHIC_DISPLAYS:
                rsa.getLogger().info("Hooked on Holographic Displays!");
                break;
        }
    }

    @Override
    public RSWHologram getHologramInstance() {
        return this.selected.getHologramInstance();
    }
}

package joserodpt.realmurdermystery.api.utils;

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

import joserodpt.realmurdermystery.api.Debugger;
import joserodpt.realskywars.api.cages.RSWCage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

public class Demolition {

    private final Location laserloc1;
    private final RSWCage cage;
    private final int laserTime, delayExplosion;

    public Demolition(Location laserloc1, RSWCage cage, int laserTime, int delayExplosion) {
        this.laserloc1 = laserloc1;
        this.cage = cage;
        this.laserTime = laserTime;
        this.delayExplosion = delayExplosion;
    }

    public void start(Plugin plugin) {
        try {
            Location sploc = laserloc1;
            sploc.setY(255);
            Location cage = this.cage.getLocation();

            cage.add(0.5, 0, 0.5);
            Laser laser = new Laser.GuardianLaser(sploc, cage, this.laserTime, (int) sploc.distance(cage));
            laser.start(plugin);

            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, this.cage::clearCage, this.delayExplosion * 20L);
        } catch (Exception e) {
            Debugger.print(Demolition.class, "Could not show win laser for " + this.cage.getLocation().toString() + "\n" + e.getMessage());
        }
    }
}

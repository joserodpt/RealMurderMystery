package joserodpt.realmurdermystery.api.map;

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

import joserodpt.realmurdermystery.api.config.TranslatableLine;
import joserodpt.realmurdermystery.api.utils.Text;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class RSWBossbar {

    private final RSWMap map;
    private BossBar bossBar;

    public RSWBossbar(RSWMap map) {
        this.map = map;
        reset();
    }

    public void reset() {
        this.bossBar = Bukkit.createBossBar(TranslatableLine.BOSSBAR_ARENA_WAIT.getSingle(), BarColor.WHITE, BarStyle.SOLID);
    }

    public void tick() {
        if (this.bossBar == null) {
            return;
        }

        switch (map.getState()) {
            case PLAYING:
                this.bossBar.setTitle(TranslatableLine.BOSSBAR_ARENA_RUNTIME.getSingle().replace("%time%", Text.formatSeconds(map.getMapTimer().getSecondsLeft())));
                double div = (double) map.getMapTimer().getSecondsLeft() / (double) map.getMaxGameTime();
                this.bossBar.setProgress(div);
                break;
            case FINISHING:
                double div2 = (double) map.getFinishingTimer().getSecondsLeft() / (double) map.getTimeEndGame();
                this.bossBar.setProgress(div2);
                break;
        }
    }

    public void addPlayer(Player player) {
        if (player != null && this.bossBar != null) {
            this.bossBar.addPlayer(player);
        }
    }

    public void removePlayer(Player player) {
        if (player != null && this.bossBar != null) {
            this.bossBar.removePlayer(player);
        }
    }

    public void setState(RSWMap.MapState w) {
        if (this.bossBar == null) {
            return;
        }

        switch (w) {
            case WAITING:
                this.bossBar.setTitle(TranslatableLine.BOSSBAR_ARENA_WAIT.getSingle());
                this.bossBar.setProgress(0D);
                break;
            case STARTING:
                int time = map.getStartMapTimer() == null ? 0 : map.getStartMapTimer().getSecondsLeft();
                this.bossBar.setTitle(TranslatableLine.BOSSBAR_ARENA_STARTING.getSingle().replace("%time%", Text.formatSeconds(time)));
                double div = (double) time / (double) map.getTimeToStart();
                if (div <= 1 && div >= 0) {
                    this.bossBar.setProgress(div);
                }
                break;
            case FINISHING:
                this.bossBar.setTitle(TranslatableLine.BOSSBAR_ARENA_END.getSingle());
                this.bossBar.setColor(BarColor.BLUE);

                int time2 = map.getFinishingTimer() == null ? 0 : map.getFinishingTimer().getSecondsLeft();
                double div2 = (double) time2 / (double) map.getTimeEndGame();
                if (div2 <= 1 && div2 >= 0) {
                    this.bossBar.setProgress(div2);
                }
                break;
            case RESETTING:
                this.bossBar.removeAll();
                break;
        }
    }

    public void setDeathMatch() {
        if (this.bossBar == null) {
            return;
        }
        this.bossBar.setTitle(TranslatableLine.BOSSBAR_ARENA_DEATHMATCH.getSingle());
        this.bossBar.setColor(BarColor.RED);
        this.bossBar.setProgress(0);
    }
}

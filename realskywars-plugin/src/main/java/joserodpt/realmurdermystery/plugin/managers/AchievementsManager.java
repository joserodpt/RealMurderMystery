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
import joserodpt.realskywars.api.achievements.RSWAchievement;
import joserodpt.realskywars.api.achievements.types.RSWAchievementRCoin;
import joserodpt.realskywars.api.config.RSWAchievementsConfig;
import joserodpt.realskywars.api.managers.AchievementsManagerAPI;
import joserodpt.realskywars.api.player.RSWPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class AchievementsManager extends AchievementsManagerAPI {
    private final RealSkywarsAPI rs;

    public AchievementsManager(RealSkywarsAPI rs) {
        this.rs = rs;
    }

    public Map<RSWPlayer.PlayerStatistics, List<RSWAchievement>> achievements = new HashMap<>();

    @Override
    public void loadAchievements() {
        int cats = 0, achi = 0;
        this.achievements.clear();
        //load coin achievements
        for (String dir : RSWAchievementsConfig.file().getSection("Coins").getRoutesAsStrings(false).stream()
                .map(Object::toString)
                .collect(Collectors.toSet())) {
            ++cats;
            RSWPlayer.PlayerStatistics t = null;

            switch (dir) {
                case "Kills":
                    t = RSWPlayer.PlayerStatistics.KILLS;
                    break;
                case "Wins-Solo":
                    t = RSWPlayer.PlayerStatistics.WINS_SOLO;
                    break;
                case "Wins-Teams":
                    t = RSWPlayer.PlayerStatistics.WINS_TEAMS;
                    break;
                case "Games-Played":
                    t = RSWPlayer.PlayerStatistics.GAMES_PLAYED;
                    break;
            }

            List<RSWAchievement> achiv = new ArrayList<>();

            String path = "Coins." + dir;
            for (String meta : RSWAchievementsConfig.file().getSection(path).getRoutesAsStrings(false)) {
                ++achi;
                Double value = RSWAchievementsConfig.file().getDouble(path + "." + meta);
                achiv.add(new RSWAchievementRCoin(t, Integer.parseInt(meta), value));
            }

            this.achievements.put(t, achiv);
        }

        rs.getLogger().info("Loaded " + achi + " rewards for " + cats + " coin categories.");
    }

    @Override
    public List<RSWAchievement> getAchievements(RSWPlayer.PlayerStatistics ds) {
        return this.achievements.get(ds);
    }

    @Override
    public RSWAchievement getAchievement(RSWPlayer.PlayerStatistics ps, int meta) {
        List<RSWAchievement> list = this.achievements.get(ps);
        if (list != null) {
            Optional<RSWAchievement> o = list.stream().filter(c -> c.getGoal() == meta).findFirst();
            if (o.isPresent()) {
                return o.get();
            }
        }
        return null;
    }
}

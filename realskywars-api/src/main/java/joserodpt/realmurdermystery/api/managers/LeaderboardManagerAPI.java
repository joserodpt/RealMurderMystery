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

import joserodpt.realmurdermystery.api.database.PlayerDataRow;
import joserodpt.realskywars.api.leaderboards.RSWLeaderboard;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.List;

public abstract class LeaderboardManagerAPI {
    public abstract void refreshLeaderboards();

    public abstract void refreshLeaderboard(RSWLeaderboard.RSWLeaderboardCategories l) throws SQLException;

    @NotNull
    protected abstract RSWLeaderboard getLeaderboard(RSWLeaderboard.RSWLeaderboardCategories l, List<PlayerDataRow> expansions);

    public abstract RSWLeaderboard getLeaderboard(RSWLeaderboard.RSWLeaderboardCategories l);
}

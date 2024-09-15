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

import com.j256.ormlite.dao.Dao;
import joserodpt.realmurdermystery.api.database.PlayerBoughtItemsRow;
import joserodpt.realmurdermystery.api.database.PlayerDataRow;
import joserodpt.realmurdermystery.api.database.PlayerGameHistoryRow;
import joserodpt.realmurdermystery.api.player.RSWGameHistoryStats;
import joserodpt.realmurdermystery.api.player.RSWPlayer;
import joserodpt.realskywars.api.shop.RSWBuyableItem;
import joserodpt.realmurdermystery.api.utils.Pair;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public abstract class DatabaseManagerAPI {
    @NotNull
    protected abstract String getDatabaseURL();

    protected abstract void getPlayerData();

    public abstract Pair<Collection<PlayerGameHistoryRow>, RSWGameHistoryStats> getPlayerGameHistory(Player p);

    public abstract List<PlayerBoughtItemsRow> getPlayerBoughtItems(Player p);

    public abstract List<PlayerBoughtItemsRow> getPlayerBoughtItemsCategory(Player p, RSWBuyableItem.ItemCategory cat);

    public abstract PlayerDataRow getPlayerData(Player p);

    public abstract void savePlayerData(PlayerDataRow playerDataRow, boolean async);

    public abstract void saveNewGameHistory(PlayerGameHistoryRow playerGameHistoryRow, boolean async);

    public abstract void saveNewBoughtItem(PlayerBoughtItemsRow playerBoughtItemsRow, boolean async);

    public abstract void deletePlayerData(UUID playerUUID, boolean async);

    public abstract void deletePlayerGameHistory(UUID playerUUID, boolean async);

    public abstract void deletePlayerBoughtItems(UUID playerUUID, boolean async);

    public abstract Dao<PlayerDataRow, UUID> getQueryDao();

    public abstract Pair<Boolean, String> didPlayerBoughtItem(RSWPlayer p, RSWBuyableItem item);
}

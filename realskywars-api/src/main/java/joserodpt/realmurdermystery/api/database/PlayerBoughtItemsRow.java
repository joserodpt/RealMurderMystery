package joserodpt.realmurdermystery.api.database;

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

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import joserodpt.realmurdermystery.api.config.RSWConfig;
import joserodpt.realmurdermystery.api.player.RSWPlayer;
import joserodpt.realmurdermystery.api.utils.Text;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@DatabaseTable(tableName = "realscoreboard_player_bought_items")
public class PlayerBoughtItemsRow {
    @DatabaseField(columnName = "id", canBeNull = false, allowGeneratedIdInsert = true, generatedId = true)
    private @NotNull UUID id;

    @DatabaseField(columnName = "player_uuid")
    private @NotNull UUID player_uuid;

    @DatabaseField(columnName = "player_name")
    private String player_name;

    @DatabaseField(columnName = "item_id")
    private String itemID;

    @DatabaseField(columnName = "category")
    private String category;

    @DatabaseField(columnName = "bought_date")
    private String date;

    public PlayerBoughtItemsRow(RSWPlayer p, String itemID, String category) {
        this.player_uuid = p.getUUID();
        this.player_name = p.getName();
        this.itemID = ChatColor.stripColor(itemID);
        this.category = category;
        this.date = Text.getDateAndTime();
    }

    public PlayerBoughtItemsRow() {
        //for ORMLite
    }

    public String getPlayerName() {
        return this.player_name;
    }

    @NotNull
    public UUID getId() {
        return this.id;
    }

    public UUID getPlayerUUID() {
        return this.player_uuid;
    }

    public String getItemID() {
        return itemID;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public Date getFormattedDateObject() {
        DateFormat dateFormat = new SimpleDateFormat(RSWConfig.file().getString("Config.Time.Formatting"));
        try {
            return dateFormat.parse(date);
        } catch (Exception e) {
            return new Date();
        }
    }
}
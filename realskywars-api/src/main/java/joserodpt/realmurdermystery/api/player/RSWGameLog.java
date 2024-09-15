package joserodpt.realmurdermystery.api.player;

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
import joserodpt.realmurdermystery.api.config.TranslatableList;
import joserodpt.realmurdermystery.api.map.RSWMap;
import joserodpt.realmurdermystery.api.utils.Itens;
import joserodpt.realmurdermystery.api.utils.Text;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class RSWGameLog {

    private String map;
    private int players, seconds, kills;
    private boolean ranked, win;
    private RSWMap.GameMode gameMode;
    private String dayandtime;

    private boolean dummy = false;

    public RSWGameLog(String map, String gameMode, boolean ranked, int players, int kills, boolean win, int seconds, String dayandtime) {
        this.map = map;
        this.gameMode = RSWMap.GameMode.valueOf(gameMode);
        this.ranked = ranked;
        this.players = players;
        this.win = win;
        this.kills = kills;
        this.seconds = seconds;
        this.dayandtime = dayandtime;
    }

    public RSWGameLog() {
        this.dummy = true;
    }

    public ItemStack getItem(RSWPlayer p) {
        if (this.dummy) {
            return Itens.createItem(Material.BUCKET, 1, TranslatableLine.SEARCH_NOTFOUND_NAME.getSingle());
        }

        List<String> list = TranslatableList.GAME_LOG_LIST.get(p);

        for (String s : list) {
            list.set(list.indexOf(s), s.replace("%players%", String.valueOf(this.players))
                    .replace("%map%", this.map + " &7[&f" + this.gameMode.getDisplayName(p) + "&7]")
                    .replace("%ranked%", this.ranked ? "&a&l✔" : "&c&l❌")
                    .replace("%win%", this.win ? "&a&l✔" : "&c&l❌")
                    .replace("%kills%", String.valueOf(this.kills))
                    .replace("%time%", Text.formatSeconds(this.seconds)));
        }

        return Itens.createItem(Material.FILLED_MAP, 1, "&f&l" + this.dayandtime, list);
    }
}

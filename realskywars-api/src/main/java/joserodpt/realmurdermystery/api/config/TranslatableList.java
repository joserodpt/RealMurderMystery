package joserodpt.realmurdermystery.api.config;

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

import joserodpt.realskywars.api.config.RSWLanguagesConfig;
import joserodpt.realmurdermystery.api.player.RSWPlayer;
import joserodpt.realmurdermystery.api.utils.Text;

import java.util.List;

public enum TranslatableList {

    MAP_START(".Messages.Map.Start"),
    MAP_END_LOG(".Messages.Map.End-Log"),
    EDIT_MAP(".Messages.Map.Edit-Map"),
    SCOREBOARD_LOBBY_LINES(".Scoreboards.Lobby.Lines"),
    SCOREBOARD_CAGE_LINES(".Scoreboards.Cage.Lines"),
    SCOREBOARD_PLAYING_LINES(".Scoreboards.Game.Lines"),
    SCOREBOARD_SPECTATOR_LINES(".Scoreboards.Spectate.Lines"),
    ITEMS_MAP_DESCRIPTION(".Itens.Map.Description"),
    TITLE_ROOMJOIN(".Titles.Join-Room"),
    STATS_ITEM_LORE(".Itens.Statistics.Lore"),
    REFILL_EVENT_TITLE(".Messages.Map.Events.Refill"),
    TNTRAIN_EVENT_TITLE(".Messages.Map.Events.TNTRain"),
    STATISTIC_PLAYER_LIST(".Statistics.Player-List"),
    STATISTIC_GAMES_LIST(".Statistics.Games-List"),
    TAB_HEADER_MATCH(".Tab.In-Game.Header"),
    TAB_FOOTER_MATCH(".Tab.In-Game.Footer"),
    TAB_HEADER_OTHER(".Tab.Other.Header"),
    TAB_FOOTER_OTHER(".Tab.Other.Footer"), GAME_LOG_LIST(".Statistics.Game-Log-List");

    private final String configPath;

    TranslatableList(String configPath) {
        this.configPath = configPath;
    }

    public List<String> getInLanguage(String lang) {
        return Text.color(RSWLanguagesConfig.file().getStringList("Languages." + lang + this.configPath));
    }

    public List<String> get(RSWPlayer player) {
        return getInLanguage(player.getLanguage());
    }

}

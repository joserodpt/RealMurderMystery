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
import joserodpt.realmurdermystery.api.database.PlayerGameHistoryRow;
import joserodpt.realmurdermystery.api.utils.Itens;
import joserodpt.realmurdermystery.api.utils.Text;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.IntSummaryStatistics;
import java.util.List;

public class RSWGameHistoryStats {

    protected int numberGames, numberWins, numerRanked, shortestTime, longestTime, averageTime, mostKillsInAGame;
    protected double med_kills;

    public RSWGameHistoryStats(Collection<PlayerGameHistoryRow> logs) {
        numberGames = logs.size();
        med_kills = logs.stream().mapToInt(PlayerGameHistoryRow::getKills).average().orElse(0);
        numberWins = (int) logs.stream().filter(PlayerGameHistoryRow::wasWin).count();
        numerRanked = (int) logs.stream().filter(PlayerGameHistoryRow::isRanked).count();
        mostKillsInAGame = logs.stream().mapToInt(PlayerGameHistoryRow::getKills).max().orElse(0);

        IntSummaryStatistics stats = logs.stream()
                .mapToInt(PlayerGameHistoryRow::getTime)
                .summaryStatistics();

        shortestTime = logs.isEmpty() ? 0 : stats.getMin();
        longestTime = logs.isEmpty() ? 0 : stats.getMax();
        averageTime = logs.isEmpty() ? 0 : (int) stats.getAverage();
    }

    public RSWGameHistoryStats() {
    }

    public int getNumberWins() {
        return numberWins;
    }

    public int getNumberLooses() {
        return numberGames - numberWins;
    }

    public double getAverageKills() {
        return med_kills;
    }

    public int getNumberGames() {
        return numberGames;
    }

    public int getNumberRanked() {
        return numerRanked;
    }

    public int getAverageTime() {
        return averageTime;
    }

    public int getLongestTime() {
        return longestTime;
    }

    public int getShortestTime() {
        return shortestTime;
    }

    public int getMostKillsInAGame() {
        return mostKillsInAGame;
    }

    public ItemStack getItem(RSWPlayer rswp) {
        return Itens.createItem(Material.BOOKSHELF, 1, TranslatableLine.STATISTIC_NAME.get(rswp), getLore(rswp));
    }

    private List<String> getLore(RSWPlayer rswp) {
        List<String> list = TranslatableList.STATISTIC_GAMES_LIST.get(rswp);

        for (String s : list) {
            list.set(list.indexOf(s), Text.color(s.replace("%games%", String.valueOf(getNumberGames()))
                    .replace("%wins%", String.valueOf(getNumberWins()))
                    .replace("%wins_percentage%", percentage(getNumberWins(), getNumberGames()))
                    .replace("%looses%", String.valueOf(getNumberLooses()))
                    .replace("%looses_percentage%", percentage(getNumberLooses(), getNumberGames()))
                    .replace("%ranked%", String.valueOf(getNumberRanked()))
                    .replace("%ranked_percentage%", percentage(getNumberRanked(), getNumberGames()))
                    .replace("%average_kills%", String.valueOf(getAverageKills()))
                    .replace("%most_kills%", String.valueOf(getMostKillsInAGame()))
                    .replace("%average_time%", Text.formatSeconds(getAverageTime()))
                    .replace("%longest_time%", Text.formatSeconds(getLongestTime()))
                    .replace("%shortest_time%", Text.formatSeconds(getShortestTime()))));
        }

        return list;
    }

    private String percentage(int small, int total) {
        if (total == 0) return " (0%)";
        return "(" + (int) ((small * 100.0) / total) + "%)";
    }
}

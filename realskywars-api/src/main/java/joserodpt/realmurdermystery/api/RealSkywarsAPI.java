package joserodpt.realmurdermystery.api;

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

import com.google.common.base.Preconditions;
import joserodpt.realmurdermystery.api.currency.CurrencyAdapterAPI;
import joserodpt.realmurdermystery.api.managers.AchievementsManagerAPI;
import joserodpt.realmurdermystery.api.managers.DatabaseManagerAPI;
import joserodpt.realmurdermystery.api.managers.HologramManagerAPI;
import joserodpt.realmurdermystery.api.managers.KitManagerAPI;
import joserodpt.realmurdermystery.api.managers.LanguageManagerAPI;
import joserodpt.realmurdermystery.api.managers.LeaderboardManagerAPI;
import joserodpt.realmurdermystery.api.managers.LobbyManagerAPI;
import joserodpt.realmurdermystery.api.managers.MapManagerAPI;
import joserodpt.realmurdermystery.api.managers.PartiesManagerAPI;
import joserodpt.realmurdermystery.api.managers.PlayerManagerAPI;
import joserodpt.realmurdermystery.api.managers.ShopManagerAPI;
import joserodpt.realmurdermystery.api.managers.WorldManagerAPI;
import joserodpt.realskywars.api.nms.RSWnms;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;
import java.util.logging.Logger;

public abstract class RealSkywarsAPI {

    private static RealSkywarsAPI instance;

    /**
     * Gets instance of this API
     *
     * @return RealSkywarsAPI API instance
     */
    public static RealSkywarsAPI getInstance() {
        return instance;
    }

    /**
     * Sets the RealMinesAPI instance.
     * <b>Note! This method may only be called once</b>
     *
     * @param instance the new instance to set
     */
    public static void setInstance(RealSkywarsAPI instance) {
        Preconditions.checkNotNull(instance, "instance");
        Preconditions.checkArgument(RealSkywarsAPI.instance == null, "Instance already set");
        RealSkywarsAPI.instance = instance;
    }

    public abstract Logger getLogger();

    public abstract String getVersion();

    public abstract RSWnms getNMS();

    public abstract WorldManagerAPI getWorldManagerAPI();

    public abstract RSWEventsAPI getEventsAPI();

    public abstract LanguageManagerAPI getLanguageManagerAPI();

    public abstract PlayerManagerAPI getPlayerManagerAPI();

    public abstract MapManagerAPI getMapManagerAPI();

    public abstract LobbyManagerAPI getLobbyManagerAPI();

    public abstract ShopManagerAPI getShopManagerAPI();

    public abstract KitManagerAPI getKitManagerAPI();

    public abstract PartiesManagerAPI getPartiesManagerAPI();

    public abstract Random getRandom();

    public abstract DatabaseManagerAPI getDatabaseManagerAPI();

    public abstract LeaderboardManagerAPI getLeaderboardManagerAPI();

    public abstract AchievementsManagerAPI getAchievementsManagerAPI();

    public abstract HologramManagerAPI getHologramManagerAPI();

    public abstract CurrencyAdapterAPI getCurrencyAdapterAPI();

    public abstract JavaPlugin getPlugin();

    public abstract String getServerVersion();

    public abstract String getSimpleServerVersion();

    public abstract boolean hasNewUpdate();

    public abstract void reload();

    public abstract Economy getVaultEconomy();
}

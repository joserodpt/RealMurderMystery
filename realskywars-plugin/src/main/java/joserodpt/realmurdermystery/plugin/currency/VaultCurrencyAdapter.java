package joserodpt.realmurdermystery.plugin.currency;

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
import joserodpt.realskywars.api.currency.CurrencyAdapterAPI;
import joserodpt.realskywars.api.player.RSWPlayer;

public class VaultCurrencyAdapter implements CurrencyAdapterAPI {

    @Override
    public void transferCoins(RSWPlayer toPlayer, RSWPlayer fromPlayer, double amount) {
        removeCoins(fromPlayer, amount);
        addCoins(toPlayer, amount);
    }

    @Override
    public void addCoins(RSWPlayer p, double amount) {
        RealSkywarsAPI.getInstance().getVaultEconomy().depositPlayer(p.getPlayer(), amount);
    }

    @Override
    public boolean removeCoins(RSWPlayer p, double amount) {
        return RealSkywarsAPI.getInstance().getVaultEconomy().withdrawPlayer(p.getPlayer(), amount).transactionSuccess();
    }

    @Override
    public void setCoins(RSWPlayer p, double amount) {
        RealSkywarsAPI.getInstance().getVaultEconomy().withdrawPlayer(p.getPlayer(), getCoins(p));
        RealSkywarsAPI.getInstance().getVaultEconomy().depositPlayer(p.getPlayer(), amount);
    }

    @Override
    public double getCoins(RSWPlayer p) {
        return RealSkywarsAPI.getInstance().getVaultEconomy().getBalance(p.getPlayer());
    }

    @Override
    public String getCoinsFormatted(RSWPlayer p) {
        return RealSkywarsAPI.getInstance().getVaultEconomy().format(getCoins(p));
    }
}

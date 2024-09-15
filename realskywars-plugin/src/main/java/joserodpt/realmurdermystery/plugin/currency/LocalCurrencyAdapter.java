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


import joserodpt.realskywars.api.currency.CurrencyAdapterAPI;
import joserodpt.realskywars.api.player.RSWPlayer;
import joserodpt.realskywars.api.utils.Text;

public class LocalCurrencyAdapter implements CurrencyAdapterAPI {
    @Override
    public void transferCoins(RSWPlayer toPlayer, RSWPlayer fromPlayer, double amount) {
        removeCoins(fromPlayer, amount);
        addCoins(toPlayer, amount);
    }

    @Override
    public void addCoins(RSWPlayer p, double amount) {
        p.setLocalCoins(getCoins(p) + amount);
        p.saveData(RSWPlayer.PlayerData.COINS);
    }

    @Override
    public boolean removeCoins(RSWPlayer p, double amount) {
        if (getCoins(p) >= amount) {
            setCoins(p, getCoins(p) - amount);
            //p.sendMessage(TranslatableLine.REMOVED_COINS.get(p, true).replace("%coins%", "" + amount));
            return true;
        }

        return false;
    }

    @Override
    public void setCoins(RSWPlayer p, double amount) {
        p.setLocalCoins(amount);
        p.saveData(RSWPlayer.PlayerData.COINS);
    }

    @Override
    public double getCoins(RSWPlayer p) {
        return p.getLocalCoins();
    }

    @Override
    public String getCoinsFormatted(RSWPlayer p) {
        return Text.formatDouble(this.getCoins(p));
    }
}

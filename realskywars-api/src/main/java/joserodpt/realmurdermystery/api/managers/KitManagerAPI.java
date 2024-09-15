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

import joserodpt.realmurdermystery.api.database.PlayerBoughtItemsRow;
import joserodpt.realskywars.api.kits.RSWKit;
import joserodpt.realskywars.api.shop.RSWBuyableItem;

import java.util.Collection;

public abstract class KitManagerAPI {
    public abstract void loadKits();

    public abstract void registerKit(RSWKit k);

    public abstract void unregisterKit(RSWKit k);

    public abstract Collection<RSWKit> getKits();

    public abstract Collection<RSWBuyableItem> getKitsAsBuyables();

    public abstract RSWKit getKit(String string);

    public abstract RSWKit getKit(PlayerBoughtItemsRow playerBoughtItemsRow);
}

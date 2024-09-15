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

import joserodpt.realskywars.api.config.RSWLanguage;
import joserodpt.realmurdermystery.api.player.RSWPlayer;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class LanguageManagerAPI {
    protected final Map<String, RSWLanguage> langList = new HashMap<>();

    public abstract void loadLanguages();

    public abstract String getDefaultLanguage();

    public abstract boolean areLanguagesEmpty();

    public abstract Collection<RSWLanguage> getLanguages();

    public abstract Map<String, RSWLanguage> getLanguagesMap();

    public abstract String getPrefix();

    public abstract String getMaterialName(RSWPlayer p, Material mat);

    public abstract String getMaterialName(Material mat);

    public abstract String getEnchantmentName(RSWPlayer p, Enchantment ench);

    public abstract String getEntityName(RSWPlayer p, EntityType type);

    public abstract RSWLanguage getLanguage(String language);
}

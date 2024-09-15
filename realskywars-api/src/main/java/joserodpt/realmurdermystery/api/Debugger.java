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

import org.bukkit.Bukkit;

import java.util.Objects;
import java.util.logging.Level;

public class Debugger {
    public static Boolean debug = false;

    public static void printerr(Class a, String b) {
        print(Level.SEVERE, a, b);
    }

    public static void print(Class a, String b) {
        print(Level.WARNING, a, b);
    }

    private static void print(Level l, Class a, String b) {
        if (debug) {
            Bukkit.getLogger().log(l, "[RSW:DEBUG] " + getName(a).replace("joserodpt.realskywars.", "") + " > " + b);
        }
    }

    static String getName(Class a) {
        Class<?> enclosingClass = a.getEnclosingClass();
        return Objects.requireNonNullElse(enclosingClass, a).getName();
    }

    public static void execute() {

    }
}

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

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public abstract class WorldManagerAPI {
    public abstract File[] verifiedListFiles(File directory) throws IOException;

    public abstract boolean isSymlink(File file);

    public abstract World createEmptyWorld(String name, World.Environment environment);

    public abstract boolean loadWorld(String worldName, World.Environment environment);

    public abstract void unloadWorld(String w, boolean save);

    protected abstract void tpToLobby(Player p);

    public abstract void copyWorld(String name, CopyTo t);

    public abstract void copyWorld(String name, File source, File target);

    public abstract void deleteWorld(String name, boolean removeFile);

    public abstract void deleteDirectory(File directory) throws IOException;

    protected abstract void cleanDirectory(File directory) throws IOException;

    public abstract void forceDelete(File file) throws IOException;

    public void clearDroppedItems(World world) {
        world.getEntities().stream().filter(e -> e.getType() == EntityType.DROPPED_ITEM).forEach(org.bukkit.entity.Entity::remove);
    }

    public enum CopyTo {ROOT, RSW_FOLDER}

    public class VoidWorld extends ChunkGenerator {
        @Override
        public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome) {
            return createChunkData(world);
        }

        public Location getFixedSpawnLocation(World world, Random random) {
            return new Location(world, 0.0D, 128.0D, 0.0D);
        }
    }
}

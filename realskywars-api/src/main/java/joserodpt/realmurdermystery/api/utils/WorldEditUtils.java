package joserodpt.realmurdermystery.api.utils;

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

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.math.Vector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldedit.world.block.BaseBlock;
import com.sk89q.worldedit.world.block.BlockState;
import com.sk89q.worldedit.world.block.BlockType;
import joserodpt.realmurdermystery.api.Debugger;
import joserodpt.realmurdermystery.api.RealSkywarsAPI;
import joserodpt.realmurdermystery.api.map.RSWMap;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.File;
import java.nio.file.Files;

public class WorldEditUtils {

    public static boolean schemFileExists(String name) {
        File folder = new File(RealSkywarsAPI.getInstance().getPlugin().getDataFolder(), "maps");
        return new File(folder, name).exists();
    }

    public static void setBlocks(Location loc1, Location loc2, BlockType type) {
        Debugger.print(WorldEditUtils.class, "Setting blocks!");

        World world = BukkitAdapter.adapt(loc1.getWorld());
        EditSession editSession = WorldEdit.getInstance().newEditSessionBuilder().world(world).build();

        BlockVector3 bv1 = BlockVector3.at(loc1.getBlockX(), loc1.getBlockY(), loc1.getBlockZ());
        BlockVector3 bv2 = BlockVector3.at(loc2.getBlockX(), loc2.getBlockY(), loc2.getBlockZ());

        CuboidRegion rg = new CuboidRegion(world, bv1, bv2);

        BlockState b = type.getDefaultState();
        BaseBlock block = b.toBaseBlock();

        try {
            editSession.setBlocks(rg, block);
            editSession.close();

            Debugger.print(WorldEditUtils.class, "All done!");
        } catch (WorldEditException e) {
            RealSkywarsAPI.getInstance().getLogger().severe("Error while setting blocks!");
            RealSkywarsAPI.getInstance().getLogger().severe(e.getMessage());
        }
    }

    public static void pasteSchematic(String name, Location location, RSWMap map) {
        Debugger.print(WorldEditUtils.class, "Pasting schematic named " + name);
        File folder = new File(RealSkywarsAPI.getInstance().getPlugin().getDataFolder(), "maps");
        File file = new File(folder, name);

        ClipboardFormat clipboardFormat = ClipboardFormats.findByFile(file);
        Clipboard clipboard;

        if (clipboardFormat != null) {
            try (ClipboardReader clipboardReader = clipboardFormat.getReader(Files.newInputStream(file.toPath()))) {

                if (location.getWorld() == null)
                    throw new NullPointerException("Failed to paste schematic due to world being null");

                clipboard = clipboardReader.read();

                if (clipboard != null) {
                    try (final EditSession editSession = WorldEdit.getInstance().newEditSession(BukkitAdapter.adapt(location.getWorld()))) {
                        ClipboardHolder holder = new ClipboardHolder(clipboard);
                        Region region = clipboard.getRegion();

                        BlockVector3 to = BlockVector3.at(location.getBlockX(), location.getBlockY(), location.getBlockZ());
                        Operation operation = holder
                                .createPaste(editSession)
                                .to(to)
                                .ignoreAirBlocks(false)
                                .copyBiomes(false)
                                .copyEntities(false)
                                .build();
                        Operations.completeLegacy(operation);

                        if (map != null) {
                            BlockVector3 clipboardOffset = clipboard.getRegion().getMinimumPoint().subtract(clipboard.getOrigin());
                            Vector3 min = to.toVector3().add(holder.getTransform().apply(clipboardOffset.toVector3()));
                            Vector3 max = min.add(holder.getTransform().apply(region.getMaximumPoint().subtract(region.getMinimumPoint()).toVector3()));

                            map.setBoundaries(toLocation(min, location.getWorld()), WorldEditUtils.toLocation(max, location.getWorld()));

                            for (Player p : location.getWorld().getPlayers()) {
                                p.sendMessage("&aThe boundaries have been set automatically using the schematic boundaries!");
                                p.sendMessage("&fIf you want to change them, use WorldEdit to select the area and type /rsw setbounds");
                            }
                        }
                    } catch (final WorldEditException e) {
                        RealSkywarsAPI.getInstance().getLogger().severe("Error while pasting schematic: " + name);
                        RealSkywarsAPI.getInstance().getLogger().severe(e.getMessage());
                    }
                }

            } catch (Exception e) {
                RealSkywarsAPI.getInstance().getLogger().severe("Error while pasting schematic!");
                RealSkywarsAPI.getInstance().getLogger().severe(e.getMessage());
            }
        }
    }

    public static Location toLocation(com.sk89q.worldedit.math.Vector3 vector, org.bukkit.World world) {
        return new Location(world, vector.getX(), vector.getY(), vector.getZ());
    }
}

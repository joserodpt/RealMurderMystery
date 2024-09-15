package joserodpt.realmurdermystery.api.map.modes;

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

import joserodpt.realmurdermystery.api.RealSkywarsAPI;
import joserodpt.realmurdermystery.api.map.RSWMap;
import joserodpt.realmurdermystery.api.utils.Text;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.block.data.type.WallSign;

public class RSWSign {

    private final RSWMap game;
    private final Block b;

    public RSWSign(RSWMap gm, Block b) {
        this.game = gm;
        this.b = b;

        this.update();
    }

    public void update() {
        if (this.game == null) {
            return;
        }

        Bukkit.getScheduler().scheduleSyncDelayedTask(RealSkywarsAPI.getInstance().getPlugin(), () -> {
            if (this.getBlock().getType().name().contains("SIGN")) {
                Sign s = (Sign) this.getBlock().getState();

                s.setLine(0, RealSkywarsAPI.getInstance().getLanguageManagerAPI().getPrefix());
                s.setLine(1, Text.color("&b" + this.getGame().getName()));
                s.setLine(2, Text.color("&f" + this.getGame().getGameMode().name() + " | &f" + this.getGame().getPlayerCount() + "&7/&f" + this.getGame().getMaxPlayers()));
                s.setLine(3, Text.color("&b&l" + this.getGame().getState().name()));
                s.update();

                this.b.getWorld().getBlockAt(this.getBehindBlock().getLocation()).setType(this.getGame().getState().getStateMaterial(this.getGame().isRanked()));
            }
        });
    }

    private Block getGlass(Block b) {
        WallSign signData = (WallSign) b.getState().getBlockData();
        BlockFace attached = signData.getFacing().getOppositeFace();

        return b.getRelative(attached);
    }

    public Block getBlock() {
        return this.b;
    }

    public RSWMap getGame() {
        return this.game;
    }

    public Block getBehindBlock() {
        return this.getGlass(this.b);
    }

    public Location getLocation() {
        return this.b.getLocation();
    }

    public String getLocationSerialized() {
        return this.getLocation().getWorld().getName() + "<" +
                this.getLocation().getBlockX() + "<" +
                this.getLocation().getBlockY() + "<" +
                this.getLocation().getBlockZ();
    }

    public void delete() {
        Bukkit.getScheduler().scheduleSyncDelayedTask(RealSkywarsAPI.getInstance().getPlugin(), () -> {
            if (this.b.getType().name().contains("SIGN")) {
                this.b.setType(Material.AIR);
            }
        });
    }
}
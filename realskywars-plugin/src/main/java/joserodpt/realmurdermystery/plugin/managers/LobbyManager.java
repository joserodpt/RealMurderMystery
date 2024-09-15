package joserodpt.realmurdermystery.plugin.managers;

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
import joserodpt.realskywars.api.config.RSWConfig;
import joserodpt.realskywars.api.config.TranslatableLine;
import joserodpt.realskywars.api.managers.LobbyManagerAPI;
import joserodpt.realskywars.api.player.RSWPlayer;
import joserodpt.realskywars.api.player.RSWPlayerItems;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class LobbyManager extends LobbyManagerAPI {

    public RealSkywarsAPI rsa;

    public LobbyManager(RealSkywarsAPI rsa) {
        this.rsa = rsa;
    }

    private Location lobbyLOC;
    private Boolean loginTP = true;

    @Override
    public void loadLobby() {
        this.loginTP = RSWConfig.file().getBoolean("Config.Auto-Teleport-To-Lobby");
        if (RSWConfig.file().isSection("Config.Lobby")) {
            double x = RSWConfig.file().getDouble("Config.Lobby.X");
            double y = RSWConfig.file().getDouble("Config.Lobby.Y");
            double z = RSWConfig.file().getDouble("Config.Lobby.Z");
            float yaw = RSWConfig.file().getFloat("Config.Lobby.Yaw");
            float pitch = RSWConfig.file().getFloat("Config.Lobby.Pitch");
            World world = Bukkit.getServer().getWorld(RSWConfig.file().getString("Config.Lobby.World"));
            this.lobbyLOC = new Location(world, x, y, z, yaw, pitch);
        }
    }

    @Override
    public void tpToLobby(Player player) {
        if (this.lobbyLOC != null && player != null) {
            player.teleport(this.lobbyLOC);
        }
    }

    @Override
    public void tpToLobby(RSWPlayer p) {
        if (this.lobbyLOC != null) {
            tpToLobby(p.getPlayer());
            TranslatableLine.LOBBY_TELEPORT.send(p, true);
            RSWPlayerItems.LOBBY.giveSet(p);
        } else {
            TranslatableLine.LOBBY_NOT_SET.send(p, true);
        }
    }

    @Override
    public Location getLobbyLocation() {
        return this.lobbyLOC;
    }

    @Override
    public boolean scoreboardInLobby() {
        return RSWConfig.file().getBoolean("Config.Scoreboard-In-Lobby");
    }

    @Override
    public void setLobbyLoc(Location location) {
        this.lobbyLOC = location;
        //give everyone items again

        for (Player p : location.getWorld().getPlayers()) {
            RSWPlayer rswPlayer = rsa.getPlayerManagerAPI().getPlayer(p);
            if (rswPlayer != null) {
                RSWPlayerItems.LOBBY.giveSet(rswPlayer);
            }
        }
    }

    @Override
    public boolean tpLobbyOnJoin() {
        return loginTP;
    }

    @Override
    public boolean isInLobby(World w) {
        if (w == null || this.lobbyLOC == null || this.lobbyLOC.getWorld() == null) {
            return false;
        }
        return this.lobbyLOC != null && this.lobbyLOC.getWorld().equals(w);
    }

}

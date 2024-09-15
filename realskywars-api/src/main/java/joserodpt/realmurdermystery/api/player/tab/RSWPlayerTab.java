package joserodpt.realmurdermystery.api.player.tab;

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
import joserodpt.realmurdermystery.api.config.TranslatableList;
import joserodpt.realmurdermystery.api.managers.MapManagerAPI;
import joserodpt.realmurdermystery.api.player.RSWPlayer;
import joserodpt.realmurdermystery.api.utils.Text;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class RSWPlayerTab implements RSWPlayerTabInterface {

    private final RSWPlayer player;
    private final List<Player> show = new ArrayList<>();

    public RSWPlayerTab(RSWPlayer player) {
        this.player = player;
        clear();
        updateRoomTAB();
    }

    @Override
    public void addPlayers(Player p) {
        if (p.getUniqueId() != this.player.getUUID() && !this.show.contains(p)) {
            this.show.add(p);
        }
    }

    @Override
    public void addPlayers(List<Player> p) {
        this.show.addAll(p);
    }

    @Override
    public void removePlayers(Player p) {
        this.show.remove(p);
    }

    @Override
    public void reset() {
        this.show.addAll(Bukkit.getOnlinePlayers());
    }

    @Override
    public void clear() {
        this.show.clear();
    }

    @Override
    public void setHeaderFooter(String h, String f) {
        if (!this.player.isBot()) {
            this.player.getPlayer().setPlayerListHeaderFooter(Text.color(h), Text.color(f));
        }
    }


    @Override
    public void updateRoomTAB() {
        if (!this.player.isBot()) {
            Bukkit.getOnlinePlayers().forEach(pl -> this.player.hidePlayer(RealSkywarsAPI.getInstance().getPlugin(), pl));
            this.show.forEach(rswPlayer -> this.player.showPlayer(RealSkywarsAPI.getInstance().getPlugin(), rswPlayer));

            String header, footer;
            if (this.player.isInMatch()) {
                header = String.join("\n", TranslatableList.TAB_HEADER_MATCH.get(this.player)).replace("%map%", this.player.getMatch().getName()).replace("%displayname%", this.player.getMatch().getDisplayName()).replace("%players%", this.player.getMatch().getPlayers().size() + "").replace("%space%", Text.makeSpace());
                footer = String.join("\n", TranslatableList.TAB_FOOTER_MATCH.get(this.player)).replace("%map%", this.player.getMatch().getName()).replace("%displayname%", this.player.getMatch().getDisplayName()).replace("%players%", this.player.getMatch().getPlayers().size() + "").replace("%space%", Text.makeSpace());
            } else {
                header = String.join("\n", TranslatableList.TAB_HEADER_OTHER.get(this.player)).replace("%players%", RealSkywarsAPI.getInstance().getPlayerManagerAPI().getPlayingPlayers(MapManagerAPI.MapGamemodes.ALL) + "").replace("%space%", Text.makeSpace());
                footer = String.join("\n", TranslatableList.TAB_FOOTER_OTHER.get(this.player)).replace("%players%", RealSkywarsAPI.getInstance().getPlayerManagerAPI().getPlayingPlayers(MapManagerAPI.MapGamemodes.ALL) + "").replace("%space%", Text.makeSpace());
            }

            this.setHeaderFooter(header, footer);
        }
    }
}

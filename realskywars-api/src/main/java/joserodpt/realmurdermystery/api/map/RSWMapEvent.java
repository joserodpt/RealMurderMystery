package joserodpt.realmurdermystery.api.map;

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

import joserodpt.realskywars.api.config.RSWLanguagesConfig;
import joserodpt.realmurdermystery.api.config.TranslatableLine;
import joserodpt.realmurdermystery.api.config.TranslatableList;
import joserodpt.realmurdermystery.api.utils.Itens;
import joserodpt.realmurdermystery.api.utils.Text;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class RSWMapEvent {

    private final EventType eventType;
    private final RSWMap room;
    private int time;

    public RSWMapEvent(RSWMap room, EventType eventType, int time) {
        this.room = room;
        this.eventType = eventType;
        this.time = time;
    }

    public RSWMapEvent(RSWMap map, EventType eventType) {
        this(map, eventType, 30);
    }

    public EventType getEventType() {
        return this.eventType;
    }

    public String getName() {
        return Text.color(this.eventType.getName() + " " + Text.formatSeconds(this.getTimeLeft()));
    }

    public int getTimeLeft() {
        return (this.room.getMaxGameTime() - (this.room.getMaxGameTime() - this.getTime())) - this.room.getTimePassed();
    }

    public void tick() {
        if (this.getTimeLeft() == 0) {
            execute();
            this.room.getEvents().remove(this);
        }
    }

    public int getTime() {
        return this.time;
    }

    public void execute() {
        switch (this.eventType) {
            case REFILL:
                this.room.getAllPlayers().forEach(rswPlayer -> rswPlayer.sendTitle(TranslatableList.REFILL_EVENT_TITLE.get(rswPlayer).get(0), TranslatableList.REFILL_EVENT_TITLE.get(rswPlayer).get(1), 4, 10, 4));
                this.room.getAllPlayers().forEach(rswPlayer -> rswPlayer.playSound(Sound.BLOCK_CHEST_LOCKED, 50, 50));
                break;
            case TNTRAIN:
                this.room.getAllPlayers().forEach(rswPlayer -> rswPlayer.sendTitle(TranslatableList.TNTRAIN_EVENT_TITLE.get(rswPlayer).get(0), TranslatableList.TNTRAIN_EVENT_TITLE.get(rswPlayer).get(1), 4, 10, 4));
                this.room.getAllPlayers().forEach(rswPlayer -> rswPlayer.playSound(Sound.ENTITY_TNT_PRIMED, 50, 50));
                this.room.getPlayers().forEach(player -> player.spawnAbovePlayer(TNTPrimed.class));
                break;
            case BORDERSHRINK:
                this.room.getBossBar().setDeathMatch();

                this.room.getAllPlayers().forEach(rswPlayer -> rswPlayer.sendTitle("", TranslatableLine.TITLE_DEATHMATCH.get(rswPlayer), 10, 20, 5));
                this.room.getAllPlayers().forEach(rswPlayer -> rswPlayer.playSound(Sound.ENTITY_ENDER_DRAGON_GROWL, 50, 50));

                this.room.getBorder().setSize((double) this.room.getBorderSize() / 2, 30L);
                this.room.getBorder().setCenter(this.room.getMapCuboid().getCenter());
                break;
        }
    }

    public String serialize() {
        return this.eventType.name() + "@" + this.time;
    }

    public ItemStack getItem() {
        return Itens.createItem(this.getEventType().getIcon(), 1, this.getEventType().getName() + " &r&f@ &b" + Text.formatSeconds(this.getTimeLeft()), Text.color(Arrays.asList("&a&nLeft-Click&r&f to edit", "&c&nQ (Drop)&r&f to remove")));
    }

    public void setTime(int seconds) {
        this.time = seconds;
    }

    public enum EventType {
        REFILL(Material.CHEST), TNTRAIN(Material.TNT), BORDERSHRINK(Material.SPAWNER);

        final Material icon;

        EventType(Material icon) {
            this.icon = icon;
        }

        public Material getIcon() {
            return this.icon;
        }

        public String getName() {
            return Text.color(RSWLanguagesConfig.file().getString("Strings.Events." + this.name()));
        }
    }
}

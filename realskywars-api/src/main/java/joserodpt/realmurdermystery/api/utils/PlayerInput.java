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

import joserodpt.realmurdermystery.api.RealSkywarsAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PlayerInput {

    private static Map<UUID, PlayerInput> inputs = new HashMap<>();
    private UUID uuid;

    private List<String> texts = Text
            .color(Arrays.asList("&l&9Type in chat your input", "&fType &4cancel &fto cancel"));

    private InputRunnable runGo;
    private InputRunnable runCancel;
    private BukkitTask taskId;

    public PlayerInput(Player p, InputRunnable correct, InputRunnable cancel) {
        this.uuid = p.getUniqueId();
        p.closeInventory();
        this.runGo = correct;
        this.runCancel = cancel;
        this.taskId = new BukkitRunnable() {
            public void run() {
                p.getPlayer().sendTitle(texts.get(0), texts.get(1), 0, 21, 0);
            }
        }.runTaskTimer(RealSkywarsAPI.getInstance().getPlugin(), 0L, 20);

        this.register();
    }

    private void register() {
        inputs.put(this.uuid, this);
    }

    private void unregister() {
        inputs.remove(this.uuid);
    }

    @FunctionalInterface
    public interface InputRunnable {
        void run(String input) throws IOException;
    }

    public static Listener getListener() {
        return new Listener() {
            @EventHandler
            public void onPlayerChat(AsyncPlayerChatEvent event) {
                Player p = event.getPlayer();
                String input = event.getMessage();
                UUID uuid = p.getUniqueId();
                if (inputs.containsKey(uuid)) {
                    PlayerInput current = inputs.get(uuid);
                    event.setCancelled(true);
                    try {
                        if (input.equalsIgnoreCase("cancel")) {
                            p.sendMessage(Text.color("&fInput canceled."));
                            current.taskId.cancel();
                            p.sendTitle("", "", 0, 1, 0);
                            Bukkit.getScheduler().scheduleSyncDelayedTask(RealSkywarsAPI.getInstance().getPlugin(), () -> {
                                try {
                                    current.runCancel.run(input);
                                } catch (Exception e) {
                                    Bukkit.getLogger().severe("An error ocourred while running the cancel runnable.");
                                    Bukkit.getLogger().severe(e.getMessage());
                                }
                            }, 3);
                            current.unregister();
                            return;
                        }

                        current.taskId.cancel();
                        Bukkit.getScheduler().scheduleSyncDelayedTask(RealSkywarsAPI.getInstance().getPlugin(), () -> {
                            try {
                                current.runGo.run(input);
                            } catch (Exception e) {
                                Bukkit.getLogger().severe("An error ocourred while running the runGo runnable.");
                                Bukkit.getLogger().severe(e.getMessage());
                            }
                        }, 3);
                        p.sendTitle("", "", 0, 1, 0);
                        current.unregister();
                    } catch (Exception e) {
                        p.sendMessage(Text.color("&cAn error ocourred. Contact JoseGamer_PT on www.spigotmc.org"));
                        RealSkywarsAPI.getInstance().getLogger().severe(e.getMessage());
                    }
                }
            }
        };
    }
}

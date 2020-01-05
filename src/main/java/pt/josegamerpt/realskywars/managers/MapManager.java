package pt.josegamerpt.realskywars.managers;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

import pt.josegamerpt.realskywars.Debugger;
import pt.josegamerpt.realskywars.cages.SoloCage;
import pt.josegamerpt.realskywars.classes.Cage;
import pt.josegamerpt.realskywars.classes.GameRoom;
import pt.josegamerpt.realskywars.classes.SetupRoom;
import pt.josegamerpt.realskywars.classes.Team;
import pt.josegamerpt.realskywars.classes.Enum.GameState;
import pt.josegamerpt.realskywars.classes.Enum.GameType;
import pt.josegamerpt.realskywars.classes.Enum.InteractionState;
import pt.josegamerpt.realskywars.classes.Enum.TL;
import pt.josegamerpt.realskywars.classes.Enum.TS;
import pt.josegamerpt.realskywars.configuration.Items;
import pt.josegamerpt.realskywars.configuration.Maps;
import pt.josegamerpt.realskywars.gui.MapSettings;
import pt.josegamerpt.realskywars.modes.Solo;
import pt.josegamerpt.realskywars.player.GamePlayer;
import pt.josegamerpt.realskywars.utils.Holograms;
import pt.josegamerpt.realskywars.utils.MathUtils;
import pt.josegamerpt.realskywars.utils.Text;
import pt.josegamerpt.realskywars.worlds.Worlds;

public class MapManager {

    static String clas = "[MAPMANAGER] - ";

    public static ArrayList<String> getRegisteredMaps() {
        Maps.reload();
        ArrayList<String> worlds = new ArrayList<String>();

        ConfigurationSection cs = Maps.file().getConfigurationSection("");
        Set<String> keys = cs.getKeys(false);
        for (Iterator<String> iterator1 = keys.iterator(); iterator1.hasNext(); ) {
            worlds.add((String) iterator1.next());
        }
        return worlds;
    }

    public static void unregisterMap(GameRoom map) {
        Maps.file().set(map.getName(), null);
        Maps.save();
        GameManager.rooms.remove(map);
    }

    public static void loadMaps() {

        GameManager.rooms.clear();

        int id = 0;
        for (String s : getRegisteredMaps()) {

            if (getGameType(s).equals(null)) {
                throw new IllegalStateException("Mode doesnt exist: " + s);
            }

            GameType t = getGameType(s);

            switch (t) {
                case SOLO:
                    World w = Bukkit.getWorld(Maps.file().getString(s + ".world"));
                    Solo gs = new Solo(id, s, w, GameState.AVAILABLE, getCagesSolo(s), Maps.file().getInt(s + ".number-of-players"), getSpecLoc(s), isSpecEnabled(s), isInstantEndingEnabled(s), getPOS1(w, s), getPOS2(w, s));
                    gs.saveRoom();
                    break;
                default:
                    throw new IllegalStateException("Mode doesnt exist: " + t.name());
            }
            id++;
        }
    }

    private static GameType getGameType(String s) {
        String as = Maps.file().getString(s + ".Settings.GameType");
        if (as.equalsIgnoreCase("SOLO")) {
            return GameType.SOLO;
        }
        if (as.equalsIgnoreCase("TEAMS")) {
            return GameType.TEAMS;
        }
        return null;
    }

    private static Boolean isInstantEndingEnabled(String s) {
        return Maps.file().getBoolean(s + ".Settings.Dragon-Ride");
    }

    private static Location getPOS1(World w, String s) {
        double hx = Maps.file().getDouble(s + ".World.Border.POS1-X");
        double hy = Maps.file().getDouble(s + ".World.Border.POS1-Y");
        double hz = Maps.file().getDouble(s + ".World.Border.POS1-Z");

        return new Location(w, hx, hy, hz);
    }

    private static Location getPOS2(World w, String s) {
        double hx = Maps.file().getDouble(s + ".World.Border.POS2-X");
        double hy = Maps.file().getDouble(s + ".World.Border.POS2-Y");
        double hz = Maps.file().getDouble(s + ".World.Border.POS2-Z");

        return new Location(w, hx, hy, hz);
    }

    public static Boolean isSpecEnabled(String s) {
        return Maps.file().getBoolean(s + ".Settings.Spectator");
    }

    public static Location getSpecLoc(String nome) {
        double x = Maps.file().getDouble(nome + ".Locations.Spectator.X");
        double y = Maps.file().getDouble(nome + ".Locations.Spectator.Y");
        double z = Maps.file().getDouble(nome + ".Locations.Spectator.Z");
        float pitch = (float) Maps.file().getDouble(nome + ".Locations.Spectator.Pitch");
        float yaw = (float) Maps.file().getDouble(nome + ".Locations.Spectator.Yaw");
        Location l = new Location(Bukkit.getWorld(nome), x, y, z, pitch, yaw);
        Debugger.print(clas + "SPECLOC FOR " + nome + " - " + l);
        return l;

    }

    public static GameRoom getMap(String name) {
        for (GameRoom g : GameManager.rooms) {
            if (name.equalsIgnoreCase(g.getName())) {
                return g;
            }
        }
        return null;
    }

    public static ArrayList<Cage> getCagesSolo(String map) {
        ConfigurationSection cs = Maps.file().getConfigurationSection(map + ".Locations.Cages");
        Set<String> keys = cs.getKeys(false);
        ArrayList<Cage> locs = new ArrayList<Cage>();
        int id = 0;
        for (Iterator<String> iterator1 = keys.iterator(); iterator1.hasNext(); ) {
            String i = iterator1.next();
            double x = Maps.file().getDouble(map + ".Locations.Cages." + i + ".X");
            double y = Maps.file().getDouble(map + ".Locations.Cages." + i + ".Y");
            double z = Maps.file().getDouble(map + ".Locations.Cages." + i + ".Z");
            World w = Bukkit.getWorld(Maps.file().getString(map + ".world"));
            Location loc = new Location(w, x, y, z);
            loc.add(0.5, 0, 0.5);
            Debugger.print(clas + "[GETLOCS for " + map + "] LOC" + id + " > " + loc.toString());
            locs.add(new SoloCage(id, loc));
            id++;
        }
        return locs;
    }

    public static void saveMap(GameRoom g) {
        String s = g.getName();

        // World
        Maps.file().set(s + ".world", g.getWorld().getName());

        // Map Name
        Maps.file().set(s + ".name", s);

        // Number Players
        Maps.file().set(s + ".number-of-players", g.getMaxPlayers());

        // Locations Cages
        for (Cage c : g.getCages()) {
            Location loc = c.getLocation();
            Maps.file().set(s + ".Locations.Cages." + c.getID() + ".X", Integer.valueOf(loc.getBlockX()));
            Maps.file().set(s + ".Locations.Cages." + c.getID() + ".Y", Integer.valueOf(loc.getBlockY()));
            Maps.file().set(s + ".Locations.Cages." + c.getID() + ".Z", Integer.valueOf(loc.getBlockZ()));
            Maps.file().set(s + ".Locations.Cages." + c.getID() + ".Yaw", Float.valueOf(loc.getYaw()));
            Maps.file().set(s + ".Locations.Cages." + c.getID() + ".Pitch", Float.valueOf(loc.getPitch()));
        }

        // SpecLoc
        Maps.file().set(s + ".Locations.Spectator.X", g.getSpectatorLocation().getX());
        Maps.file().set(s + ".Locations.Spectator.Y", g.getSpectatorLocation().getY());
        Maps.file().set(s + ".Locations.Spectator.Z", g.getSpectatorLocation().getZ());
        Maps.file().set(s + ".Locations.Spectator.Yaw", g.getSpectatorLocation().getYaw());
        Maps.file().set(s + ".Locations.Spectator.Pitch", g.getSpectatorLocation().getPitch());

        // Settings
        Maps.file().set(s + ".Settings.Spectator", g.isSpectatorEnabled());
        Maps.file().set(s + ".Settings.Instant-Ending", g.isInstantEndEnabled());
        Maps.file().set(s + ".Settings.GameType", g.getMode().name());

        // Border
        Maps.file().set(s + ".World.Border.POS1-X", g.getPOS1().getX());
        Maps.file().set(s + ".World.Border.POS1-Y", g.getPOS1().getY());
        Maps.file().set(s + ".World.Border.POS1-Z", g.getPOS1().getZ());
        Maps.file().set(s + ".World.Border.POS2-X", g.getPOS2().getX());
        Maps.file().set(s + ".World.Border.POS2-Y", g.getPOS2().getY());
        Maps.file().set(s + ".World.Border.POS2-Z", g.getPOS2().getZ());

        Maps.save();
    }

    public static void cancelSetup(GamePlayer p) {
        Maps.file().set(p.setup.Name, null);
        Maps.save();
        p.setup = null;
        PlayerManager.tpLobby(p);
    }

    public static void setupTeams(GamePlayer p, String mapname, int teams, int pperteam) {
        SetupRoom s = new SetupRoom(mapname, null, teams, pperteam);
        p.setup = s;
        p.istate = InteractionState.GUI_ROOMSETUP;

        MapSettings m = new MapSettings(s, p.p.getUniqueId());
        m.openInventory(p);
    }

    public static void continueSetup(GamePlayer p) {
        if (p.setup.tpConfirm == false) {

            p.istate = InteractionState.NONE;

            p.setup.tpConfirm = true;

            p.sendMessage(LanguageManager.getString(p, TS.GENERATING_WORLD, true));
            World world = Worlds.createWorld(p.setup.Name);
            p.setup.worldMap = world;

            Location loc = new Location(world, 0, 65, 0);
            p.p.teleport(loc);

            Text.sendList(p.p, LanguageManager.getList(p, TL.INITSETUP_ARENA), p.setup.maxPlayers);

            p.p.getInventory().addItem(Items.CAGESET);
            p.p.setGameMode(GameMode.CREATIVE);
        }
    }

    public static void setupSolo(GamePlayer p, String mapname, int maxP) {
        SetupRoom s = new SetupRoom(mapname, null, maxP);
        p.setup = s;
        p.istate = InteractionState.GUI_ROOMSETUP;

        MapSettings m = new MapSettings(s, p.p.getUniqueId());
        m.openInventory(p);
    }

    public static void finishSetup(GamePlayer p) {
        if (p.setup.POS1 == null || p.setup.POS2 == null) {
            p.sendMessage(LanguageManager.getString(p, TS.NO_ARENA_BOUNDARIES, true));
            return;
        }

        PlayerManager.tpLobby(p);
        ArrayList<String> list = new ArrayList<String>();
        list.add(LanguageManager.getString(p, TS.SAVING_ARENA, true));
        Text.sendList(p.p, list);

        p.p.getInventory().clear();

        // Beacon Remove
        Holograms.removeAll();
        for (Cage l : p.setup.cages) {
            p.setup.worldMap.getBlockAt(l.getLocation()).setType(Material.AIR);
        }

        // Save Data

        switch (p.setup.gameType) {
            case SOLO:
                Solo gs = new Solo(MapManager.getRegisteredMaps().size() + 1, p.setup.Name, p.setup.worldMap, GameState.AVAILABLE, p.setup.cages, p.setup.maxPlayers, p.setup.spectator, p.setup.spec, p.setup.instantEnding, p.setup.POS1, p.setup.POS2);
                gs.saveRoom();
                saveMap(gs);
                break;
            default:
                throw new IllegalStateException("Forbiden Mode");
        }

        p.setup = null;
        p.sendMessage(LanguageManager.getString(p, TS.ARENA_REGISTERED, true));

        PlayerManager.giveItems(p.p, PlayerManager.PlayerItems.LOBBY);
    }

    public static void saveSettings(GameRoom game) {
        Maps.file().set(game.getName() + ".Settings.Spectator", game.isSpectatorEnabled());
        Maps.file().set(game.getName() + ".Settings.Instant-End", game.isInstantEndEnabled());
        Maps.save();
    }
}

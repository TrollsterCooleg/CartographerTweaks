package me.cooleg.cartographertweaks.markerborder;

import io.github.bananapuncher714.cartographer.core.Cartographer;
import io.github.bananapuncher714.cartographer.core.api.WorldCursor;
import io.github.bananapuncher714.cartographer.core.api.map.WorldCursorProvider;
import io.github.bananapuncher714.cartographer.core.api.setting.SettingStateBoolean;
import io.github.bananapuncher714.cartographer.core.map.Minimap;
import io.github.bananapuncher714.cartographer.core.renderer.PlayerSetting;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCursor;

import java.util.Collection;
import java.util.HashSet;

public class BorderMarkerProvider implements WorldCursorProvider {

    private final Cartographer cartographer;
    private final SettingStateBoolean settings;
    private final int DISTANCE = 5;

    public BorderMarkerProvider(Cartographer cartographer, SettingStateBoolean settings) {
        this.cartographer = cartographer;
        this.settings = settings;
    }

    @Override
    public Collection<WorldCursor> getCursors(Player player, Minimap minimap, PlayerSetting playerSetting) {
        if (!cartographer.getPlayerManager().getViewerFor(player.getUniqueId()).getSetting(settings)) {return null;}
        World world = player.getWorld();
        WorldBorder border = world.getWorldBorder();
        Location loc = border.getCenter();
        int borderX = loc.getBlockX();
        int borderZ = loc.getBlockZ();
        int playerX = player.getLocation().getBlockX();
        int playerZ = player.getLocation().getBlockZ();
        double radius = border.getSize()/2;

        int x1 = (int) (borderX+radius);
        int x2 = (int) (borderX-radius);
        int z1 = (int) (borderZ+radius);
        int z2 = (int) (borderZ-radius);

        HashSet<WorldCursor> cursors = new HashSet<>();
        boolean seeX1 = Math.abs(playerX-x1)<64;
        boolean seeX2 = Math.abs(playerX-x2)<64;
        boolean seeZ1 = Math.abs(playerZ-z1)<64;
        boolean seeZ2 = Math.abs(playerZ-z2)<64;

        for (int x = playerX-64; x<=playerX+64; x++) {
            if (x % DISTANCE != 0) {continue;}
            if (x > x1 || x < x2) {continue;}
            if (seeZ1) {
                cursors.add(new WorldCursor(new Location(world, x, 0, z1), MapCursor.Type.RED_X));
            }
            if (seeZ2) {
                cursors.add(new WorldCursor(new Location(world, x, 0, z2), MapCursor.Type.RED_X));
            }
        }

        for (int z = playerZ-64; z<=playerZ+64; z++) {
            if (z % DISTANCE != 0) {continue;}
            if (z > z1 || z < z2) {continue;}
            if (seeX1) {
                cursors.add(new WorldCursor(new Location(world, x1, 0, z), MapCursor.Type.RED_X));
            }
            if (seeX2) {
                cursors.add(new WorldCursor(new Location(world, x2, 0, z), MapCursor.Type.RED_X));
            }
        }

        return cursors;

    }

}

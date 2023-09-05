package me.cooleg.cartographertweaks.showteammate;

import io.github.bananapuncher714.cartographer.core.Cartographer;
import io.github.bananapuncher714.cartographer.core.api.WorldCursor;
import io.github.bananapuncher714.cartographer.core.api.map.WorldCursorProvider;
import io.github.bananapuncher714.cartographer.core.api.setting.SettingStateBoolean;
import io.github.bananapuncher714.cartographer.core.map.Minimap;
import io.github.bananapuncher714.cartographer.core.renderer.PlayerSetting;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCursor;
import org.bukkit.scoreboard.Team;

import java.util.Collection;
import java.util.HashSet;

public class TeammateMarkerProvider implements WorldCursorProvider {

    private final Cartographer cartographer;
    private final SettingStateBoolean settings;

    public TeammateMarkerProvider(Cartographer cartographer, SettingStateBoolean settings) {
        this.cartographer = cartographer;
        this.settings = settings;
    }

    @Override
    public Collection<WorldCursor> getCursors(Player player, Minimap minimap, PlayerSetting playerSetting) {
        if (!cartographer.getPlayerManager().getViewerFor(player.getUniqueId()).getSetting(settings)) {return null;}
        Team team = player.getScoreboard().getPlayerTeam(player);
        if (team == null) {return null;}

        HashSet<WorldCursor> cursors = new HashSet<>();

        for (String name : team.getEntries()) {
            Player teammate = Bukkit.getPlayerExact(name);
            if (teammate == null) {continue;}
            if (teammate.getUniqueId().equals(player.getUniqueId())) {continue;}
            if (teammate.getScoreboardTags().contains("dead")) {continue;}
            if (teammate.getWorld() != player.getWorld()) {continue;}
            cursors.add(new WorldCursor(teammate.getLocation(), MapCursor.Type.BLUE_POINTER));
        }
        return cursors;
    }
}

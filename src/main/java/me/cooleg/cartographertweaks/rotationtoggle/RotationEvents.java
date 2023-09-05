package me.cooleg.cartographertweaks.rotationtoggle;

import io.github.bananapuncher714.cartographer.core.Cartographer;
import io.github.bananapuncher714.cartographer.core.api.BooleanOption;
import io.github.bananapuncher714.cartographer.core.api.setting.SettingStateBoolean;
import io.github.bananapuncher714.cartographer.core.map.MapViewer;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;

public class RotationEvents implements Listener {

    private final SettingStateBoolean toggleSetting;
    private final Cartographer cartographer;

    public RotationEvents(Cartographer cartographer, SettingStateBoolean toggleSetting) {
        this.toggleSetting = toggleSetting;
        this.cartographer = cartographer;
    }

    @EventHandler
    public void rightClickMap(PlayerInteractEvent event) {
        Action action = event.getAction();
        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) {return;}
        if (event.getHand() != EquipmentSlot.HAND) {return;}

        ItemStack item = event.getItem();
        if (item == null) {return;}
        if (item.getType() != Material.FILLED_MAP) {return;}

        MapMeta meta = (MapMeta) item.getItemMeta();
        if (cartographer.getRenderers().get(meta.getMapId()) == null) {return;}

        MapViewer viewer = cartographer.getPlayerManager().getViewerFor(event.getPlayer().getUniqueId());
        if (viewer.getSetting(toggleSetting)) {
            BooleanOption opposite = switch (viewer.getSetting(MapViewer.ROTATE)) {
                case TRUE -> BooleanOption.FALSE;
                case FALSE, UNSET -> BooleanOption.TRUE;
            };

            viewer.setSetting(MapViewer.ROTATE, opposite);
        }
    }

}

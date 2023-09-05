package me.cooleg.cartographertweaks;

import io.github.bananapuncher714.cartographer.core.Cartographer;
import io.github.bananapuncher714.cartographer.core.api.events.minimap.MinimapLoadEvent;
import io.github.bananapuncher714.cartographer.core.api.map.MapProvider;
import io.github.bananapuncher714.cartographer.core.map.Minimap;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashSet;

public class ProviderRegister implements Listener {

    private final Cartographer cartographer;
    private final HashSet<MapProvider> providers = new HashSet<>();

    public ProviderRegister(Cartographer cartographer) {
        this.cartographer = cartographer;
    }

    public void registerProvider(MapProvider provider) {
        providers.add(provider);
        for (Minimap map : cartographer.getMapManager().getMinimaps().values()) {
            map.register(provider);
        }
    }

    public void unregisterAll() {
        for (Minimap map : cartographer.getMapManager().getMinimaps().values()) {
            for (MapProvider provider : providers) {
                map.unregister(provider);
            }
        }
    }

    @EventHandler
    public void minimapLoad(MinimapLoadEvent event) {
        for (MapProvider provider : providers) {
            event.getMinimap().register(provider);
        }
    }

}

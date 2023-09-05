package me.cooleg.cartographertweaks;

import io.github.bananapuncher714.cartographer.core.Cartographer;
import io.github.bananapuncher714.cartographer.core.api.setting.SettingState;
import io.github.bananapuncher714.cartographer.core.api.setting.SettingStateBoolean;
import io.github.bananapuncher714.cartographer.core.module.Module;
import me.cooleg.cartographertweaks.markerborder.BorderMarkerProvider;
import me.cooleg.cartographertweaks.rotationtoggle.RotationEvents;
import me.cooleg.cartographertweaks.showteammate.TeammateMarkerProvider;

public class CartographerTweaks extends Module {

    private final SettingStateBoolean toggle = SettingStateBoolean.of( "rotation_toggle", false, true);
    private final SettingStateBoolean teammateMarkers = SettingStateBoolean.of( "teammate_markers", false, true);
    private final SettingStateBoolean borderMarkers = SettingStateBoolean.of( "border_markers", false, true);

    private ProviderRegister register;

    @Override
    public void onEnable() {
        Cartographer cartographer = getCartographer();
        registerSettings();

        registerListener(register = new ProviderRegister(cartographer));
        register.registerProvider(new TeammateMarkerProvider(cartographer, teammateMarkers));
        register.registerProvider(new BorderMarkerProvider(cartographer, borderMarkers));

        registerListener(new RotationEvents(cartographer, toggle));
    }

    @Override
    public void onDisable() {
        register.unregisterAll();
    }

    @Override
    public SettingState<?>[] getSettingStates() {
        return new SettingState<?>[] {toggle, teammateMarkers, borderMarkers};
    }
}

package me.cooleg.cartographertweaks;

import io.github.bananapuncher714.cartographer.core.api.setting.SettingState;
import io.github.bananapuncher714.cartographer.core.api.setting.SettingStateBoolean;
import io.github.bananapuncher714.cartographer.core.module.Module;

public class CartographerTweaks extends Module {

    private final SettingStateBoolean toggle = SettingStateBoolean.of( "rotation_toggle", false, true);

    @Override
    public void onEnable() {
        registerSettings();
        registerListener(new CartographerEvents(toggle, getCartographer()));
    }

    @Override
    public SettingState<?>[] getSettingStates() {
        return new SettingState<?>[] {toggle};
    }
}

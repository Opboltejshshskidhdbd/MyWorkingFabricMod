package com.twopartygamer.nitrofps;

import net.fabricmc.api.ModInitializer;

public class NitroFPS implements ModInitializer {
    @Override
    public void onInitialize() {
        KeybindHandler.register();
    }
}
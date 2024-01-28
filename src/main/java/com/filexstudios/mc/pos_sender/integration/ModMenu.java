package com.filexstudios.mc.pos_sender.integration;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class ModMenu implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        //return parent -> new PosSenderConfigScreen(parent, PosSenderConfig.INSTANCE);
        return null;
    }
}

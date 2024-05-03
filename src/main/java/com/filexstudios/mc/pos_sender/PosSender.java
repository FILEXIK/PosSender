package com.filexstudios.mc.pos_sender;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class PosSender implements ModInitializer {
    public static KeyBinding myKeybind;
    @Override
    public void onInitialize() {
        myKeybind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "pos_sender.send_position_key",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_Y,
                "Pos Sender"
        ));
        registerLanguage("en_us");
        registerLanguage("pl_pl");
    }


    private void registerLanguage(String languageCode) {
        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(
                new SimpleSynchronousResourceReloadListener() {
                    @Override
                    public void reload(ResourceManager manager) {

                    }
                    @Override
                    public Identifier getFabricId() {
                        return new Identifier("pos_sender", "lang/" + languageCode);
                    }
                });
    }
}

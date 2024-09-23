package com.filexstudios.mc.pos_sender.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.loader.impl.util.log.Log;
import net.fabricmc.loader.impl.util.log.LogCategory;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

import static com.filexstudios.mc.pos_sender.PosSender.myKeybind;

public class PosSenderClient implements ClientModInitializer {
    private boolean isKeyPressed = false;
    private String dimension = "error";
    private final int keyToPress = GLFW.GLFW_KEY_Y; // Przykładowy przycisk, można zmienić na inny

    @Override
    public void onInitializeClient() {
        ClientEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (entity == MinecraftClient.getInstance().player) {
                detectDimension(entity);
            }
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (myKeybind.wasPressed()) {
                if (client.player != null) {
                    sendChatMessage();
                }
            }
        });
    }

    private void sendChatMessage(){
        MinecraftClient client = MinecraftClient.getInstance();
        assert client.player != null;
        int posX = (int) Math.round(client.player.getX());
        int posY = (int) Math.round(client.player.getY());
        int posZ = (int) Math.round(client.player.getZ());
        String message = String.format("%s/%s/%s/%s", posX, posY, posZ, dimension);
        client.player.networkHandler.sendChatMessage(message);

        
    }


    private void detectDimension(Entity entity) {
        if (entity.getWorld().getRegistryKey().equals(World.NETHER)) {
            dimension = "Nether";
        } else if (entity.getWorld().getRegistryKey().equals(World.END)) {
            dimension = "End";
        } else if (entity.getWorld().getRegistryKey().equals(World.OVERWORLD)) {
            dimension = "Overworld";
        } else {
            dimension = String.valueOf(entity.getWorld().getRegistryKey().getValue());
        }
    }
}

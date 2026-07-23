package mba.sno.myopia.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyopiaClient implements ClientModInitializer {
    public static final String MOD_ID = "Myopia";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    // Use Identifier.fromNamespaceAndPath in MC 26.1.2
    public static final KeyMapping.Category MYOPIA_CATEGORY = KeyMapping.Category.register(
            Identifier.fromNamespaceAndPath("myopia", "main")
    );

    private static final String KEY_TOGGLE_MYOPIA = "key.myopia.toggle";
    public static boolean enableBlur = true;

    private static KeyMapping toggleKey;

    @Override
    public void onInitializeClient() {
        LOGGER.info("Making Minecraft anti-myopic since 2026!");

        toggleKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                KEY_TOGGLE_MYOPIA, // Translation key for the keybind name
                InputConstants.Type.KEYSYM, // Key type (keyboard)
                GLFW.GLFW_KEY_O, // Default key
                MYOPIA_CATEGORY // Category object
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleKey.consumeClick()) {
                enableBlur = !enableBlur;
                client.levelRenderer.allChanged();
            }
        });
    }
}

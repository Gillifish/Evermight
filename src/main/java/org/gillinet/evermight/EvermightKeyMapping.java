package org.gillinet.evermight;


import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = Evermight.MODID, value = Dist.CLIENT)
public class EvermightKeyMapping {
    private EvermightKeyMapping() {}

    public static KeyMapping CAST_KEY;

    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent e) {
        CAST_KEY = new KeyMapping(
                "key.evermight.cast",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_V,
                "key.categories.evermight"
        );
        e.register(CAST_KEY);
    }
}

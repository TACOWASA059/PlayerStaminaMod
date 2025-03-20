package com.github.tacowasa059.playerstaminamod.client.event;

import com.github.tacowasa059.playerstaminamod.PlayerStaminaMod;
import com.github.tacowasa059.playerstaminamod.client.ClientSettingVariables;
import com.github.tacowasa059.playerstaminamod.client.StaminaHandler;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PlayerStaminaMod.MODID, value = Dist.CLIENT)
public class StaminaBarRenderer {
    private static final int BAR_WIDTH = 45;
    private static final int BAR_HEIGHT = 1;

    // color
    private static final int YELLOW = 0xFFFFE500;
    private static final int RED = 0xFFFF0000;
    private static final int WHITE = 0xFFFFFFFF;
    private static final int BGColor = 0x80000000;

    @SubscribeEvent
    public static void onRenderGameOverlay(RenderGuiOverlayEvent.Post event) {
        if (!event.getOverlay().id().equals(VanillaGuiOverlay.HOTBAR.id())) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        if (mc.options.renderDebug) return;
        if (mc.screen!=null) return;


        float stamina = StaminaHandler.getStamina();
        float maxStamina = 100.0f;
        int staminaWidth = (int) (BAR_WIDTH * (stamina / maxStamina));

        GuiGraphics graphics = event.getGuiGraphics();

        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int screenHeight = mc.getWindow().getGuiScaledHeight();
        int x = (screenWidth - BAR_WIDTH) / 2;
        int y = screenHeight - 58;


        graphics.fill(x, y, x + BAR_WIDTH, y + BAR_HEIGHT, BGColor);

        if(stamina > ClientSettingVariables.MiddleThreshold){
            graphics.fill(x, y, x + staminaWidth, y + BAR_HEIGHT, WHITE);
        }else{
            if(StaminaHandler.isExhausted()){
                graphics.fill(x, y, x + staminaWidth, y + BAR_HEIGHT, RED);
            }else{
                graphics.fill(x, y, x + staminaWidth, y + BAR_HEIGHT, YELLOW);
            }
        }


        String text = "▶▶";
        PoseStack poseStack = graphics.pose();
        poseStack.pushPose();
        poseStack.translate(x - mc.font.width(text)/4f, y - BAR_HEIGHT, 0);
        poseStack.scale(0.25f, 0.5f, 0.5f);
        graphics.drawCenteredString(mc.font, text, 0, 0, 0xFFFFFF);
        poseStack.popPose();

    }
}

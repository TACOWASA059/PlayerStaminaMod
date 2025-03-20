package com.github.tacowasa059.playerstaminamod.common.event;

import com.github.tacowasa059.playerstaminamod.PlayerStaminaMod;
import com.github.tacowasa059.playerstaminamod.common.CommonConfig;
import com.github.tacowasa059.playerstaminamod.common.networks.NetworkHandler;
import com.github.tacowasa059.playerstaminamod.common.networks.StaminaSyncPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

@Mod.EventBusSubscriber(modid = PlayerStaminaMod.MODID)
public class PlayerJoinListener {
    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new StaminaSyncPacket(
                    CommonConfig.tickRecoveryRate.get().floatValue(),
                    CommonConfig.tickSprintConsumptionRate.get().floatValue(),
                    CommonConfig.jumpConsumption.get().floatValue(),
                    CommonConfig.initStamina.get().floatValue(),
                    CommonConfig.respawnStamina.get().floatValue(),
                    CommonConfig.middleThreshold.get().floatValue(),
                    CommonConfig.sprintSpeedMultiplier.get().floatValue()
            ));
        }
    }
}

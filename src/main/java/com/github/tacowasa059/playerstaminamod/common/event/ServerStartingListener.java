package com.github.tacowasa059.playerstaminamod.common.event;

import com.github.tacowasa059.playerstaminamod.PlayerStaminaMod;
import com.github.tacowasa059.playerstaminamod.common.CommonConfig;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PlayerStaminaMod.MODID)
public class ServerStartingListener {
    @SubscribeEvent
    public static void onStart(ServerStartingEvent event){
        SprintSpeedHandler.setSpeedFactor(CommonConfig.sprintSpeedMultiplier.get().floatValue());
    }
}

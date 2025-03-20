package com.github.tacowasa059.playerstaminamod.client.event;

import com.github.tacowasa059.playerstaminamod.PlayerStaminaMod;
import com.github.tacowasa059.playerstaminamod.client.ClientSettingVariables;
import com.github.tacowasa059.playerstaminamod.client.SprintSpeedHandler;
import com.github.tacowasa059.playerstaminamod.client.StaminaHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PlayerStaminaMod.MODID, value = Dist.CLIENT)
public class ClientPlayerEvent {

    @SubscribeEvent
    public static void onTick(TickEvent.ClientTickEvent event){
        if(event.phase== TickEvent.Phase.END) return;

        LocalPlayer player = Minecraft.getInstance().player;
        if(player == null) return;

        // modify sprint speed
        SprintSpeedHandler.modifySprintSpeed(player);

        if(!player.isSpectator() && !player.isCreative()){
            // exhausted
            if(StaminaHandler.getStamina()==0 && !StaminaHandler.isExhausted()){
                StaminaHandler.setExhausted(true);
                player.setSprinting(false);
            }
            else if(StaminaHandler.getStamina() > ClientSettingVariables.MiddleThreshold && StaminaHandler.isExhausted()){
                StaminaHandler.setExhausted(false);
            }

            // recover/consumption
            if(player.isSprinting()){
                StaminaHandler.subtract(ClientSettingVariables.tickSprintConsumptionRate);
            }else{
                StaminaHandler.add(ClientSettingVariables.tickRecoveryRate);
            }
        } else{
            // recover/consumption
            StaminaHandler.add(ClientSettingVariables.tickRecoveryRate);
        }
    }



    // jump consumption
    @SubscribeEvent
    public static void onJump(LivingEvent.LivingJumpEvent event){
        LivingEntity entity = event.getEntity();
        if(entity == null) return;
        if(!entity.level().isClientSide) return;

        if(entity.equals(Minecraft.getInstance().player)){
            Player player = Minecraft.getInstance().player;
            if(player.isCreative() || player.isSpectator()) return;

            if(StaminaHandler.isExhausted()){
                Vec3 vec3 = entity.getDeltaMovement();
                entity.setDeltaMovement(new Vec3(vec3.x, 0, vec3.z));
                return;
            }

            StaminaHandler.subtract(ClientSettingVariables.jumpConsumption);
        }
    }

    // kill reset
    @SubscribeEvent
    public static void onKill(LivingDeathEvent event){
        LivingEntity entity = event.getEntity();
        if(entity.equals(Minecraft.getInstance().player)){
            StaminaHandler.setStamina(ClientSettingVariables.respawnStamina);
            StaminaHandler.setExhausted(ClientSettingVariables.respawnIsExhausted);
        }
    }


}

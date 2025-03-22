package com.github.tacowasa059.playerstaminamod.common.event;

import com.github.tacowasa059.playerstaminamod.PlayerStaminaMod;
import com.github.tacowasa059.playerstaminamod.common.Accessor.IPlayerStamina;
import com.github.tacowasa059.playerstaminamod.common.CommonConfig;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PlayerStaminaMod.MODID)
public class ServerPlayerEvent {

    @SubscribeEvent
    public static void onTick(TickEvent.PlayerTickEvent event){
        if(event.phase== TickEvent.Phase.END) return;

        Player player = event.player;
        if(player == null) return;

        if(player.level().isClientSide) return;
        IPlayerStamina playerStamina = (IPlayerStamina) player;

        // modify sprint speed
        SprintSpeedHandler.modifySprintSpeed(player);

        // below middle
        playerStamina.playerStaminaMod$setBelowMiddle(playerStamina.playerStaminaMod$getStamina() <= CommonConfig.middleThreshold.get());

        if(!player.isSpectator() && !player.isCreative()){
            // exhausted
            if(playerStamina.playerStaminaMod$getStamina()==0 && !playerStamina.playerStaminaMod$isExhausted()){
                playerStamina.playerStaminaMod$setExhausted(true);
                player.setSprinting(false);
            }
            else if(!playerStamina.playerStaminaMod$isBelowMiddle() && playerStamina.playerStaminaMod$isExhausted()){
                playerStamina.playerStaminaMod$setExhausted(false);
            }

            // recover/consumption
            if(player.isSprinting()){
                playerStamina.playerStaminaMod$subtractStamina(CommonConfig.tickSprintConsumptionRate.get().floatValue());
            }else{
                playerStamina.playerStaminaMod$addStamina(CommonConfig.tickRecoveryRate.get().floatValue());
            }
        } else{
            // recover/consumption
            playerStamina.playerStaminaMod$addStamina(CommonConfig.tickRecoveryRate.get().floatValue());
        }
    }



    // jump consumption
    @SubscribeEvent
    public static void onJump(LivingEvent.LivingJumpEvent event){
        LivingEntity entity = event.getEntity();
        if(entity == null) return;
        if(!(entity instanceof  Player player)) return;

        if(player.isCreative() || player.isSpectator()) return;
        IPlayerStamina playerStamina = (IPlayerStamina) player;

        // exhausted
        if(playerStamina.playerStaminaMod$isExhausted()){
            Vec3 vec3 = entity.getDeltaMovement();
            entity.setDeltaMovement(new Vec3(vec3.x, 0, vec3.z));
            return;
        }

        // subtract
        if(!player.level().isClientSide) playerStamina.playerStaminaMod$subtractStamina(CommonConfig.jumpConsumption.get().floatValue());

    }
}

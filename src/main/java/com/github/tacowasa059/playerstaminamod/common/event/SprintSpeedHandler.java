package com.github.tacowasa059.playerstaminamod.common.event;

import com.github.tacowasa059.playerstaminamod.common.Accessor.IPlayerStamina;
import com.github.tacowasa059.playerstaminamod.common.CommonConfig;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class SprintSpeedHandler {

    private static final UUID SPEED_MODIFIER_SPRINTING_UUID = UUID.fromString("662A6B8D-DA3E-4C1C-8813-96EA6097278E");

    private static final String key = "Sprinting speed limiter";

    private static AttributeModifier SPEED_MODIFIER_SPRINTING = new AttributeModifier(SPEED_MODIFIER_SPRINTING_UUID,
            key, CommonConfig.sprintSpeedMultiplier.get() - 1.0D, AttributeModifier.Operation.MULTIPLY_TOTAL);

    // calculation -> d1 *= 1.0D + attributemodifier2.getAmount();

    public static void modifySprintSpeed(Player player) {
        IPlayerStamina playerStamina = (IPlayerStamina) player;
        AttributeInstance attributeinstance = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if(attributeinstance == null) return;

        if(player.isSprinting()){
            if (attributeinstance.getModifier(SPEED_MODIFIER_SPRINTING_UUID) == null){
                if(playerStamina.playerStaminaMod$isBelowMiddle()){
                    attributeinstance.addTransientModifier(SPEED_MODIFIER_SPRINTING);
                }
            } else{
                if(!playerStamina.playerStaminaMod$isBelowMiddle()){
                    attributeinstance.removeModifier(SPEED_MODIFIER_SPRINTING);
                }
            }
        }else{
            if (attributeinstance.getModifier(SPEED_MODIFIER_SPRINTING_UUID) != null) {
                attributeinstance.removeModifier(SPEED_MODIFIER_SPRINTING);
            }
        }
    }

    public static void setSpeedFactor(float value){
        SPEED_MODIFIER_SPRINTING = new AttributeModifier(SPEED_MODIFIER_SPRINTING_UUID, key,
                value - 1.0D, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
}

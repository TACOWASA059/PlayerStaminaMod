package com.github.tacowasa059.playerstaminamod.client;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;

public class SprintSpeedHandler {

    private static final UUID SPEED_MODIFIER_SPRINTING_UUID = UUID.fromString("662A6B8D-DA3E-4C1C-8813-96EA6097278D");
    private static final AttributeModifier SPEED_MODIFIER_SPRINTING30 = new AttributeModifier(SPEED_MODIFIER_SPRINTING_UUID,
            "Sprinting speed boost", 0.3F, AttributeModifier.Operation.MULTIPLY_TOTAL);

    private static AttributeModifier SPEED_MODIFIER_SPRINTING20 = new AttributeModifier(SPEED_MODIFIER_SPRINTING_UUID,
            "Sprinting speed boost", 0.3F * ClientSettingVariables.sprintSpeedMultiplier, AttributeModifier.Operation.MULTIPLY_TOTAL);

    public static void modifySprintSpeed(LocalPlayer player) {
        if(player.isSprinting()){
            AttributeInstance attributeinstance = player.getAttribute(Attributes.MOVEMENT_SPEED);
            if(attributeinstance == null) return;
            if (attributeinstance.getModifier(SPEED_MODIFIER_SPRINTING_UUID) == null) return;
            if(StaminaHandler.getStamina() <= ClientSettingVariables.MiddleThreshold){

                if(!player.isCreative() && !player.isSpectator()){
                    attributeinstance.removeModifier(SPEED_MODIFIER_SPRINTING30);
                    attributeinstance.addTransientModifier(SPEED_MODIFIER_SPRINTING20);
                }

            }else{
                attributeinstance.removeModifier(SPEED_MODIFIER_SPRINTING20);
                attributeinstance.addTransientModifier(SPEED_MODIFIER_SPRINTING30);
            }
        }
    }

    public static void setSpeedFactor(float value){
        SPEED_MODIFIER_SPRINTING20 = new AttributeModifier(SPEED_MODIFIER_SPRINTING_UUID, "Sprinting speed boost",
                value, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
}

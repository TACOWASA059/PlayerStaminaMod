package com.github.tacowasa059.playerstaminamod.mixin.client;

import com.github.tacowasa059.playerstaminamod.client.StaminaHandler;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin {
    @Shadow
    protected abstract boolean hasEnoughImpulseToStartSprinting();
    @Shadow
    protected abstract boolean hasEnoughFoodToStartSprinting();
    @Shadow
    protected abstract boolean vehicleCanSprint(Entity p_265184_);
    @Inject(method="canStartSprinting", at=@At("HEAD"), cancellable = true)
    private void canStartSprinting(CallbackInfoReturnable<Boolean> cir) {
        LocalPlayer player = (LocalPlayer) (Object) this;
        cir.setReturnValue(!player.isSprinting() && this.hasEnoughImpulseToStartSprinting() && this.hasEnoughFoodToStartSprinting() && !player.isUsingItem() && !player.hasEffect(MobEffects.BLINDNESS)
                && (!player.isPassenger() ||this.vehicleCanSprint(player.getVehicle())) && !player.isFallFlying() && !StaminaHandler.isExhausted());
        cir.cancel();
    }

}

package com.github.tacowasa059.playerstaminamod.mixin.common;

import com.github.tacowasa059.playerstaminamod.common.Accessor.IPlayerStamina;
import com.github.tacowasa059.playerstaminamod.common.CommonConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(Player.class)
public class PlayerMixin implements IPlayerStamina {
    @Unique
    private final String NBT_KEY_STAMINA = "PLAYER_STAMINA";

    @Unique
    private static final EntityDataAccessor<Boolean> IS_EXHAUSTED = SynchedEntityData.defineId(Player.class, EntityDataSerializers.BOOLEAN);
    @Unique
    private static final EntityDataAccessor<Boolean> BELOW_MIDDLE = SynchedEntityData.defineId(Player.class, EntityDataSerializers.BOOLEAN);
    @Unique
    private static final EntityDataAccessor<Float> PLAYER_STAMINA = SynchedEntityData.defineId(Player.class, EntityDataSerializers.FLOAT);

    @Inject(method="defineSynchedData",at=@At("TAIL"))
    void defineSynchedData(CallbackInfo ci){
        Player player = (Player) (Object)this;
        player.getEntityData().define(IS_EXHAUSTED, false);
        player.getEntityData().define(BELOW_MIDDLE, false);
        player.getEntityData().define(PLAYER_STAMINA, CommonConfig.initStamina.get().floatValue());
    }

    @Override
    public void playerStaminaMod$addStamina(float value) {
        float stamina = Math.min(100f,  playerStaminaMod$getStamina() + value);
        playerStaminaMod$setStamina(stamina);
    }

    @Override
    public void playerStaminaMod$subtractStamina(float value) {
        float stamina = Math.max(0f, playerStaminaMod$getStamina() - value);
        playerStaminaMod$setStamina(stamina);
    }

    @Override
    public float playerStaminaMod$getStamina() {
        Player player = (Player)(Object)this;
        return player.getEntityData().get(PLAYER_STAMINA);
    }

    @Override
    public void playerStaminaMod$setStamina(float stamina) {
        Player player = (Player)(Object)this;
        player.getEntityData().set(PLAYER_STAMINA, stamina);
    }

    @Override
    public boolean playerStaminaMod$isExhausted() {
        Player player = (Player)(Object)this;
        return player.getEntityData().get(IS_EXHAUSTED);
    }

    @Override
    public void playerStaminaMod$setExhausted(boolean isExhausted) {
        Player player = (Player)(Object)this;
        player.getEntityData().set(IS_EXHAUSTED, isExhausted);
    }

    @Override
    public boolean playerStaminaMod$isBelowMiddle() {
        Player player = (Player)(Object)this;
        return player.getEntityData().get(BELOW_MIDDLE);
    }

    @Override
    public void playerStaminaMod$setBelowMiddle(boolean flag) {
        Player player = (Player)(Object)this;
        player.getEntityData().set(BELOW_MIDDLE, flag);
    }

    @Inject(method = "addAdditionalSaveData", at = @At("HEAD"))
    public void addAdditionalSaveData(CompoundTag compound, CallbackInfo ci) {
        compound.putFloat(NBT_KEY_STAMINA, playerStaminaMod$getStamina());
    }
    @Inject(method = "readAdditionalSaveData", at = @At("HEAD"))
    public void readAdditionalSaveData(CompoundTag compound, CallbackInfo ci) {
        if (compound.contains(NBT_KEY_STAMINA)) {
            playerStaminaMod$setStamina(compound.getFloat(NBT_KEY_STAMINA));
        }
    }
}

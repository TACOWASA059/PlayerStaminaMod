package com.github.tacowasa059.playerstaminamod.common.networks;

import com.github.tacowasa059.playerstaminamod.client.ClientSettingVariables;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class StaminaSyncPacket {

    private final float tickRecoveryRate;
    private final float tickSprintConsumptionRate;
    private final float jumpConsumption;
    private final float initStamina;
    private final float respawnStamina;
    private final float middleThreshold;

    private final float sprintSpeedMultiplier;

    public StaminaSyncPacket(float tickRecoveryRate, float tickSprintConsumptionRate, float jumpConsumption,
                             float initStamina,  float respawnStamina,
                             float middleThreshold, float sprintSpeedMultiplier) {
        this.tickRecoveryRate = tickRecoveryRate;
        this.tickSprintConsumptionRate = tickSprintConsumptionRate;
        this.jumpConsumption = jumpConsumption;
        this.initStamina = initStamina;
        this.respawnStamina = respawnStamina;
        this.middleThreshold = middleThreshold;
        this.sprintSpeedMultiplier = sprintSpeedMultiplier;
    }

    public static void encode(StaminaSyncPacket packet, FriendlyByteBuf buf) {
        buf.writeFloat(packet.tickRecoveryRate);
        buf.writeFloat(packet.tickSprintConsumptionRate);
        buf.writeFloat(packet.jumpConsumption);
        buf.writeFloat(packet.initStamina);
        buf.writeFloat(packet.respawnStamina);
        buf.writeFloat(packet.middleThreshold);
        buf.writeFloat(packet.sprintSpeedMultiplier);
    }

    public static StaminaSyncPacket decode(FriendlyByteBuf buf) {
        float tickRecoveryRate = buf.readFloat();
        float tickSprintConsumptionRate = buf.readFloat();
        float jumpConsumption = buf.readFloat();
        float initStamina = buf.readFloat();
        float respawnStamina = buf.readFloat();
        float middleThreshold = buf.readFloat();
        float sprintSpeedMultiplier = buf.readFloat();
        return new StaminaSyncPacket(tickRecoveryRate, tickSprintConsumptionRate, jumpConsumption,
                initStamina, respawnStamina, middleThreshold, sprintSpeedMultiplier);
    }

    public static void handle(StaminaSyncPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            // クライアント側で設定値を反映
            ClientSettingVariables.set(packet.tickRecoveryRate, packet.tickSprintConsumptionRate, packet.jumpConsumption,
                    packet.initStamina, packet.respawnStamina, packet.middleThreshold, packet.sprintSpeedMultiplier);
        });
        ctx.get().setPacketHandled(true);
    }
}

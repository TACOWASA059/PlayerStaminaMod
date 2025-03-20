package com.github.tacowasa059.playerstaminamod.common;

import net.minecraftforge.common.ForgeConfigSpec;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
public class CommonConfig {

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static ForgeConfigSpec.DoubleValue tickRecoveryRate;
    public static ForgeConfigSpec.DoubleValue tickSprintConsumptionRate;
    public static ForgeConfigSpec.DoubleValue jumpConsumption;
    public static ForgeConfigSpec.DoubleValue initStamina;
    public static ForgeConfigSpec.DoubleValue respawnStamina;

    public static ForgeConfigSpec.DoubleValue middleThreshold;

    public static ForgeConfigSpec.DoubleValue sprintSpeedMultiplier;

    static {
        BUILDER.push("Stamina Settings");

        tickRecoveryRate = BUILDER
                .comment("Stamina recovery rate per tick (default: 0.15)")
                .defineInRange("tickRecoveryRate", 0.15, 0.0, 100.0);

        tickSprintConsumptionRate = BUILDER
                .comment("Stamina consumption rate while sprinting per tick (default: 0.15)")
                .defineInRange("tickSprintConsumptionRate", 0.15, 0.0, 100.0);

        jumpConsumption = BUILDER
                .comment("Stamina consumption when jumping (default: 10.0)")
                .defineInRange("jumpConsumption", 10.0, 0.0, 100.0);

        initStamina = BUILDER
                .comment("Initial stamina when the player first loads the world (default: 100.0)")
                .defineInRange("initStamina", 100.0, 0.0, 100.0);


        respawnStamina = BUILDER
                .comment("Stamina after player respawn (default: 100.0)")
                .defineInRange("respawnStamina", 100.0, 0.0, 100.0);


        middleThreshold = BUILDER
                .comment("Threshold where stamina is considered to be in the middle range (default: 33.3)")
                .defineInRange("middleThreshold", 33.3f, 0.0, 100.0);

        sprintSpeedMultiplier = BUILDER
                .comment("Multiplier for player speed while sprinting in yellow zone (default: 0.66)")
                .defineInRange("sprintSpeedMultiplier", 0.66, 0.0, 1.0);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
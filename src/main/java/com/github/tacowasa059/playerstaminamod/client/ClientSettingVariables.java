package com.github.tacowasa059.playerstaminamod.client;

public class ClientSettingVariables {
    public static float tickRecoveryRate = 0.15f;
    public static float tickSprintConsumptionRate = 0.15f;
    public static float jumpConsumption = 10f;


    public static float initStamina = 100f;
    public static boolean initIsExhausted = false;

    public static float respawnStamina = 100f;

    public static boolean respawnIsExhausted = false;

    public static float MiddleThreshold = 33.3f;

    public static float sprintSpeedMultiplier = 0.66f;

    public static void set(float tickRecoveryRate, float tickSprintConsumptionRate, float jumpConsumption,
                           float initStamina, float respawnStamina,
                           float middleThreshold, float sprintSpeedMultiplier) {
        ClientSettingVariables.tickRecoveryRate = tickRecoveryRate;
        ClientSettingVariables.tickSprintConsumptionRate = tickSprintConsumptionRate;
        ClientSettingVariables.jumpConsumption = jumpConsumption;
        ClientSettingVariables.initStamina = initStamina;
        ClientSettingVariables.respawnStamina = respawnStamina;
        ClientSettingVariables.MiddleThreshold = middleThreshold;
        ClientSettingVariables.sprintSpeedMultiplier = sprintSpeedMultiplier;

        StaminaHandler.setStamina(initStamina);
        StaminaHandler.setExhausted(initIsExhausted);
        SprintSpeedHandler.setSpeedFactor(sprintSpeedMultiplier);
    }
}

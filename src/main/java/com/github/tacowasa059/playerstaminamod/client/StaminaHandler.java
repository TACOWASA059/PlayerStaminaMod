package com.github.tacowasa059.playerstaminamod.client;

public class StaminaHandler {

    private static float stamina = ClientSettingVariables.initStamina;


    private static boolean isExhausted = ClientSettingVariables.initIsExhausted;

    public static void add(float value){
        stamina = Math.min(100f, stamina + value);
    }
    public static void subtract(float value){
        stamina = Math.max(0f, stamina - value);
    }

    public static float getStamina() {
        return stamina;
    }

    public static void setStamina(float stamina) {
        StaminaHandler.stamina = stamina;
    }

    public static boolean isExhausted() {
        return isExhausted;
    }

    public static void setExhausted(boolean isExhausted) {
        StaminaHandler.isExhausted = isExhausted;
    }

}

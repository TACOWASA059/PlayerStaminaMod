package com.github.tacowasa059.playerstaminamod.common.Accessor;

public interface IPlayerStamina {
    void playerStaminaMod$addStamina(float value);

    void playerStaminaMod$subtractStamina(float value);

    float playerStaminaMod$getStamina();

    void playerStaminaMod$setStamina(float stamina);

    boolean playerStaminaMod$isExhausted();

    void playerStaminaMod$setExhausted(boolean isExhausted);

    boolean playerStaminaMod$isBelowMiddle();
    void playerStaminaMod$setBelowMiddle(boolean flag);
}

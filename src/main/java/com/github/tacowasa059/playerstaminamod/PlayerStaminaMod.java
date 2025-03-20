package com.github.tacowasa059.playerstaminamod;

import com.github.tacowasa059.playerstaminamod.common.CommonConfig;
import com.github.tacowasa059.playerstaminamod.common.networks.NetworkHandler;
import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(PlayerStaminaMod.MODID)
public class PlayerStaminaMod {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "playerstaminamod";

    @SuppressWarnings("removal")
    public PlayerStaminaMod() {
        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonConfig.SPEC);
        NetworkHandler.register();
    }

}

package com.github.tacowasa059.playerstaminamod.common.event;

import com.github.tacowasa059.playerstaminamod.PlayerStaminaMod;
import com.github.tacowasa059.playerstaminamod.common.StaminaCommand;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PlayerStaminaMod.MODID)
public class CommandRegistration {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        StaminaCommand.register(dispatcher);
    }
}

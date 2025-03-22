package com.github.tacowasa059.playerstaminamod.common;

import com.github.tacowasa059.playerstaminamod.common.event.SprintSpeedHandler;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class StaminaCommand {

    private static final SuggestionProvider<CommandSourceStack> PARAMETER_SUGGESTIONS = (context, builder) -> {
        builder.suggest("tickRecoveryRate");
        builder.suggest("tickSprintConsumptionRate");
        builder.suggest("jumpConsumption");
        builder.suggest("initStamina");
        builder.suggest("middleThreshold");
        builder.suggest("sprintSpeedMultiplier");
        return builder.buildFuture();
    };

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("stamina")
                        .requires(player -> player.hasPermission(2))
                .then(Commands.argument("parameter", StringArgumentType.string())
                        .suggests(PARAMETER_SUGGESTIONS)
                        .executes(context -> getStaminaConfig(context, StringArgumentType.getString(context, "parameter")))
                        .then(Commands.argument("value", DoubleArgumentType.doubleArg())
                                .executes(context -> setStaminaConfig(
                                        context,
                                        StringArgumentType.getString(context, "parameter"),
                                        DoubleArgumentType.getDouble(context, "value")
                                )))));
    }

    private static int getStaminaConfig(CommandContext<CommandSourceStack> context, String parameter) {
        double value;
        switch (parameter) {
            case "tickRecoveryRate" -> value = CommonConfig.tickRecoveryRate.get();
            case "tickSprintConsumptionRate" -> value = CommonConfig.tickSprintConsumptionRate.get();
            case "jumpConsumption" -> value = CommonConfig.jumpConsumption.get();
            case "initStamina" -> value = CommonConfig.initStamina.get();
            case "middleThreshold" -> value = CommonConfig.middleThreshold.get();
            case "sprintSpeedMultiplier" -> value = CommonConfig.sprintSpeedMultiplier.get();
            default -> {
                context.getSource().sendFailure(Component.literal("Unknown parameter: " + parameter));
                return 0;
            }
        }
        context.getSource().sendSuccess(()->Component.literal(parameter + " = " + value), false);
        return 1;
    }

    private static int setStaminaConfig(CommandContext<CommandSourceStack> context, String parameter, double value) {
        switch (parameter) {
            case "tickRecoveryRate" -> CommonConfig.tickRecoveryRate.set(value);
            case "tickSprintConsumptionRate" -> CommonConfig.tickSprintConsumptionRate.set(value);
            case "jumpConsumption" -> CommonConfig.jumpConsumption.set(value);
            case "initStamina" -> CommonConfig.initStamina.set(value);
            case "middleThreshold" -> CommonConfig.middleThreshold.set(value);
            case "sprintSpeedMultiplier" -> CommonConfig.sprintSpeedMultiplier.set(value);
            default -> {
                context.getSource().sendFailure(Component.literal("Unknown parameter: " + parameter));
                return 0;
            }
        }
        SprintSpeedHandler.setSpeedFactor(CommonConfig.sprintSpeedMultiplier.get().floatValue());
        context.getSource().sendSuccess(()->Component.literal("Set " + parameter + " to " + value), true);
        return 1;
    }
}

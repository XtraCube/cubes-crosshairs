package dev.xtracube.crosshairs;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import dev.xtracube.crosshairs.client.CrosshairOverlay;
import dev.xtracube.crosshairs.commands.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class CubesCrosshairClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        HudRenderCallback.EVENT.register(new CrosshairOverlay());

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {

            LiteralCommandNode<FabricClientCommandSource> velModNode = ClientCommandManager
                    .literal("cube:set_vel_mod")
                    .then(ClientCommandManager.argument("value", DoubleArgumentType.doubleArg(0.0, 1.0))
                            .executes(new SetVelocityModifierCommand()))
                    .build();

            LiteralCommandNode<FabricClientCommandSource> deltaScaleNode = ClientCommandManager
                    .literal("cube:set_delta_scale")
                    .then(ClientCommandManager.argument("value", DoubleArgumentType.doubleArg(-1.0, 1.0))
                            .executes(new SetDeltaScaleCommand()))
                    .build();

            LiteralCommandNode<FabricClientCommandSource> thicknessNode = ClientCommandManager
                    .literal("cube:set_thickness")
                    .then(ClientCommandManager.argument("value", FloatArgumentType.floatArg())
                            .executes(new SingleFloatShaderCommand("Thickness")))
                    .build();

            LiteralCommandNode<FabricClientCommandSource> featherNode = ClientCommandManager
                    .literal("cube:set_feather")
                    .then(ClientCommandManager.argument("value", FloatArgumentType.floatArg())
                            .executes(new SingleFloatShaderCommand("Feather")))
                    .build();

            LiteralCommandNode<FabricClientCommandSource> radiusNode = ClientCommandManager
                    .literal("cube:set_radius")
                    .then(ClientCommandManager.argument("value", FloatArgumentType.floatArg())
                            .executes(new SingleFloatShaderCommand("Radius")))
                    .build();

            LiteralCommandNode<FabricClientCommandSource> alphaNode = ClientCommandManager
                    .literal("cube:set_alpha")
                    .then(ClientCommandManager.argument("value", FloatArgumentType.floatArg(0.0f, 1.0f))
                            .executes(new SingleFloatShaderCommand("Alpha")))
                    .build();

            LiteralCommandNode<FabricClientCommandSource> saturationNode = ClientCommandManager
                    .literal("cube:set_saturation")
                    .then(ClientCommandManager.argument("value", FloatArgumentType.floatArg(0.0f, 1.0f))
                            .executes(new SingleFloatShaderCommand("Saturation")))
                    .build();

            LiteralCommandNode<FabricClientCommandSource> brightnessNode = ClientCommandManager
                    .literal("cube:set_brightness")
                    .then(ClientCommandManager.argument("value", FloatArgumentType.floatArg(0.0f, 1.0f))
                            .executes(new SingleFloatShaderCommand("Brightness")))
                    .build();

            LiteralCommandNode<FabricClientCommandSource> spinSpeedNode = ClientCommandManager
                    .literal("cube:set_spin_speed")
                    .then(ClientCommandManager.argument("value", FloatArgumentType.floatArg())
                            .executes(new SingleFloatShaderCommand("SpinSpeed")))
                    .build();

            dispatcher.getRoot().addChild(velModNode);
            dispatcher.getRoot().addChild(deltaScaleNode);
            dispatcher.getRoot().addChild(thicknessNode);
            dispatcher.getRoot().addChild(featherNode);
            dispatcher.getRoot().addChild(radiusNode);
            dispatcher.getRoot().addChild(alphaNode);
            dispatcher.getRoot().addChild(saturationNode);
            dispatcher.getRoot().addChild(brightnessNode);
            dispatcher.getRoot().addChild(spinSpeedNode);

        });

    }
}

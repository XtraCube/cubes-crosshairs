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
                    .literal("set_vel_mod")
                    .then(ClientCommandManager.argument("value", DoubleArgumentType.doubleArg(0.0, 1.0))
                            .executes(new SetVelocityModifierCommand()))
                    .build();

            LiteralCommandNode<FabricClientCommandSource> deltaScaleNode = ClientCommandManager
                    .literal("set_delta_scale")
                    .then(ClientCommandManager.argument("value", DoubleArgumentType.doubleArg(0.0, 1.0))
                            .executes(new SetDeltaScaleCommand()))
                    .build();

            LiteralCommandNode<FabricClientCommandSource> thicknessNode = ClientCommandManager
                    .literal("set_thickness")
                    .then(ClientCommandManager.argument("value", FloatArgumentType.floatArg())
                            .executes(new SingleFloatShaderCommand("Thickness")))
                    .build();

            LiteralCommandNode<FabricClientCommandSource> radiusNode = ClientCommandManager
                    .literal("set_radius")
                    .then(ClientCommandManager.argument("value", FloatArgumentType.floatArg())
                            .executes(new SingleFloatShaderCommand("Radius")))
                    .build();

            LiteralCommandNode<FabricClientCommandSource> alphaNode = ClientCommandManager
                    .literal("set_alpha")
                    .then(ClientCommandManager.argument("value", FloatArgumentType.floatArg(0.0f, 1.0f))
                            .executes(new SingleFloatShaderCommand("Alpha")))
                    .build();

            LiteralCommandNode<FabricClientCommandSource> saturationNode = ClientCommandManager
                    .literal("set_saturation")
                    .then(ClientCommandManager.argument("value", FloatArgumentType.floatArg(0.0f, 1.0f))
                            .executes(new SingleFloatShaderCommand("Saturation")))
                    .build();

            LiteralCommandNode<FabricClientCommandSource> brightnessNode = ClientCommandManager
                    .literal("set_brightness")
                    .then(ClientCommandManager.argument("value", FloatArgumentType.floatArg(0.0f, 1.0f))
                            .executes(new SingleFloatShaderCommand("Brightness")))
                    .build();

            dispatcher.getRoot().addChild(velModNode);
            dispatcher.getRoot().addChild(deltaScaleNode);
            dispatcher.getRoot().addChild(thicknessNode);
            dispatcher.getRoot().addChild(radiusNode);
            dispatcher.getRoot().addChild(alphaNode);
            dispatcher.getRoot().addChild(saturationNode);
            dispatcher.getRoot().addChild(brightnessNode);

        });

    }
}

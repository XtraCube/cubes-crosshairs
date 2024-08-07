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


            LiteralCommandNode<FabricClientCommandSource>[] nodes = new LiteralCommandNode[] {
                    ClientCommandManager
                            .literal("cube:set_vel_mod")
                            .then(ClientCommandManager.argument("value", DoubleArgumentType.doubleArg(0.0, 1.0))
                                    .executes(new SetVelocityModifierCommand()))
                            .build(),

                    ClientCommandManager
                            .literal("cube:set_delta_scale")
                            .then(ClientCommandManager.argument("value", DoubleArgumentType.doubleArg(-1.0, 1.0))
                                    .executes(new SetDeltaScaleCommand()))
                            .build(),

                    ClientCommandManager
                            .literal("cube:set_spin_speed")
                            .then(ClientCommandManager.argument("value", FloatArgumentType.floatArg())
                                    .executes(new SingleFloatShaderCommand("SpinSpeed")))
                            .build(),

                    ClientCommandManager
                            .literal("cube:set_radius")
                            .then(ClientCommandManager.argument("value", FloatArgumentType.floatArg(0.0f, 1.0f))
                                    .executes(new SingleFloatShaderCommand("Radius")))
                            .build(),

                    ClientCommandManager
                            .literal("cube:set_thickness")
                            .then(ClientCommandManager.argument("value", FloatArgumentType.floatArg(0.0f, 1.0f))
                                    .executes(new SingleFloatShaderCommand("Thickness")))
                            .build(),

                    ClientCommandManager
                            .literal("cube:set_hue")
                            .then(ClientCommandManager.argument("value", FloatArgumentType.floatArg(0.0f, 1.0f))
                                    .executes(new SingleFloatShaderCommand("Hue")))
                            .build(),

                    ClientCommandManager
                            .literal("cube:set_saturation")
                            .then(ClientCommandManager.argument("value", FloatArgumentType.floatArg(0.0f, 1.0f))
                                    .executes(new SingleFloatShaderCommand("Saturation")))
                            .build(),

                    ClientCommandManager
                            .literal("cube:set_brightness")
                            .then(ClientCommandManager.argument("value", FloatArgumentType.floatArg(0.0f, 1.0f))
                                    .executes(new SingleFloatShaderCommand("Brightness")))
                            .build(),

                    ClientCommandManager
                            .literal("cube:set_alpha")
                            .then(ClientCommandManager.argument("value", FloatArgumentType.floatArg(0.0f, 1.0f))
                                    .executes(new SingleFloatShaderCommand("BaseAlpha")))
                            .build(),

                    ClientCommandManager
                            .literal("cube:set_feather")
                            .then(ClientCommandManager.argument("value", FloatArgumentType.floatArg(0.0f, 1.0f))
                                    .executes(new SingleFloatShaderCommand("Feather")))
                            .build(),

                    ClientCommandManager
                            .literal("cube:set_progress_offset")
                            .then(ClientCommandManager.argument("value", FloatArgumentType.floatArg(-2.0f, 2.0f))
                                    .executes(new SingleFloatShaderCommand("ProgressOffset")))
                            .build(),

                    ClientCommandManager
                            .literal("cube:set_disabled_alpha")
                            .then(ClientCommandManager.argument("value", FloatArgumentType.floatArg(0.0f, 1.0f))
                                    .executes(new SingleFloatShaderCommand("DisabledAlpha")))
                            .build(),


            };

            for (LiteralCommandNode<FabricClientCommandSource> node : nodes) {
                dispatcher.getRoot().addChild(node);
            }

        });

    }
}

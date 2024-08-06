package dev.xtracube.crosshairs.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.context.CommandContext;
import dev.xtracube.crosshairs.client.CrosshairOverlay;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;

public class SetVelocityModifierCommand implements Command<FabricClientCommandSource> {

    @Override
    public int run(CommandContext<FabricClientCommandSource> context) {

        if (CrosshairOverlay.Instance != null) {
            CrosshairOverlay.Instance.VelocityModifier = DoubleArgumentType.getDouble(context, "value");
            context.getSource().sendFeedback(Text.literal("Set velocity modifier successfully"));
            return 1;
        }

        context.getSource().sendFeedback(Text.literal("No crosshair overlay instance"));
        return 1;
    }
}

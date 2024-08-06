package dev.xtracube.crosshairs.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.context.CommandContext;
import dev.xtracube.crosshairs.ShaderSupplier;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;

public class SingleFloatShaderCommand implements Command<FabricClientCommandSource> {

    private final String commandName;

    public SingleFloatShaderCommand(String name) {
        this.commandName = name;
    }

    @Override
    public int run(CommandContext<FabricClientCommandSource> context) {
        ShaderSupplier.INSTANCE.get().getUniform(commandName).set(FloatArgumentType.getFloat(context, "value"));
        context.getSource().sendFeedback(Text.literal("Set "+commandName+" successfully"));
        return 1;
    }
}

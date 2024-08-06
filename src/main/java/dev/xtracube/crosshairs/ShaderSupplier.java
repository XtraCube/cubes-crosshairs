package dev.xtracube.crosshairs;

import net.fabricmc.fabric.impl.client.rendering.FabricShaderProgram;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.util.Objects;
import java.util.function.Supplier;

public class ShaderSupplier implements Supplier<ShaderProgram> {

    public static ShaderSupplier INSTANCE = new ShaderSupplier();
    private static ShaderProgram shader;

    @Override
    public ShaderProgram get() {
        try {
            if (shader == null) {
                shader = new FabricShaderProgram(
                        Objects.requireNonNull(MinecraftClient.getInstance()).getResourceManager(),
                        Identifier.of("minecraft", "crosshair"),
                        VertexFormats.POSITION);
            }
            return shader;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

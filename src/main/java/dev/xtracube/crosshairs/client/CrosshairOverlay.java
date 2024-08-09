package dev.xtracube.crosshairs.client;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.xtracube.crosshairs.ShaderSupplier;
import dev.xtracube.crosshairs.mixin.ClientPlayerInteractionManagerMixin;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;
import org.joml.Matrix4f;

public class CrosshairOverlay implements HudRenderCallback {

    public static CrosshairOverlay Instance;

    public double VelocityModifier = 0.85;
    public double DeltaScale = 0.1;

    private double lastMouseX = 0;
    private double lastMouseY = 0;

    private double velocityX = 0;
    private double velocityY = 0;

    public CrosshairOverlay() {
        Instance = this;
    }

    @Override
    public void onHudRender(DrawContext drawContext, RenderTickCounter tickCounter) {

        MinecraftClient mc = MinecraftClient.getInstance();

        double mouseX = mc.mouse.getX();
        double mouseY = mc.mouse.getY();

        double mouseDeltaX = mouseX - lastMouseX;
        double mouseDeltaY = mouseY - lastMouseY;

        lastMouseX = mouseX;
        lastMouseY = mouseY;

        velocityX += mouseDeltaX * DeltaScale;
        velocityY -= mouseDeltaY * DeltaScale;

        velocityX *= VelocityModifier;
        velocityY *= VelocityModifier;

        int x1 = 0;
        int y1 = 0;

        int x2 = drawContext.getScaledWindowWidth();
        int y2 = drawContext.getScaledWindowHeight();

        int z = 0;

        // drawContext.drawCenteredTextWithShadow(mc.textRenderer, String.valueOf(), x2/2, y2/2, 0xFFFFFFFF);

        var shader = ShaderSupplier.INSTANCE.get();
        shader.getUniform("Offset").set(new float[] {(float)velocityX, (float)velocityY});

        float cooldown = 1.0f;

        if (mc.interactionManager != null && mc.interactionManager.isBreakingBlock()) {
            cooldown = 1f - ((ClientPlayerInteractionManagerMixin) mc.interactionManager ).getCurrentBreakingProgress();
        }
        else if (mc.player != null) {
            cooldown = mc.player.getAttackCooldownProgress(mc.player.getItemUseTime()+tickCounter.getTickDelta(true));
        }

        shader.getUniform("Cooldown").set(cooldown);

        if (mc.targetedEntity != null && mc.targetedEntity.isAttackable()) {
            shader.getUniform("Saturation").set(.75f);
        }
        else {
            shader.getUniform("Saturation").set(0f);
        }

        Matrix4f matrix4f = drawContext.getMatrices().peek().getPositionMatrix();
        BufferBuilder bufferBuilder = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION);
        bufferBuilder.vertex(matrix4f, (float)x1, (float)y1, (float)z);
        bufferBuilder.vertex(matrix4f, (float)x1, (float)y2, (float)z);
        bufferBuilder.vertex(matrix4f, (float)x2, (float)y2, (float)z);
        bufferBuilder.vertex(matrix4f, (float)x2, (float)y1, (float)z);

        RenderSystem.setShader(ShaderSupplier.INSTANCE);
        RenderSystem.enableBlend();
        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
        RenderSystem.disableBlend();

    }
}

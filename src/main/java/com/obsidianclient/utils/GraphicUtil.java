/**
 * This file is part of Obsidian Client.
 * Copyright (C) 2022  Alexander Richter
 *
 * Obsidian Client is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Obsidian Client is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Obsidian Client.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.obsidianclient.utils;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class GraphicUtil {
	
	private static final Minecraft mc = Minecraft.getMinecraft();

	//To check if the resolution changed:
	private static double oldResolutionWidth;
	private static double oldResolutionHeight;
	
	//ResourceLocations for drawing the container background:
	private static final ResourceLocation containerTopLeft = new ResourceLocation("obsidianclient/ContainerBackground/edge1.png");
	private static final ResourceLocation containerTopRight = new ResourceLocation("obsidianclient/ContainerBackground/edge2.png");
	private static final ResourceLocation containerBottomRight = new ResourceLocation("obsidianclient/ContainerBackground/edge3.png");
	private static final ResourceLocation containerBottomLeft = new ResourceLocation("obsidianclient/ContainerBackground/edge4.png");
	private static final ResourceLocation containerTop = new ResourceLocation("obsidianclient/ContainerBackground/line1.png");
	private static final ResourceLocation containerRight = new ResourceLocation("obsidianclient/ContainerBackground/line2.png");
	private static final ResourceLocation containerBottom = new ResourceLocation("obsidianclient/ContainerBackground/line3.png");
	private static final ResourceLocation containerLeft = new ResourceLocation("obsidianclient/ContainerBackground/line4.png");

	/**
	 * Checks if the display resolution changed. Returns true if it did.
	 */
	public static boolean hasResolutionChanged() {
		ScaledResolution resolution = new ScaledResolution(mc);
		if (resolution.getScaledWidth_double() != oldResolutionWidth || resolution.getScaledHeight_double() != oldResolutionHeight) {
			oldResolutionWidth = resolution.getScaledWidth_double();
			oldResolutionHeight = resolution.getScaledHeight_double();
			return true;
		}
		return false;
	}

	/**
	 * Draws a container background like the one in the player inventory.
	 */
	public static void drawContainerBackground(double x, double y, double width, double height) {

		//Clearing the Screen with White:
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

		//Top-Border:
		mc.getTextureManager().bindTexture(containerTop);
		drawScaledCustomSizeModalRect(x + 4, y, 0, 0, 1, 4, width - 12, 4, 1, 4);
		
		//Bottom-Border:
		mc.getTextureManager().bindTexture(containerBottom);
		drawScaledCustomSizeModalRect(x + 4, y + height - 8, 0, 0, 1, 4, width - 12, 4, 1, 4);
		
		//Left-Border:
		mc.getTextureManager().bindTexture(containerLeft);
		drawScaledCustomSizeModalRect(x, y + 4, 0, 0, 4, 1, 4, height - 12, 4, 1);
		
		//Right-Border:
		mc.getTextureManager().bindTexture(containerRight);
		drawScaledCustomSizeModalRect(x + width - 8, y + 4, 0, 0, 4, 1, 4, height - 12, 4, 1);
		
		//Top-Left Corner:
		mc.getTextureManager().bindTexture(containerTopLeft);
		drawModalRectWithCustomSizedTexture(x, y, 0, 0, 4, 4, 4, 4);
		
		//Top-Right Corner:
		mc.getTextureManager().bindTexture(containerTopRight);
		drawModalRectWithCustomSizedTexture(x + width - 8, y, 0, 0, 4, 4, 4, 4);
		
		//Bottom-Left Corner:
		mc.getTextureManager().bindTexture(containerBottomLeft);
		drawModalRectWithCustomSizedTexture(x, y + height - 8, 0, 0, 4, 4, 4, 4);
		
		//Bottom-Right Corner:
		mc.getTextureManager().bindTexture(containerBottomRight);
		drawModalRectWithCustomSizedTexture(x + width - 8, y + height - 8, 0, 0, 4, 4, 4, 4);
		
		//Main-Body:
		drawRect(x + 4, y + 4, x + width - 8, y + height - 8, new Color(198, 198, 198, 255).getRGB());
	}

	/**
	 * Draws a container background with a hole in the middle like in the Achievements Screen.
	 */
	public static void drawHollowContainerBackground(double x, double y, double width, double height, double paddingTop, double paddingBottom, double paddingLeft, double paddingRight) {
		//Draws a basic container background:
		drawContainerBackground(x, y, width, height);
		
		//Left:
		drawVerticalLine(x + 7 + paddingLeft - 1, y + 7 + paddingTop, y + height - 11 - paddingBottom, Color.BLACK.getRGB());
		
		//Top:
		drawHorizontalLine(x + 7 + paddingLeft - 1, x + width - 11 - paddingRight, y + 7 + paddingTop, Color.BLACK.getRGB());
		
		//Right:
		drawVerticalLine(x + width - 11 - paddingRight, y + 7 + paddingTop, y + height - 11 - paddingBottom, Color.WHITE.getRGB());
		
		//Bottom:
		drawHorizontalLine(x + 7 + paddingLeft - 1, x + width - 11 - paddingRight, y + height - 11 - paddingBottom, Color.WHITE.getRGB());
		
		//Top-Right Corner:
		drawRect(x + width - 11 - paddingRight, y + 7 + paddingTop, x + width - 10 - paddingRight, y + 8 + paddingTop, new Color(139, 139, 139).getRGB());
		
		//Bottom-Left Corner:
		drawRect(x + 7 + paddingLeft - 1, y + height - 11 - paddingBottom, x + 8 + paddingLeft - 1, y + height - 10 - paddingBottom, new Color(139, 139, 139).getRGB());
		
		//Draws the Background:
		drawRect(x + 8 + paddingLeft - 1, y + 8 + paddingTop, x + width - 11 - paddingRight, y + height - 11 - paddingBottom, new Color(55, 55, 55).getRGB());

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	}

	/**
	 * Renders the specified text to the screen, center-aligned, without shadow.
	 */
	public static void drawCenteredString(FontRenderer fontRenderer, String text, float x, float y, int color) {
		fontRenderer.drawString(text, x - fontRenderer.getStringWidth(text) / 2.0F, y, color, false);
	}

	/**
	 * Renders the specified text to the screen, center-aligned, with shadow.
	 */
	public static void drawCenteredStringWithShadow(FontRenderer fontRenderer, String text, float x, float y, int color) {
		fontRenderer.drawString(text, x - fontRenderer.getStringWidth(text) / 2.0F, y, color, true);
	}

	/**
	 * Draw a 1 pixel wide horizontal line.
	 */
	public static void drawHorizontalLine(double startX, double endX, double y, int color)
    {
        if (endX < startX)
        {
            double d = startX;
            startX = endX;
            endX = d;
        }

        drawRect(startX, y, endX + 1, y + 1, color);
    }

	/**
	 * Draw a 1 pixel wide vertical line.
	 */
	public static void drawVerticalLine(double x, double startY, double endY, int color)
    {
        if (endY < startY)
        {
            double d = startY;
            startY = endY;
            endY = d;
        }

        drawRect(x, startY + 1, x + 1, endY, color);
    }

	/**
	 * Draw a hollow box.
	 */
	public static void drawBox(double x, double y, double width, double height, int color, boolean shouldRenderOutside) {
		if (shouldRenderOutside) {
			//Left:
			drawVerticalLine(x - 1, y - 1, y + height, color);
			//Right:
			drawVerticalLine(x + width, y - 1, y + height, color);
			//Top:
			drawHorizontalLine(x - 1, x + width, y - 1, color);
			//Bottom:
			drawHorizontalLine(x - 1, x + width, y + height, color);
		} else {
			//Left:
			drawVerticalLine(x, y, y + height, color);
			//Right:
			drawVerticalLine(x + width - 1, y, y + height, color);
			//Top:
			drawHorizontalLine(x, x + width - 1, y, color);
			//Bottom:
			drawHorizontalLine(x, x + width - 1, y + height - 1, color);
		}
	}

	/**
	 * Draws a solid color rectangle with the specified coordinates and color (ARGB format).
	 */
	public static void drawRect(double x, double y, double width, double height, int color) {

		double d;
		if (x < width) {
			d = x;
			x = width;
			width = d;
		}

		if (y < height) {
			d = y;
			y = height;
			height = d;
		}

		float f3 = (float)(color >> 24 & 255) / 255.0F;
		float f = (float)(color >> 16 & 255) / 255.0F;
		float f1 = (float)(color >> 8 & 255) / 255.0F;
		float f2 = (float)(color & 255) / 255.0F;

		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer renderer = tessellator.getWorldRenderer();

		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.color(f, f1, f2, f3);

		renderer.begin(7, DefaultVertexFormats.POSITION);
		renderer.pos(x, height, 0.0D).endVertex();
		renderer.pos(width, height, 0.0D).endVertex();
		renderer.pos(width, y, 0.0D).endVertex();
		renderer.pos(x, y, 0.0D).endVertex();
		tessellator.draw();

		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();

	}

	/**
	 * Draws a textured rectangle at z = 0.
	 */
	public static void drawTexturedModalRect(double x, double y, double textureX, double textureY, double width, double height) {

		float f = 0.00390625F;
		float f1 = 0.00390625F;

		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer renderer = tessellator.getWorldRenderer();

		renderer.begin(7, DefaultVertexFormats.POSITION_TEX);
		renderer.pos(x, y + height, 0.0D).tex(textureX * f, (textureY + height) * f1).endVertex();
		renderer.pos(x + width, y + height, 0.0D).tex((textureX + width) * f, (textureY + height) * f1).endVertex();
		renderer.pos(x + width, y, 0.0D).tex((textureX + width) * f, textureY * f1).endVertex();
		renderer.pos(x, y, 0.0D).tex(textureX * f, textureY * f1).endVertex();
		tessellator.draw();

	}

	/**
	 * Draws a textured rectangle at z = 0.
	 */
	public static void drawModalRectWithCustomSizedTexture(double x, double y, float u, float v, float width, float height, float textureWidth, float textureHeight) {

		float f = 1.0F / textureWidth;
		float f1 = 1.0F / textureHeight;

		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer renderer = tessellator.getWorldRenderer();

		renderer.begin(7, DefaultVertexFormats.POSITION_TEX);
		renderer.pos(x, y + height, 0.0D).tex(u * f, (v + height) * f1).endVertex();
		renderer.pos(x + width, y + height, 0.0D).tex((u + width) * f, (v + height) * f1).endVertex();
		renderer.pos(x + width, y, 0.0D).tex((u + width) * f, v * f1).endVertex();
		renderer.pos(x, y, 0.0D).tex(u * f, v * f1).endVertex();
		tessellator.draw();

	}

	/**
	 * Draws a scaled, textured, tiled modal rect at z = 0.
	 */
	public static void drawScaledCustomSizeModalRect(double x, double y, float u, float v, float uWidth, float vHeight, double width, double height, float tileWidth, float tileHeight) {

		float f = 1.0F / tileWidth;
		float f1 = 1.0F / tileHeight;

		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer renderer = tessellator.getWorldRenderer();

		renderer.begin(7, DefaultVertexFormats.POSITION_TEX);
		renderer.pos(x, y + height, 0.0D).tex(u * f, (v + vHeight) * f1).endVertex();
		renderer.pos(x + width, y + height, 0.0D).tex((u + uWidth) * f, (v + vHeight) * f1).endVertex();
		renderer.pos(x + width, y, 0.0D).tex((u + uWidth) * f, v * f1).endVertex();
		renderer.pos(x, y, 0.0D).tex(u * f, v * f1).endVertex();
		tessellator.draw();

	}

}

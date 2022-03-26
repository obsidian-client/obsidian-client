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

package com.obsidianclient.modules.impl;

import java.awt.Color;

import com.obsidianclient.ObsidianClient;
import com.obsidianclient.modules.ModuleDraggable;
import com.obsidianclient.modules.config.impl.ConfigPosition;
import com.obsidianclient.utils.GraphicUtil;
import com.obsidianclient.utils.MathUtil;
import com.obsidianclient.utils.Savable;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class ModulePosition extends ModuleDraggable {

	//The different settings for this module:
	@Savable
	public static boolean useClassicDesign = false;
	@Savable
	public static int accuracy = 0;
	@Savable
	public static boolean isHorizontalMode = false;
	@Savable
	public static boolean useSeparatorsInHorizontalMode = true;

	public ModulePosition() {
		super("Position Display", "Displays your current position.",
				false, 6,
				new ResourceLocation("obsidianclient/xyz.png"),
				null,
				ObsidianClient.getClient().getAnchorUtil().getTopLeft(),
				0, 0,
				0, 0);
		this.setOptionsScreen(new ConfigPosition(this));
	}

	//Rendering the normal version of the module:
	@Override
	public void renderWidget() {
		if (isHorizontalMode) {

			//Choosing the right separator:
			String separator;
			if (useSeparatorsInHorizontalMode) {
				separator = " | ";
			} else {
				separator = " ";
			}

			//Figuring out the length of the module:
			int length = Minecraft.getMinecraft().fontRendererObj.getStringWidth(this.getXPositionString(accuracy) + separator + this.getYPositionString(accuracy) + separator + this.getZPositionString(accuracy));

			//Making sure the boundaries are correct:
			if (this.getWidth() != length) {
				this.setWidth(length + 1);
			}
			if (this.getHeight() != 10) {
				this.setHeight(10);
			}

			//Drawing the background:
			GraphicUtil.drawRect(this.getXInt(), this.getYInt(), this.getXInt() + length + 1, this.getYInt() + this.getHeight(), ObsidianClient.GRAY_OVERLAY_COLOR);

			//Drawing the text:
			Minecraft.getMinecraft().fontRendererObj.drawString(this.getXPositionString(accuracy) + separator + this.getYPositionString(accuracy) + separator + this.getZPositionString(accuracy), this.getXInt() + 1, this.getYInt() + 1, Color.WHITE.getRGB(), false);

		} else {

			//Figuring out the length of the module:
			int length = MathUtil.getLargestValue(Minecraft.getMinecraft().fontRendererObj.getStringWidth(this.getXPositionString(accuracy)), Minecraft.getMinecraft().fontRendererObj.getStringWidth(this.getYPositionString(accuracy)), Minecraft.getMinecraft().fontRendererObj.getStringWidth(this.getZPositionString(accuracy)));

			//Making sure the boundaries are correct:
			if (this.getWidth() != length) {
				this.setWidth(length + 1);
			}
			if (this.getHeight() != 30) {
				this.setHeight(30);
			}

			//Drawing the background:
			GraphicUtil.drawRect(this.getXInt(), this.getYInt(), this.getXInt() + length + 1, this.getYInt() + this.getHeight(), ObsidianClient.GRAY_OVERLAY_COLOR);

			//Drawing the text:
			Minecraft.getMinecraft().fontRendererObj.drawString(this.getXPositionString(accuracy), this.getXInt() + 1, this.getYInt() + 1, Color.WHITE.getRGB(), false);
			Minecraft.getMinecraft().fontRendererObj.drawString(this.getYPositionString(accuracy), this.getXInt() + 1, this.getYInt() + 11, Color.WHITE.getRGB(), false);
			Minecraft.getMinecraft().fontRendererObj.drawString(this.getZPositionString(accuracy), this.getXInt() + 1, this.getYInt() + 21, Color.WHITE.getRGB(), false);

		}
	}

	//Rendering the dummy version used in the HUD Settings Screen:
	@Override
	public void renderDummy() {

		//Choosing the right text:
		String txtX;
		String txtY;
		String txtZ;
		if (useClassicDesign) {
			txtX = "[X] ---";
			txtY = "[Y] ---";
			txtZ = "[Z] ---";
		} else {
			txtX = "X: ---";
			txtY = "Y: ---";
			txtZ = "Z: ---";
		}

		if (isHorizontalMode) {

			//Choosing the right separator:
			String separator;
			if (useSeparatorsInHorizontalMode) {
				separator = " | ";
			} else {
				separator = " ";
			}

			//Figuring out the length of the module:
			int length = Minecraft.getMinecraft().fontRendererObj.getStringWidth(txtX + separator + txtY + separator + txtZ);

			//Making sure the boundaries are correct:
			if (this.getWidth() != length) {
				this.setWidth(length + 1);
			}
			if (this.getHeight() != 10) {
				this.setHeight(10);
			}

			//Drawing the background:
			GraphicUtil.drawRect(this.getXInt(), this.getYInt(), this.getXInt() + this.getWidth() + 1, this.getYInt() + this.getHeight(), ObsidianClient.GRAY_OVERLAY_COLOR);

			//Drawing the text:
			Minecraft.getMinecraft().fontRendererObj.drawString(txtX + separator + txtY + separator + txtZ, this.getXInt() + 1, this.getYInt() + 1, Color.WHITE.getRGB(), false);

		} else {

			//Figuring out the length of the module:
			int length = MathUtil.getLargestValue(Minecraft.getMinecraft().fontRendererObj.getStringWidth(txtX), Minecraft.getMinecraft().fontRendererObj.getStringWidth(txtY), Minecraft.getMinecraft().fontRendererObj.getStringWidth(txtZ));

			//Making sure the boundaries are correct:
			if (this.getWidth() != length) {
				this.setWidth(length + 1);
			}
			if (this.getHeight() != 30) {
				this.setHeight(30);
			}

			//Drawing the background:
			GraphicUtil.drawRect(this.getXInt(), this.getYInt(), this.getXInt() + length + 1, this.getYInt() + this.getHeight(), ObsidianClient.GRAY_OVERLAY_COLOR);

			//Drawing the text:
			Minecraft.getMinecraft().fontRendererObj.drawString(txtX, this.getXInt() + 1, this.getYInt() + 1, Color.WHITE.getRGB(), false);
			Minecraft.getMinecraft().fontRendererObj.drawString(txtY, this.getXInt() + 1, this.getYInt() + 11, Color.WHITE.getRGB(), false);
			Minecraft.getMinecraft().fontRendererObj.drawString(txtZ, this.getXInt() + 1, this.getYInt() + 21, Color.WHITE.getRGB(), false);

		}
	}

	//Creates a String that contains the x position of the player,
	//it also features a classic design and different accuracy levels:
	private String getXPositionString(int accuracy) {
		String prefix;
		if (useClassicDesign) {
			prefix = "[X] ";
		} else {
			prefix = "X: ";
		}

		if (accuracy == 1) {
			return String.format(prefix + "%.1f", Minecraft.getMinecraft().getRenderViewEntity().posX);
		} else if (accuracy == 2) {
			return String.format(prefix + "%.2f", Minecraft.getMinecraft().getRenderViewEntity().posX);
		} else if (accuracy == 3) {
			return String.format(prefix + "%.3f", Minecraft.getMinecraft().getRenderViewEntity().posX);
		}
		return String.format(prefix + "%.0f", Minecraft.getMinecraft().getRenderViewEntity().posX);
	}

	//Creates a String that contains the y position of the player,
	//it also features a classic design and different accuracy levels:
	private String getYPositionString(int accuracy) {
		String prefix;
		if (useClassicDesign) {
			prefix = "[Y] ";
		} else {
			prefix = "Y: ";
		}

		if (accuracy == 1) {
			return String.format(prefix + "%.1f", Minecraft.getMinecraft().getRenderViewEntity().getEntityBoundingBox().minY);
		} else if (accuracy == 2) {
			return String.format(prefix + "%.2f", Minecraft.getMinecraft().getRenderViewEntity().getEntityBoundingBox().minY);
		} else if (accuracy == 3) {
			return String.format(prefix + "%.3f", Minecraft.getMinecraft().getRenderViewEntity().getEntityBoundingBox().minY);
		}
		return String.format(prefix + "%.0f", Minecraft.getMinecraft().getRenderViewEntity().getEntityBoundingBox().minY);
	}

	//Creates a String that contains the z position of the player,
	//it also features a classic design and different accuracy levels:
	private String getZPositionString(int accuracy) {
		String prefix;
		if (useClassicDesign) {
			prefix = "[Z] ";
		} else {
			prefix = "Z: ";
		}

		if (accuracy == 1) {
			return String.format(prefix + "%.1f", Minecraft.getMinecraft().getRenderViewEntity().posZ);
		} else if (accuracy == 2) {
			return String.format(prefix + "%.2f", Minecraft.getMinecraft().getRenderViewEntity().posZ);
		} else if (accuracy == 3) {
			return String.format(prefix + "%.3f", Minecraft.getMinecraft().getRenderViewEntity().posZ);
		}
		return String.format(prefix + "%.0f", Minecraft.getMinecraft().getRenderViewEntity().posZ);
	}

}

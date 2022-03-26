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
import com.obsidianclient.utils.GraphicUtil;
import com.obsidianclient.utils.Savable;
import com.obsidianclient.modules.ModuleDraggable;
import com.obsidianclient.modules.config.impl.ConfigFPS;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class ModuleFPS extends ModuleDraggable {

	//The different settings for this module:
	@Savable
	public static boolean showModuleName = true;
	@Savable
	public static boolean useClassicDesign = false;
	
	public ModuleFPS() {
		super("FPS Display", "Displays your current fps.",
				false, 4,
				new ResourceLocation("obsidianclient/fps.png"),
				null,
				ObsidianClient.getClient().getAnchorUtil().getTopLeft(),
				0, 0,
				60, 10);
		this.setOptionsScreen(new ConfigFPS(this));
	}
	
	@Override
	public void renderWidget() {
		
		//Finding out the right prefix:
		String prefix;
		if (showModuleName) {
			
			if (useClassicDesign) {
				prefix = "[FPS] ";
				
			} else {
				prefix = "FPS: ";
			}
			
		} else {
			prefix = "";
		}
		
		
		GraphicUtil.drawRect(this.getXInt(), this.getYInt(), this.getXInt() + Minecraft.getMinecraft().fontRendererObj.getStringWidth(prefix + Minecraft.getDebugFPS()) + 1, this.getYInt() + this.getHeight(), ObsidianClient.GRAY_OVERLAY_COLOR);
		Minecraft.getMinecraft().fontRendererObj.drawString(prefix + Minecraft.getDebugFPS(), this.getXInt() + 1, this.getYInt() + 1, Color.WHITE.getRGB(), false);
	}
	
	@Override
	public void renderDummy() {
		
		//Finding out the right prefix:
		String prefix;
		if (showModuleName) {
					
			if (useClassicDesign) {
				prefix = "[FPS]";
						
			} else {
				prefix = "FPS:";
			}
					
		} else {
			prefix = "";
		}
		
		GraphicUtil.drawRect(this.getXInt(), this.getYInt(), this.getXInt() + this.getWidth() + 1, this.getYInt() + this.getHeight(), ObsidianClient.GRAY_OVERLAY_COLOR);
		Minecraft.getMinecraft().fontRendererObj.drawString(prefix + " " + "---", this.getXInt() + 1, this.getYInt() + 1, Color.WHITE.getRGB(), false);
	}

}

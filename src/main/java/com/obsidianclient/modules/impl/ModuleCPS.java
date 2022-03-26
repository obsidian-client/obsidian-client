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
import java.util.ArrayList;
import java.util.List;

import com.obsidianclient.modules.config.impl.ConfigCPS;
import com.obsidianclient.utils.GraphicUtil;
import com.obsidianclient.utils.Savable;
import org.lwjgl.input.Mouse;

import com.obsidianclient.ObsidianClient;
import com.obsidianclient.modules.ModuleDraggable;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class ModuleCPS extends ModuleDraggable {

	//The different settings for this module:
	@Savable
	public static boolean showModuleName = true;
	@Savable
	public static boolean useClassicDesign = false;

	private List<Long> clicks = new ArrayList<Long>();
	private boolean wasPressed;
	private long lastPressed;
	
	public ModuleCPS() {
		super("CPS Display", "Displays your current cps.",
				false, 5,
				new ResourceLocation("obsidianclient/cps.png"),
				null,
				ObsidianClient.getClient().getAnchorUtil().getTopLeft(),
				0, 0,
				60, 10);
		this.setOptionsScreen(new ConfigCPS(this));
	}

	@Override
	public void update() {
		this.updateCps();
	}

	@Override
	public void renderWidget() {

		//Finding out the right prefix:
		String prefix;
		if (showModuleName) {

			if (useClassicDesign) {
				prefix = "[CPS] ";

			} else {
				prefix = "CPS: ";
			}

		} else {
			prefix = "";
		}

		GraphicUtil.drawRect(this.getXInt(), this.getYInt(), this.getXInt() + Minecraft.getMinecraft().fontRendererObj.getStringWidth(prefix + this.getCps()) + 1, this.getYInt() + this.getHeight(), ObsidianClient.GRAY_OVERLAY_COLOR);
		Minecraft.getMinecraft().fontRendererObj.drawString(prefix + this.getCps(), this.getXInt() + 1, this.getYInt() + 1, Color.WHITE.getRGB(), false);
	}
	
	@Override
	public void renderDummy() {

		//Finding out the right prefix:
		String prefix;
		if (showModuleName) {

			if (useClassicDesign) {
				prefix = "[CPS]";

			} else {
				prefix = "CPS:";
			}

		} else {
			prefix = "";
		}

		GraphicUtil.drawRect(this.getXInt(), this.getYInt(), this.getXInt() + this.getWidth() + 1, this.getYInt() + this.getHeight(), ObsidianClient.GRAY_OVERLAY_COLOR);
		Minecraft.getMinecraft().fontRendererObj.drawString(prefix + " " + "---", this.getXInt() + 1, this.getYInt() + 1, Color.WHITE.getRGB(), false);
	}
	
	private void updateCps() {
		final boolean pressed = Mouse.isButtonDown(0);
		
		if (pressed != this.wasPressed) {
			this.lastPressed = System.currentTimeMillis();
			this.wasPressed = pressed;
			
			if (pressed) {
				this.clicks.add(this.lastPressed);
			}
		}
	}
	
	private int getCps() {		
		final long time = System.currentTimeMillis();
		
		for (int i = 0; i < this.clicks.size(); i++) {
			Long aLong = this.clicks.get(i);
			if (aLong + 1000 < time) {
				this.clicks.remove(aLong);
			}
		}
		
		return this.clicks.size();
	}

}

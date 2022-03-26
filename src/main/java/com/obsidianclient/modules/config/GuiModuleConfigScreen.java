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

package com.obsidianclient.modules.config;

import java.awt.Color;
import java.io.IOException;

import com.obsidianclient.modules.Module;
import org.lwjgl.input.Keyboard;

import com.obsidianclient.ObsidianClient;
import com.obsidianclient.gui.elements.GuiButtonObsidianClient;
import com.obsidianclient.utils.GraphicUtil;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;

public abstract class GuiModuleConfigScreen extends GuiScreen {

	//The screen that opened this one:
	protected GuiScreen parentScreen = null;

	//Text for the headline:
	protected String displayModuleName;
	
	//Boundaries:
	protected int cardWidth;
	protected int cardHeight;
	
	//The Module of the config screen:
	private final Module module;
	
	//Standards: Width = 220, Height = 220
	public GuiModuleConfigScreen(String displayModuleName, int cardWidth, int cardHeight, Module module) {
		this.displayModuleName = displayModuleName;
		this.cardWidth = cardWidth;
		this.cardHeight = cardHeight;
		this.module = module;
	}
	
	@Override
	public void initGui() {
		//Adding the default "Done" button:
		this.buttonList.add(new GuiButtonObsidianClient(0, this.width / 2 - 200 / 2, this.height / 2 + this.cardHeight / 2 - 33, 196, 20, "Done"));
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		//Functionality for the "Done" button:
		if (button.id == 0) {
			//Saving the settings:
			ObsidianClient.getClient().getModuleManager().saveConfig(this.module);
			//Going back to the previous screen:
			this.mc.displayGuiScreen(this.parentScreen);
		}
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if (keyCode == Keyboard.KEY_ESCAPE) {
			//Saving the settings:
			ObsidianClient.getClient().getModuleManager().saveConfig(this.module);
			//Going back to the previous screen:
			this.mc.displayGuiScreen(this.parentScreen);
		}
	}
	
	//Can be overridden to draw on elements:
	public void drawConfigScreen(int mouseX, int mouseY, float partialTicks) {}
	
	@Override
	public final void drawScreen(int mouseX, int mouseY, float partialTicks) {
		//Draws the background:
		GraphicUtil.drawRect(0, 0, this.width, this.height, Integer.MIN_VALUE);
		
		//Enabling alpha:
		GlStateManager.enableAlpha();
		
		//Draws the background container:
		GraphicUtil.drawHollowContainerBackground(this.width / 2.0D - this.cardWidth / 2.0D, this.height / 2.0D - this.cardHeight / 2.0D, this.cardWidth, this.cardHeight, 13, 0, 0, 0);
		
		//Disabling alpha:
		GlStateManager.disableAlpha();
		
		//Draws the headline:
		mc.fontRendererObj.drawString(this.displayModuleName + " Settings", this.width / 2.0F - mc.fontRendererObj.getStringWidth(this.displayModuleName + " Settings") / 2.0F, this.height / 2.0F - this.cardHeight / 2.0F + 8, Color.DARK_GRAY.getRGB(), false);
		
		//Let the upper class draw things:
		this.drawConfigScreen(mouseX, mouseY, partialTicks);
		
		//Draws the buttons and labels:
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	public final GuiScreen getParentScreen() {
		return parentScreen;
	}

	public final void setParentScreen(GuiScreen parentScreen) {
		this.parentScreen = parentScreen;
	}

}

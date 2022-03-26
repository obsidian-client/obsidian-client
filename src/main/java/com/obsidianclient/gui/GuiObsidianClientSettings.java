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

package com.obsidianclient.gui;

import java.awt.Color;
import java.io.IOException;

import com.obsidianclient.ObsidianClient;
import com.obsidianclient.gui.elements.GuiButtonChat;
import com.obsidianclient.gui.elements.GuiButtonImage;
import com.obsidianclient.gui.elements.GuiButtonObsidianClient;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

import com.obsidianclient.gui.elements.GuiSettingsList;
import com.obsidianclient.utils.GraphicUtil;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;

public class GuiObsidianClientSettings extends GuiScreen {

	private GuiSettingsList settingsList;
	
	public GuiButtonObsidianClient btnToggle;
	public GuiButtonObsidianClient btnOptions;

	private GuiButtonImage btnInfo;

	//The GuiScreen that opened up this one:
	private GuiScreen parentScreen = null;
	
	//Initializing the gui:
	@Override
	public void initGui() {

		this.settingsList = new GuiSettingsList(this.mc, this, this.width, this.height, 32, this.height - 32, 36);
		this.settingsList.registerScrollButtons(4, 5);

		this.buttonList.add(btnToggle = new GuiButtonObsidianClient(1, this.width / 2 - 155, this.height - 26, 100, 20, "Toggle"));
		this.btnToggle.enabled = false;
		this.buttonList.add(btnOptions = new GuiButtonObsidianClient(2, this.width / 2 - 50, this.height - 26, 100, 20, "Options"));
		this.btnOptions.enabled = false;

		this.buttonList.add(new GuiButtonObsidianClient(0, this.width / 2 + 55, this.height - 26, 100, 20, "Back"));
		this.buttonList.add(new GuiButtonObsidianClient(10, this.width / 2 - 100 - 55, 6, 100, 20, "Login"));

		this.buttonList.add(btnInfo = new GuiButtonImage(30, 6, 6, new ResourceLocation("obsidianclient/info.png")));

		GuiButtonObsidianClient btnHudSettings;
		this.buttonList.add(btnHudSettings = new GuiButtonObsidianClient(20, this.width / 2 + 55, 6, 100, 20, "Edit HUD"));
		if (this.parentScreen instanceof GuiHudSettings) {
			btnHudSettings.enabled = false;
		}

	}
	
	//Controls the users actions:
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.id == 0) {
			this.mc.displayGuiScreen(this.parentScreen);
			//Saving the changes:
			ObsidianClient.getClient().getModuleManager().saveWholeConfig();

		} else if (button.id == 1) {
			this.settingsList.getSelectedModule().toggle();

		} else if (button.id == 2) {
			this.settingsList.getSelectedModule().getOptionsScreen().setParentScreen(this);
			this.mc.displayGuiScreen(this.settingsList.getSelectedModule().getOptionsScreen());

		} else if (button.id == 10) {
			ObsidianClient.getClient().getAccountLoginScreen().setParentScreen(this);
			this.mc.displayGuiScreen(ObsidianClient.getClient().getAccountLoginScreen());

		} else if (button.id == 20) {
			ObsidianClient.getClient().getHudSettingsScreen().setParentScreen(this);
			this.mc.displayGuiScreen(ObsidianClient.getClient().getHudSettingsScreen());

		} else if (button.id == 30) {
			ObsidianClient.getClient().getObsidianClientInfoScreen().setParentScreen(this);
			this.mc.displayGuiScreen(ObsidianClient.getClient().getObsidianClientInfoScreen());

		} else {
			this.settingsList.actionPerformed(button);
		}
	}
	
	//Closes the gui-screen, when the player press escape:
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if (keyCode == Keyboard.KEY_ESCAPE) {
			this.mc.displayGuiScreen(this.parentScreen);
			//Saving the changes:
			ObsidianClient.getClient().getModuleManager().saveWholeConfig();
		}
	}
	
	//Gives the mouse input to the settings list:
	@Override
	public void handleMouseInput() throws IOException {
		this.settingsList.handleMouseInput();
		super.handleMouseInput();
	}
	
	//Must be active for the gui to work right:
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	//Draws the screen:
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {		
		
		//Draws the settings-list:
		this.settingsList.drawScreen(mouseX, mouseY, partialTicks);
		
		//Draws the name of the gui:
		GraphicUtil.drawCenteredStringWithShadow(mc.fontRendererObj, "Obsidian Client", this.width / 2.0F, 12, Color.WHITE.getRGB());
		
		//Draws the mini player, when the player is in a world:
		if (mc.theWorld != null) {
			
			//The Background for it:
			GraphicUtil.drawContainerBackground(this.width - 55, this.height / 2.0D - 40, 50, 80);
			
			//The mini player:
			GlStateManager.enableDepth();
			GuiInventory.drawEntityOnScreen(this.width - 32, this.height / 2 + 26, 30, (float) this.width - 30 - mouseX, (float) this.height / 2 - 20 - mouseY, this.mc.thePlayer);
			GlStateManager.disableDepth();
		}		
		
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	public GuiScreen getParentScreen() {
		return parentScreen;
	}

	public void setParentScreen(GuiScreen parentScreen) {
		this.parentScreen = parentScreen;
	}
}

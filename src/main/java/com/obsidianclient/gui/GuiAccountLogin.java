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

import com.mojang.authlib.exceptions.AuthenticationException;
import com.obsidianclient.gui.elements.GuiTextFieldHint;
import com.obsidianclient.utils.GraphicUtil;
import com.obsidianclient.utils.LoginUtil;
import com.obsidianclient.gui.elements.GuiButtonObsidianClient;
import org.apache.logging.log4j.LogManager;
import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiAccountLogin extends GuiScreen {

	private GuiTextFieldHint txtUsername;
	private GuiTextFieldHint txtPassword;

	//The GuiScreen that opened up this one:
	private GuiScreen parentScreen = null;
	
	//Initializing the Screen:
	@Override
	public void initGui() {
		//Buttons:
		this.buttonList.add(new GuiButtonObsidianClient(1, this.width / 2 - 100 - 2 - 3, this.height / 2 + 40, 100, 20, "Login"));
		this.buttonList.add(new GuiButtonObsidianClient(0, this.width / 2 + 2 - 3, this.height / 2 + 40, 100, 20, "Cancel"));
		
		//Text fields:
		Keyboard.enableRepeatEvents(true);
		
		this.txtUsername = new GuiTextFieldHint(0, this.mc.fontRendererObj, this.width / 2 - 100 - 3, this.height / 2 - 5 - 20 - 2, 200, 20, "Username");
		this.txtUsername.setFocused(true);
		this.txtUsername.setCanLoseFocus(true);
		
		this.txtPassword = new GuiTextFieldHint(1, this.mc.fontRendererObj, this.width / 2 - 100 - 3, this.height / 2 + 3, 200, 20, "Password");
		this.txtPassword.setFocused(false);
		this.txtPassword.setCanLoseFocus(true);
	}	
	
	//Updates the Screen:
	@Override
	public void updateScreen() {
		this.txtUsername.updateCursorCounter();
		this.txtPassword.updateCursorCounter();
	}
	
	//Called when a button was clicked:
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.id == 0) {
			mc.displayGuiScreen(parentScreen);

			//If the "Login" button was pressed:
		} else if (button.id == 1) {

			String username = txtUsername.getText();
			String password = txtPassword.getText();

			if (username != null) {

				if (!username.equals("")) {

					if (username.contains("@")) {

						//For Mojang Accounts:
						try {

							LoginUtil.loginMojang(username, password);

						} catch (AuthenticationException e) {
							LogManager.getLogger().warn("[Obsidian Client - Account Manager] Login into premium account failed!");
							e.printStackTrace();
						}

					} else {

						//For Legacy Accounts:
						LoginUtil.loginLegacy(username);

					}

				}

			}

		}

	}
	
	//Called when a key was typed:
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if (keyCode == Keyboard.KEY_ESCAPE) {
			mc.displayGuiScreen(parentScreen);

			//Allowing the user to switch between the TextFields using the TAB Key:
		} else if (keyCode == Keyboard.KEY_TAB) {
			this.txtUsername.setFocused(!this.txtUsername.isFocused());
			this.txtPassword.setFocused(!this.txtPassword.isFocused());
		}
		
		this.txtUsername.textboxKeyTyped(typedChar, keyCode);
		this.txtPassword.textboxKeyTyped(typedChar, keyCode);
	}
	
	//Called if a mouse button was clicked:
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		this.txtUsername.mouseClicked(mouseX, mouseY, mouseButton);
		this.txtPassword.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	//Called when the screen is closing:
	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}
	
	//Draws the screen:
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {

		int cardWidth = 240;
		int cardHeight = 160;

		//Drawing the background:
		GraphicUtil.drawRect(0, 0, this.width, this.height, Integer.MIN_VALUE);
		GraphicUtil.drawHollowContainerBackground(this.width / 2.0D - cardWidth / 2.0D, this.height / 2.0D - cardHeight / 2.0D, cardWidth, cardHeight, 13, 0, 0, 0);

		//Draws the headline:
		mc.fontRendererObj.drawString("Account Login", this.width / 2.0F - mc.fontRendererObj.getStringWidth("Account Login") / 2.0F, this.height / 2.0F - cardHeight / 2.0F + 8, Color.DARK_GRAY.getRGB(), false);

		GraphicUtil.drawCenteredStringWithShadow(mc.fontRendererObj, "Please enter account information:", this.width / 2.0F, this.height / 2.0F - 50, Color.WHITE.getRGB());
		
		this.txtUsername.drawTextBox();
		this.txtPassword.drawTextBox();
		
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	public GuiScreen getParentScreen() {
		return parentScreen;
	}

	public void setParentScreen(GuiScreen parentScreen) {
		this.parentScreen = parentScreen;
	}
}

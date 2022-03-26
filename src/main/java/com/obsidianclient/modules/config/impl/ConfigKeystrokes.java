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

package com.obsidianclient.modules.config.impl;

import java.awt.Color;
import java.io.IOException;

import com.obsidianclient.gui.elements.GuiButtonToggle;
import com.obsidianclient.modules.config.GuiModuleConfigScreen;
import com.obsidianclient.modules.impl.ModuleKeystrokes;

import net.minecraft.client.gui.GuiButton;

//The config screen for the Keystrokes Module:
public class ConfigKeystrokes extends GuiModuleConfigScreen {
	
	private GuiButtonToggle btnToggleMouseButtons;
	private GuiButtonToggle btnToggleSpaceBar;
	
	public ConfigKeystrokes(ModuleKeystrokes module) {
		super("Keystrokes", 220, 120, module);
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		//A button for toggling the mouse buttons:
		this.buttonList.add(btnToggleMouseButtons = new GuiButtonToggle(1, this.width / 2 + this.cardWidth / 2 - 34, this.height / 2 - 28, 20, 20, "", false));
		this.btnToggleMouseButtons.setActive(ModuleKeystrokes.areMouseButtonsEnabled);
		
		//A button for toggling the space bar:
		this.buttonList.add(btnToggleSpaceBar = new GuiButtonToggle(2, this.width / 2 + this.cardWidth / 2 - 34, this.height / 2 - 3, 20, 20, "", false));
		this.btnToggleSpaceBar.setActive(ModuleKeystrokes.isSpaceBarEnabled);
	}
	
	//On-Click listeners for all the buttons:
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		
		if (button.id == 1) {
			this.btnToggleMouseButtons.setActive(!this.btnToggleMouseButtons.getActiveState());
			ModuleKeystrokes.areMouseButtonsEnabled = this.btnToggleMouseButtons.getActiveState();
			
		} else if (button.id == 2) {
			this.btnToggleSpaceBar.setActive(!this.btnToggleSpaceBar.getActiveState());
			ModuleKeystrokes.isSpaceBarEnabled = this.btnToggleSpaceBar.getActiveState();
		}
		
	}
	
	@Override
	public void drawConfigScreen(int mouseX, int mouseY, float partialTicks) {
		//Drawing the names for the different settings:
		this.mc.fontRendererObj.drawString("Show Mouse Buttons", this.width / 2.0F - this.cardWidth / 2.0F + 12, this.height / 2.0F - 28 + 5, Color.WHITE.getRGB(), false);
		this.mc.fontRendererObj.drawString("Show Space Bar", this.width / 2.0F - this.cardWidth / 2.0F + 12, this.height / 2.0F - 3 + 5, Color.WHITE.getRGB(), false);
	}

}

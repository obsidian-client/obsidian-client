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
import com.obsidianclient.modules.impl.ModuleFPS;

import net.minecraft.client.gui.GuiButton;

//The config screen for the FPS module:
public class ConfigFPS extends GuiModuleConfigScreen {

	private GuiButtonToggle btnToggleModuleName;
	private GuiButtonToggle btnToggleClassicMode;
	
	public ConfigFPS(ModuleFPS module) {
		super("FPS Display", 220, 120, module);
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		//A button for toggling the visibility of the module name:
		this.buttonList.add(btnToggleModuleName = new GuiButtonToggle(1, this.width / 2 + this.cardWidth / 2 - 34, this.height / 2 - 28, 20, 20, "", false));
		this.btnToggleModuleName.setActive(ModuleFPS.showModuleName);
				
		//A button for toggling the classic design:
		this.buttonList.add(btnToggleClassicMode = new GuiButtonToggle(2, this.width / 2 + this.cardWidth / 2 - 34, this.height / 2 - 3, 20, 20, "", false));
		this.btnToggleClassicMode.setActive(ModuleFPS.useClassicDesign);
		
	}
	
	//On-Click listeners for all the buttons:
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		
		if (button.id == 1) {
			this.btnToggleModuleName.setActive(!this.btnToggleModuleName.getActiveState());
			ModuleFPS.showModuleName = this.btnToggleModuleName.getActiveState();
			
		} else if (button.id == 2) {
			this.btnToggleClassicMode.setActive(!this.btnToggleClassicMode.getActiveState());
			ModuleFPS.useClassicDesign = this.btnToggleClassicMode.getActiveState();
		}
		
	}
	
	@Override
	public void drawConfigScreen(int mouseX, int mouseY, float partialTicks) {
		//Drawing the names for the different settings:
		this.mc.fontRendererObj.drawString("Show Module Name", this.width / 2.0F - this.cardWidth / 2.0F + 12, this.height / 2.0F - 28 + 5, Color.WHITE.getRGB(), false);
		this.mc.fontRendererObj.drawString("Classic Design", this.width / 2.0F - this.cardWidth / 2.0F + 12, this.height / 2.0F - 3 + 5, Color.WHITE.getRGB(), false);
	}

}

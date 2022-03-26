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
import java.util.ArrayList;
import java.util.List;

import com.obsidianclient.gui.elements.GuiButtonChangeInteger;
import com.obsidianclient.gui.elements.GuiButtonToggle;
import com.obsidianclient.modules.config.GuiModuleConfigScreen;
import com.obsidianclient.modules.impl.ModuleArmorStatus;

import net.minecraft.client.gui.GuiButton;

//A simple config screen for the armor status module:
public class ConfigArmorStatus extends GuiModuleConfigScreen {

	private GuiButtonToggle btnTogglePercent;
	private GuiButtonChangeInteger btnTogglePercentAccuracy;
	
	public ConfigArmorStatus(ModuleArmorStatus module) {
		super("Armor Status", 220, 120, module);
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		//A button to toggle on/off the percent numbers:
		this.buttonList.add(btnTogglePercent = new GuiButtonToggle(1, this.width / 2 + this.cardWidth / 2 - 34, this.height / 2 - 28, 20, 20, "", false));
		this.btnTogglePercent.setActive(ModuleArmorStatus.withDamageString);
		
		//A button to toggle the percent accuracy:
		List<Integer> list = new ArrayList<Integer>();
		list.add(0, 0);
		list.add(1, 1);
		list.add(2, 2);
		this.buttonList.add(btnTogglePercentAccuracy = new GuiButtonChangeInteger(2, this.width / 2 + this.cardWidth / 2 - 34, this.height / 2 - 3, list));
		this.btnTogglePercentAccuracy.setCurrentIndex(ModuleArmorStatus.percentAccuracy);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		
		//On-Click listeners for all the buttons:
		if (button.id == 1) {
			this.btnTogglePercent.setActive(!btnTogglePercent.getActiveState());
			ModuleArmorStatus.withDamageString = btnTogglePercent.getActiveState();

		} else if (button.id == 2) {
			this.btnTogglePercentAccuracy.onClick();
			ModuleArmorStatus.percentAccuracy = this.btnTogglePercentAccuracy.getSelectedItem();

		}
	}
	
	@Override
	public void drawConfigScreen(int mouseX, int mouseY, float partialTicks) {
		//Draws some Strings:
		this.mc.fontRendererObj.drawString("Show % Numbers", this.width / 2.0F - this.cardWidth / 2.0F + 12, this.height / 2.0F - 28 + 5, Color.WHITE.getRGB(), false);
		this.mc.fontRendererObj.drawString("Percent Accuracy", this.width / 2.0F - this.cardWidth / 2.0F + 12, this.height / 2.0F - 3 + 5, Color.WHITE.getRGB(), false);
	}

}

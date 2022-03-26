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

package com.obsidianclient.gui.override;

import java.io.IOException;

import com.obsidianclient.ObsidianClient;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;

public class GuiObsidianClientMainMenu extends GuiMainMenu {

	@Override
	public void initGui() {
		super.initGui();
		
		//Disabling the realms button:		
		for (int i = 0; i < this.buttonList.size(); i++) {
			GuiButton btn = this.buttonList.get(i);
			if (btn.id == 14) {
				btn.enabled = false;
				btn.visible = false;
			}
		}		
		
		//Adding the Obsidian Client button:
		this.buttonList.add(new GuiButton(50, this.width / 2 + 2, this.height / 4 + 48 + 24 * 2, 98, 20, "Obsidian Client"));
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		
		if (button.id == 50) {
			//Opening the Obsidian Client Settings:
			ObsidianClient.getClient().getObsidianClientSettings().setParentScreen(this);
			this.mc.displayGuiScreen(ObsidianClient.getClient().getObsidianClientSettings());
		}
	}
	
}

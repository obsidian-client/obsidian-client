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

import com.obsidianclient.launch.Environment;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiIngameMenu;

public class GuiObsidianClientIngameMenu extends GuiIngameMenu {

	@Override
	public void initGui() {
		super.initGui();

		if (Environment.isRunningForge) {

			//Changing the width of the "Open to LAN" button to 98:
			for (GuiButton btn : this.buttonList) {
				if (btn.id == 7) {
					btn.setWidth(98);
				}
			}

			//Adding a new button for the Obsidian Client Settings:
			this.buttonList.add(new GuiButton(20, this.width / 2 + 2, this.height / 4 + 56, 98, 20, "Obsidian Client"));

		} else {

			//Adding a new button for the Obsidian Client Settings:
			this.buttonList.add(new GuiButton(20, this.width / 2 - 100, this.height / 4 + 56, 200, 20, "Obsidian Client"));

		}

	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);

		if (button.id == 20) {
			//Opening the Obsidian Client Settings:
			ObsidianClient.getClient().getObsidianClientSettings().setParentScreen(this);
			this.mc.displayGuiScreen((ObsidianClient.getClient().getObsidianClientSettings()));
		}

	}
	
}

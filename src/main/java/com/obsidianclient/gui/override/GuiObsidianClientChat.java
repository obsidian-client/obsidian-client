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
import org.lwjgl.input.Mouse;

import com.obsidianclient.gui.elements.GuiButtonChat;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;

public class GuiObsidianClientChat extends GuiChat {
	
	@Override
	public void initGui() {
		super.initGui();
		
		//Adding the Obsidian Client Settings button:
		this.buttonList.add(new GuiButtonChat(0, this.width - 14, this.height - 14, 12, 12, new ResourceLocation("obsidianclient/optionsWhite.png"), ""));
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		
		//Checking if the Obsidian Client Settings button was pressed:
		if (button.id == 0) {
			//Opening the Obsidian Client Settings:
			ObsidianClient.getClient().getObsidianClientSettings().setParentScreen(this);
			this.mc.displayGuiScreen(ObsidianClient.getClient().getObsidianClientSettings());
		}
	}
	
	//Changing (Overriding) the length of the text box:
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawRect(2, this.height - 14, this.width - 16, this.height - 2, Integer.MIN_VALUE);
        this.inputField.drawTextBox();
        IChatComponent ichatcomponent = this.mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY());

        if (ichatcomponent != null && ichatcomponent.getChatStyle().getChatHoverEvent() != null)
        {
            this.handleComponentHover(ichatcomponent, mouseX, mouseY);
        }

        for (int i = 0; i < this.buttonList.size(); i++)
        {
            this.buttonList.get(i).drawButton(this.mc, mouseX, mouseY);
        }

        for (int i = 0; i < this.labelList.size(); i++)
        {
            this.labelList.get(i).drawLabel(this.mc, mouseX, mouseY);
        }
	}

}

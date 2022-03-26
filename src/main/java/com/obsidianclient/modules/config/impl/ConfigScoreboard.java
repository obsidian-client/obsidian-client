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

import com.obsidianclient.gui.elements.GuiButtonToggle;
import com.obsidianclient.modules.config.GuiModuleConfigScreen;
import com.obsidianclient.modules.impl.ModuleScoreboard;
import net.minecraft.client.gui.GuiButton;

import java.awt.*;
import java.io.IOException;

public class ConfigScoreboard extends GuiModuleConfigScreen {

    private GuiButtonToggle btnToggleRedNumbers;

    public ConfigScoreboard(ModuleScoreboard module) {
        super("Scoreboard", 220, 90, module);
    }

    @Override
    public void initGui() {
        super.initGui();

        //A button to turn off the red numbers on the Scoreboard:
        this.buttonList.add(btnToggleRedNumbers = new GuiButtonToggle(1, this.width / 2 + this.cardWidth / 2 - 34, this.height / 2 - 15, 20, 20, "", false));
        this.btnToggleRedNumbers.setActive(ModuleScoreboard.hideRedNumbers);
    }

    //On-click listeners for the buttons:
    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);

        if (button.id == 1) {
            this.btnToggleRedNumbers.setActive(!btnToggleRedNumbers.getActiveState());
            ModuleScoreboard.hideRedNumbers = this.btnToggleRedNumbers.getActiveState();
        }
    }

    @Override
    public void drawConfigScreen(int mouseX, int mouseY, float partialTicks) {
        //Draws the names of the buttons:
        this.mc.fontRendererObj.drawString("Hide Red Numbers", this.width / 2.0F - this.cardWidth / 2.0F + 12, this.height / 2.0F - 15 + 5, Color.WHITE.getRGB(), false);
    }
}

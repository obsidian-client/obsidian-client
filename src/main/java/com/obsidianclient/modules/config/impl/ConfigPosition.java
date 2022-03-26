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

import com.obsidianclient.gui.elements.GuiButtonChangeInteger;
import com.obsidianclient.gui.elements.GuiButtonToggle;
import com.obsidianclient.modules.config.GuiModuleConfigScreen;
import com.obsidianclient.modules.impl.ModulePosition;
import net.minecraft.client.gui.GuiButton;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//The Config Screen for the Position Module:
public class ConfigPosition extends GuiModuleConfigScreen {

    private GuiButtonToggle btnToggleClassicMode;
    private GuiButtonChangeInteger btnChangeAccuracy;
    private GuiButtonToggle btnToggleHorizontalMode;
    private GuiButtonToggle btnToggleSeparators;

    public ConfigPosition(ModulePosition module) {
        super("Position Display", 220, 180, module);
    }

    @Override
    public void initGui() {
        super.initGui();

        //A button to toggle the classic design:
        this.buttonList.add(btnToggleClassicMode = new GuiButtonToggle(1, this.width / 2 + this.cardWidth / 2 - 34, this.height / 2 - 53, 20, 20, "", false));
        this.btnToggleClassicMode.setActive(ModulePosition.useClassicDesign);

        //A button to set the accuracy:
        List<Integer> list = new ArrayList<Integer>();
        list.add(0, 0);
        list.add(1, 1);
        list.add(2, 2);
        list.add(3, 3);
        this.buttonList.add(btnChangeAccuracy = new GuiButtonChangeInteger(2, this.width / 2 + this.cardWidth / 2 - 34, this.height / 2 - 28, list));
        this.btnChangeAccuracy.setCurrentIndex(ModulePosition.accuracy);

        //A button to toggle the horizontal mode:
        this.buttonList.add(btnToggleHorizontalMode = new GuiButtonToggle(3, this.width / 2 + this.cardWidth / 2 - 34, this.height / 2 - 3, 20, 20, "", false));
        this.btnToggleHorizontalMode.setActive(ModulePosition.isHorizontalMode);

        //A button to toggle the separators in the horizontal mode:
        this.buttonList.add(btnToggleSeparators = new GuiButtonToggle(4, this.width / 2 + this.cardWidth / 2 - 34, this.height / 2 + 22, 20, 20, "", false));
        this.btnToggleSeparators.setActive(ModulePosition.useSeparatorsInHorizontalMode);

    }

    //On-click listener for all the buttons:
    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);

        if (button.id == 1) {
            this.btnToggleClassicMode.setActive(!this.btnToggleClassicMode.getActiveState());
            ModulePosition.useClassicDesign = this.btnToggleClassicMode.getActiveState();

        } else if (button.id == 2) {
            this.btnChangeAccuracy.onClick();
            ModulePosition.accuracy = this.btnChangeAccuracy.getSelectedItem();

        } else if (button.id == 3) {
            this.btnToggleHorizontalMode.setActive(!this.btnToggleHorizontalMode.getActiveState());
            ModulePosition.isHorizontalMode = this.btnToggleHorizontalMode.getActiveState();

        } else if (button.id == 4) {
            this.btnToggleSeparators.setActive(!this.btnToggleSeparators.getActiveState());
            ModulePosition.useSeparatorsInHorizontalMode = this.btnToggleSeparators.getActiveState();

        }

    }

    //Drawing the names of the different settings / buttons:
    @Override
    public void drawConfigScreen(int mouseX, int mouseY, float partialTicks) {
        this.mc.fontRendererObj.drawString("Classic Design", this.width / 2.0F - this.cardWidth / 2.0F + 12, this.height / 2.0F - 53 + 5, Color.WHITE.getRGB(), false);
        this.mc.fontRendererObj.drawString("Accuracy", this.width / 2.0F - this.cardWidth / 2.0F + 12, this.height / 2.0F - 28 + 5, Color.WHITE.getRGB(), false);
        this.mc.fontRendererObj.drawString("Horizontal Mode", this.width / 2.0F - this.cardWidth / 2.0F + 12, this.height / 2.0F - 3 + 5, Color.WHITE.getRGB(), false);
        this.mc.fontRendererObj.drawString("Separators In Horizontal Mode", this.width / 2.0F - this.cardWidth / 2.0F + 12, this.height / 2.0F + 22 + 5, Color.WHITE.getRGB(), false);
    }
}

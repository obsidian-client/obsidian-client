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

import com.obsidianclient.gui.elements.GuiButtonChangeEnum;
import com.obsidianclient.gui.elements.GuiButtonToggle;
import com.obsidianclient.modules.config.GuiModuleConfigScreen;
import com.obsidianclient.modules.impl.ModuleBow;
import net.minecraft.client.gui.GuiButton;

import java.awt.*;
import java.io.IOException;

public class ConfigBow extends GuiModuleConfigScreen {

    private GuiButtonToggle btnToggleParticles;
    private GuiButtonChangeEnum btnToggleParticleType;

    public ConfigBow(ModuleBow module) {
        super("Bow", 220, 120, module);
    }

    @Override
    public void initGui() {
        super.initGui();

        //A button to toggle ModuleBow.alwaysShowParticles:
        this.buttonList.add(btnToggleParticles = new GuiButtonToggle(1, this.width / 2 + this.cardWidth / 2 - 34, this.height / 2 - 28, 20, 20, "", false));
        this.btnToggleParticles.setActive(ModuleBow.alwaysShowParticles);

        //A button to toggle ModuleBow.particleType:
        this.buttonList.add(btnToggleParticleType = new GuiButtonChangeEnum(2, this.width / 2 + this.cardWidth / 2 - 34 - 90, this.height / 2 - 3, 110, 20, ModuleBow.particleList));
        this.btnToggleParticleType.setCurrentIndex(ModuleBow.particleListIndex);

    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);

        //On-Click listeners for all the buttons:
        if (button.id == 1) {
            this.btnToggleParticles.setActive(!btnToggleParticles.getActiveState());
            ModuleBow.alwaysShowParticles = btnToggleParticles.getActiveState();

        } else if (button.id == 2) {
            this.btnToggleParticleType.onClick();
            ModuleBow.particleListIndex = btnToggleParticleType.getList().indexOf(btnToggleParticleType.getSelectedItem());

        }

    }

    @Override
    public void drawConfigScreen(int mouseX, int mouseY, float partialTicks) {
        //Draws the names of the buttons:
        this.mc.fontRendererObj.drawString("Always Show Particles", this.width / 2.0F - this.cardWidth / 2.0F + 12, this.height / 2.0F - 28 + 5, Color.WHITE.getRGB(), false);
        this.mc.fontRendererObj.drawString("Particle Type", this.width / 2.0F - this.cardWidth / 2.0F + 12, this.height / 2.0F - 3 + 5, Color.WHITE.getRGB(), false);
    }

}

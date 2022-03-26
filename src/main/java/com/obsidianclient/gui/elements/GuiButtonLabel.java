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

package com.obsidianclient.gui.elements;

import com.obsidianclient.utils.GraphicUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class GuiButtonLabel extends GuiButton {

    public int labelColor;
    public boolean withShadow;

    public GuiButtonLabel(int buttonId, int x, int y, String buttonText, int color, boolean withShadow) {
        super(buttonId, x, y, buttonText);
        this.width = Minecraft.getMinecraft().fontRendererObj.getStringWidth(buttonText);
        this.height = Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT;
        this.labelColor = color;
        this.withShadow = withShadow;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {

            mc.fontRendererObj.drawString(this.displayString, this.xPosition, this.yPosition, this.labelColor, this.withShadow);

            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            if (this.hovered) {
                GraphicUtil.drawHorizontalLine(this.xPosition, this.xPosition + this.width - 1, this.yPosition + this.height - 1, this.labelColor);
            }

        }
    }
}

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
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiButtonImage extends GuiButton
{
	private ResourceLocation pictureLocation;

    public int imageWidth = 14;
    public int imageHeight = 14;
	
    public GuiButtonImage(int buttonId, int x, int y, ResourceLocation pictureLocation)
    {
        super(buttonId, x, y, 20, 20, "");
        this.pictureLocation = pictureLocation;
    }

    public void drawButton(Minecraft mc, int mouseX, int mouseY)
    {
        if (this.visible)
        {
            mc.getTextureManager().bindTexture(new ResourceLocation("obsidianclient/buttons.png"));
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            boolean hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            int i = 106;

            if (hovered)
            {
                i += this.height;
            }

            GraphicUtil.drawTexturedModalRect(this.xPosition, this.yPosition, 0, i, this.width, this.height);
            
            mc.getTextureManager().bindTexture(pictureLocation);
            GraphicUtil.drawModalRectWithCustomSizedTexture(this.xPosition + (this.width - imageWidth) / 2.0D, this.yPosition + (this.width - imageWidth) / 2.0D, 0, 0, imageWidth, imageHeight, imageWidth, imageHeight);
        }
    }
}

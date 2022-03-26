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

import java.awt.Color;

import com.obsidianclient.utils.GraphicUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

//The default button used in every GuiScreen that belongs to ObsidianClient.
public class GuiButtonObsidianClient extends GuiButton {

	public GuiButtonObsidianClient(int buttonId, int x, int y, String buttonText) {
		super(buttonId, x, y, 200, 20, buttonText);
	}
	
	public GuiButtonObsidianClient(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
		super(buttonId, x, y, widthIn, heightIn, buttonText);
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		if (this.visible) {
			
			this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
			
            mc.getTextureManager().bindTexture(new ResourceLocation("obsidianclient/buttons.png"));
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            int i = this.getHoverState(this.hovered);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.blendFunc(770, 771);
            GraphicUtil.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + i * 20, this.width / 2.0D, this.height);
            GraphicUtil.drawTexturedModalRect(this.xPosition + this.width / 2.0D, this.yPosition, 200 - this.width / 2.0D, 46 + i * 20, this.width / 2.0D, this.height);
            this.mouseDragged(mc, mouseX, mouseY);
            GraphicUtil.drawCenteredStringWithShadow(mc.fontRendererObj, this.displayString, this.xPosition + this.width / 2.0F, this.yPosition + (this.height - 8) / 2.0F, Color.WHITE.getRGB());
			
		}
	}

}

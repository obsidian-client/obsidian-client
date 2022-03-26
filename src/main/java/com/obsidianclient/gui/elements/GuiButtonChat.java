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

import com.obsidianclient.ObsidianClient;
import com.obsidianclient.utils.GraphicUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

//A simple button that looks like the text box of the GuiChat Screen.
//It can contain an image or text.
//It's used in GuiObsidianClientChat.
public class GuiButtonChat extends GuiButton
{
	private ResourceLocation pictureLocation = null;

	private boolean isLightMode = false;

	public boolean drawBackgroundWhenPictureShown = true;
	public int pictureWidth = 16;
	public int pictureHeight = 16;

    public GuiButtonChat(int buttonId, int x, int y, String buttonText)
    {
        this(buttonId, x, y, 200, 20, buttonText);
    }

    public GuiButtonChat(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText)
    {
    	super(buttonId, x, y, widthIn, heightIn, buttonText);
    }

	public GuiButtonChat(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, boolean isLightMode)
	{
		super(buttonId, x, y, widthIn, heightIn, buttonText);
		this.isLightMode = isLightMode;
	}
    
    public GuiButtonChat(int buttonId, int x, int y, int widthIn, int heightIn, ResourceLocation pictureLocation, String buttonText)
    {
    	super(buttonId, x, y, widthIn, heightIn, buttonText);
    	this.pictureLocation = pictureLocation;
    	
    }

	public GuiButtonChat(int buttonId, int x, int y, int widthIn, int heightIn, ResourceLocation pictureLocation, String buttonText, boolean isLightMode)
	{
		super(buttonId, x, y, widthIn, heightIn, buttonText);
		this.pictureLocation = pictureLocation;
		this.isLightMode = isLightMode;

	}
   
    public void drawButton(Minecraft mc, int mouseX, int mouseY)
    {
        if (this.visible)
        {            
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;

            //The two primary colors used below:
            int darkColor = Integer.MIN_VALUE;
            if (this.isLightMode) {
            	darkColor = ObsidianClient.GRAY_OVERLAY_COLOR;
			}
            int lightColor = Integer.MAX_VALUE;

            if (pictureLocation != null) {
            	
            	mc.getTextureManager().bindTexture(pictureLocation);
            	
            	GlStateManager.color(1.0f, 1.0f, 1.0f);
            	if (this.hovered) {

					if (this.drawBackgroundWhenPictureShown) {
						GraphicUtil.drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, darkColor);
					}
            		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            		GraphicUtil.drawModalRectWithCustomSizedTexture(this.xPosition, this.yPosition, 0, 0, this.pictureWidth, this.pictureHeight, this.pictureWidth, this.pictureHeight);
            		
            	} else {

					if (this.drawBackgroundWhenPictureShown) {
						GraphicUtil.drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, darkColor);
					}
            		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            		GraphicUtil.drawModalRectWithCustomSizedTexture(this.xPosition + 1, this.yPosition + 1, 0, 0, this.pictureWidth - 2, this.pictureHeight - 2, this.pictureWidth - 2, this.pictureHeight - 2);
            	}
        		
        	} else {
        		
        		if (this.hovered) {
        			
        			GraphicUtil.drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, lightColor);
        			
        		} else {
        			
        			GraphicUtil.drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, darkColor);
        			
        		}
        		
        	}
            
            if (!this.displayString.equals("")) {
            	
            	GraphicUtil.drawCenteredStringWithShadow(mc.fontRendererObj, this.displayString, this.xPosition + this.width / 2.0F, this.yPosition + (this.height - 8) / 2.0F, Color.WHITE.getRGB());
            }          
        }
    }

}

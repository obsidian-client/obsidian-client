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
import com.obsidianclient.gui.GuiObsidianClientSettings;
import com.obsidianclient.modules.Module;

import com.obsidianclient.utils.GraphicUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

//The list seen in the Obsidian Client Settings:
public class GuiSettingsList extends GuiSlot {	
	
	private final GuiObsidianClientSettings obsidianClientSettings;
	
	//The current selected slot:
	private int currentIndex = -1;
	
	//The current module index (for drawing):
	private int currentModuleIndex;
	
	public GuiSettingsList(Minecraft mc, GuiObsidianClientSettings obsidianClientSettings, int width, int height, int top, int bottom, int slotHeight)
    {
        super(mc, width, height, top, bottom, slotHeight);
        this.obsidianClientSettings = obsidianClientSettings;
        
        this.currentModuleIndex = 0;
    }
	
    protected int getSize()
    {
        return ObsidianClient.getClient().getModuleManager().getModuleList().size();
    }

    //Will be called, when an element was clicked:
    protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY)
    {
        this.currentIndex = slotIndex;
        boolean flag = currentIndex >= 0 && currentIndex < this.getSize();
        
        this.obsidianClientSettings.btnToggle.enabled = flag;
        if (ObsidianClient.getClient().getModuleManager().getModule(slotIndex).getOptionsScreen() != null) {
        	 this.obsidianClientSettings.btnOptions.enabled = flag;
        	 
        } else {
        	this.obsidianClientSettings.btnOptions.enabled = !flag;
        }
        
        if (!flag) {
        	this.currentIndex = -1;
        }

        if (isDoubleClick && flag)
        {
            ObsidianClient.getClient().getModuleManager().getModule(slotIndex).toggle();
        }
    }

    //Returns true, if the slot is selected:
    protected boolean isSelected(int slotIndex)
    {
        return slotIndex == this.currentIndex;
    }

    protected int getContentHeight()
    {
        return ObsidianClient.getClient().getModuleManager().getModuleList().size() * 36;
    }

    public Module getSelectedModule() {
    	return ObsidianClient.getClient().getModuleManager().getModule(currentIndex);
    }
    
    //Overrides the default drawSelectionBox method, so that there is no background when selected:
    @Override
    protected void drawSelectionBox(int p_148120_1_, int p_148120_2_, int mouseXIn, int mouseYIn) {
    	GlStateManager.enableAlpha();
    	
    	int i = this.getSize();

        for (int j = 0; j < i; ++j)
        {
            int k = p_148120_2_ + j * this.slotHeight + this.headerPadding;
            int l = this.slotHeight - 4;

            if (k > this.bottom || k + l < this.top)
            {
                this.func_178040_a(j, p_148120_1_, k);
            }            

            if (k >= this.top - this.slotHeight && k <= this.bottom)
            {
                this.drawSlot(j, p_148120_1_, k, l, mouseXIn, mouseYIn);
            }
        }
    }
    
    //Draws the background:
    protected void drawBackground()
    {
        GraphicUtil.drawRect(0, 0, this.width, this.height, Integer.MIN_VALUE);
    }

    //Draws a slot:
    protected void drawSlot(int entryID, int x, int y, int slotHeight, int mouseXIn, int mouseYIn)
    {       
        //Draws the background:
    	if (entryID != currentIndex) {
    		mc.getTextureManager().bindTexture(new ResourceLocation("obsidianclient/settingsListItemLight.png"));
    	} else {
    		mc.getTextureManager().bindTexture(new ResourceLocation("obsidianclient/settingsListItemDark.png"));
    	}        
        GraphicUtil.drawModalRectWithCustomSizedTexture(x, y, 0, 0, 225, slotHeight, 225, slotHeight);
        GlStateManager.color(1.0f, 1.0f, 1.0f);
        
        //Draws a logo of the module:
        GlStateManager.enableAlpha();
        ResourceLocation location = ObsidianClient.getClient().getModuleManager().getModule(entryID).getImage();
        mc.getTextureManager().bindTexture(location);
        GraphicUtil.drawModalRectWithCustomSizedTexture(x + 5, y + 5, 0, 0, 20, 20, 20, 20);
        
        //Draws the on/off light:
        if (ObsidianClient.getClient().getModuleManager().getModule(entryID).isToggled()) {
        	mc.getTextureManager().bindTexture(new ResourceLocation("obsidianclient/greenLight.png"));
        } else {
        	mc.getTextureManager().bindTexture(new ResourceLocation("obsidianclient/redLight.png"));
        }
        GraphicUtil.drawModalRectWithCustomSizedTexture(x + 200, y + 5, 0, 0, 20, 20, 20, 20);
        GlStateManager.disableAlpha();
        
        //Draws a string with the name of the module:
        String s = ObsidianClient.getClient().getModuleManager().getModule(entryID).getName();
        mc.fontRendererObj.drawStringWithShadow(s, x + 35, y + 11, Color.WHITE.getRGB());
    	
        //Sets the right module index (+1):
        if (this.currentModuleIndex > this.getSize()) {
        	this.currentModuleIndex ++;
        	
        } else {
        	this.currentModuleIndex = 0;
        }        
    }
	
}

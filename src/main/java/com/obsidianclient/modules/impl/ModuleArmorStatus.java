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

package com.obsidianclient.modules.impl;

import java.awt.Color;

import com.obsidianclient.utils.GraphicUtil;
import org.lwjgl.opengl.GL11;

import com.obsidianclient.ObsidianClient;
import com.obsidianclient.utils.Savable;
import com.obsidianclient.modules.ModuleDraggable;
import com.obsidianclient.modules.config.impl.ConfigArmorStatus;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ModuleArmorStatus extends ModuleDraggable {
	
	public static final int WIDTH_WITH_DAMAGE = 60;
	public static final int WIDTH_WITHOUT_DAMAGE = 16;
	
	//Settings:
	@Savable
	public static boolean withDamageString = false;
	@Savable
	public static int percentAccuracy = 1;
	
	public ModuleArmorStatus() {
		//Initializing the module:
		super("Armor Status", "Displays the status of your armor",
				false, 3,
				new ResourceLocation("textures/items/diamond_chestplate.png"),
				null,
				ObsidianClient.getClient().getAnchorUtil().getTopLeft(),
				0, 0,
				WIDTH_WITH_DAMAGE, 64);
		this.setOptionsScreen(new ConfigArmorStatus(this));
	}
	
	@Override
	public void renderWidget() {
		//Looping through the armor-inventory of the player:
		for (int i = 0; i < Minecraft.getMinecraft().thePlayer.inventory.armorInventory.length; i++) {
			ItemStack item = Minecraft.getMinecraft().thePlayer.inventory.armorInventory[i];			
			if (item != null) {
				//Draws the current item stack:
				this.drawItemStack(i, item);
			} else {
				//Draws a placeholder image:
				this.drawDummyStack(i);
			}
		}
	}
	
	@Override
	public void renderDummy() {
		//Draws some dummy items for the hud-editing:
		this.drawDummyStack(3);
		this.drawDummyStack(2);
		this.drawDummyStack(1);
		this.drawDummyStack(0);
	}
	
	//Draws a placeholder image:
	private void drawDummyStack(int y) {
		int yAdd = (-16 * y) + 48;
		
		if (withDamageString) {
			
			//Make sure that the width is correct:
			if (this.getWidth() != WIDTH_WITH_DAMAGE) {
				this.setWidth(WIDTH_WITH_DAMAGE);
			}
			
			//Draws the long background:
			GraphicUtil.drawRect(this.getXInt(), this.getYInt() + yAdd, this.getXInt() + this.getWidth(), this.getYInt() + yAdd + 16, ObsidianClient.GRAY_OVERLAY_COLOR);
			
			//Draws the damage counter placeholder:
			Minecraft.getMinecraft().fontRendererObj.drawString("---", this.getXInt() + 30, this.getYInt() + yAdd + 5, Color.WHITE.getRGB(), false);
			
		} else {
			
			//Make sure the width is correct:
			if (this.getWidth() != WIDTH_WITHOUT_DAMAGE) {
				this.setWidth(WIDTH_WITHOUT_DAMAGE);
			}
			
			//Draws the short background:				
			GraphicUtil.drawRect(this.getXInt(), this.getYInt() + yAdd, this.getXInt() + 16, this.getYInt() + yAdd + 16, ObsidianClient.GRAY_OVERLAY_COLOR);
		}
		
		//Draws the item stack placeholder:
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		if (y == 3) {
        	Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("obsidianclient/ArmorStatus/helmet.png"));
        } else if (y == 2) {
        	Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("obsidianclient/ArmorStatus/chestplate.png"));
        } else if (y == 1) {
        	Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("obsidianclient/ArmorStatus/leggins.png"));
        } else if (y == 0) {
        	Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("obsidianclient/ArmorStatus/boots.png"));
        }
        GraphicUtil.drawModalRectWithCustomSizedTexture(this.getXInt(), this.getYInt() + yAdd, 0, 0, 16, 16, 16, 16);
        
	}
	
	//Draws an ItemStack:
	private void drawItemStack(int y, ItemStack itemStack) {
		int yAdd = (-16 * y) + 48;
		if (itemStack != null) {
			
			GL11.glPushMatrix();
			
			if (withDamageString) {
				
				//Make sure that the width is correct:
				if (this.getWidth() != WIDTH_WITH_DAMAGE) {
					this.setWidth(WIDTH_WITH_DAMAGE);
				}
				
				//Draws the long background:
				GraphicUtil.drawRect(this.getXInt(), this.getYInt() + yAdd, this.getXInt() + this.getWidth(), this.getYInt() + yAdd + 16, ObsidianClient.GRAY_OVERLAY_COLOR);
				
				//Draws the damage counter:
				if (itemStack.getItem().isDamageable()) {
					double damage = ((itemStack.getMaxDamage() - itemStack.getItemDamage()) / (double) itemStack.getMaxDamage()) * 100;
					
					//Getting the right format and xAdd:
					String formatString = "";
					int xAdd = 0;
					
					if (percentAccuracy == 0) {
						formatString = "%.0f%%";
						xAdd = 28;
					} else if (percentAccuracy == 1) {
						formatString = "%.1f%%";
						xAdd = 23;
					} else if (percentAccuracy == 2) {
						formatString = "%.2f%%";
						xAdd = 20;
					}
					
					String s = String.format(formatString, damage);					
					Minecraft.getMinecraft().fontRendererObj.drawString(s, this.getXInt() + xAdd, this.getYInt() + yAdd + 5, Color.WHITE.getRGB(), false);
				}
				
			} else {
				
				//Make sure the width is correct:
				if (this.getWidth() != WIDTH_WITHOUT_DAMAGE) {
					this.setWidth(WIDTH_WITHOUT_DAMAGE);
				}
				
				//Draws the short background:				
				GraphicUtil.drawRect(this.getXInt(), this.getYInt() + yAdd, this.getXInt() + 16, this.getYInt() + yAdd + 16, ObsidianClient.GRAY_OVERLAY_COLOR);
			}
			
			//Draws the item stack:
			RenderHelper.enableGUIStandardItemLighting();
	        Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(itemStack, this.getXInt(), this.getYInt() + yAdd);
	        Minecraft.getMinecraft().getRenderItem().renderItemOverlayIntoGUI(Minecraft.getMinecraft().fontRendererObj, itemStack, this.getXInt(), this.getYInt() + yAdd, "");
			
			GL11.glPopMatrix();
			
		}
	}
	
}

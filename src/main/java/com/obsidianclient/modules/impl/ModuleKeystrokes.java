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

import com.obsidianclient.utils.GraphicUtil;
import com.obsidianclient.utils.Savable;
import org.lwjgl.opengl.GL11;

import com.obsidianclient.ObsidianClient;
import com.obsidianclient.modules.ModuleDraggable;
import com.obsidianclient.modules.config.impl.ConfigKeystrokes;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ResourceLocation;

public class ModuleKeystrokes extends ModuleDraggable {

	//The currently active collection of keys:
	private static KeystrokesMode currentMode;
	
	//The different settings for this module:
	@Savable
	public static boolean areMouseButtonsEnabled = false;
	@Savable
	public static boolean isSpaceBarEnabled = true;
	
	public ModuleKeystrokes() {
		super("Keystrokes", "Displays specific keys on your screen!",
				false, 7,
				new ResourceLocation("obsidianclient/keystrokes.png"),
				null,
				ObsidianClient.getClient().getAnchorUtil().getTopLeft(),
				0, 0,
				0, 0);
		this.setOptionsScreen(new ConfigKeystrokes(this));
	}
	
	//Draws the current collection of keys on the screen:
	@Override
	public void renderWidget() {
		
		boolean wasBlendEnabled = GL11.glIsEnabled(GL11.GL_BLEND);
		GlStateManager.disableBlend();
		boolean wasAlphaEnabled = GL11.glIsEnabled(GL11.GL_ALPHA_TEST);
		GlStateManager.enableAlpha();
		
		//Updates everything:
		this.updateKeystrokesMode();
		this.updateBounds();
		
		//Draws every key from the current collection:
		for (KeystrokesKey key : currentMode.getKeys()) {
			
			//Finding out the right key color:
			int keyColor;
			if (key.isKeyDown()) {
				keyColor = Integer.MAX_VALUE;
			} else {
				keyColor = ObsidianClient.GRAY_OVERLAY_COLOR;
			}
			
			//Drawing the key background:
			GraphicUtil.drawRect(this.getXInt() + key.getKeyX(), this.getYInt() + key.getKeyY(), this.getXInt() + key.getKeyX() + key.getKeyWidth(), this.getYInt() + key.getKeyY() + key.getKeyHeight(), keyColor);
			
			//Finding out the with of the key name:
			int keyTextWidth = Minecraft.getMinecraft().fontRendererObj.getStringWidth(key.getName());
			
			//Finding out the right color for the key name:
			int keyTextColor;
			if (key.isKeyDown()) {
				keyTextColor = ObsidianClient.GRAY_OVERLAY_COLOR;
			} else {
				keyTextColor = Integer.MAX_VALUE;
			}
			
			//Drawing the name of the key:
			Minecraft.getMinecraft().fontRendererObj.drawString(key.getName(), this.getXInt() + key.getKeyX() + key.getKeyWidth() / 2.0F - keyTextWidth / 2.0F, this.getYInt() + key.getKeyY() + key.getKeyHeight() / 2.0F - 4, keyTextColor, false);
			
		}
		
		if (wasBlendEnabled) {
			GlStateManager.enableBlend();
		}
		if (!wasAlphaEnabled) {
			GlStateManager.disableAlpha();
		}
	}
	
	//Draws a dummy for gui editing in the HudConfigScreen:
	@Override
	public void renderDummy() {
		
		boolean wasBlendEnabled = GL11.glIsEnabled(GL11.GL_BLEND);
		GlStateManager.disableBlend();
		boolean wasAlphaEnabled = GL11.glIsEnabled(GL11.GL_ALPHA_TEST);
		GlStateManager.enableAlpha();
		
		//Updates everything:
		this.updateKeystrokesMode();
		this.updateBounds();
		
		//Draws every key from the current collection:
		for (KeystrokesKey key : currentMode.getKeys()) {
			
			//Drawing the key background:
			GraphicUtil.drawRect(this.getXInt() + key.getKeyX(), this.getYInt() + key.getKeyY(), this.getXInt() + key.getKeyX() + key.getKeyWidth(), this.getYInt() + key.getKeyY() + key.getKeyHeight(), ObsidianClient.GRAY_OVERLAY_COLOR);
			
			//Drawing the name of the key:
			Minecraft.getMinecraft().fontRendererObj.drawString(key.getName(), this.getXInt() + key.getKeyX() + key.getKeyWidth() / 2.0F - Minecraft.getMinecraft().fontRendererObj.getStringWidth(key.getName()) / 2.0F, this.getYInt() + key.getKeyY() + key.getKeyHeight() / 2.0F - 4, Integer.MAX_VALUE, false);
			
		}
		
		if (wasBlendEnabled) {
			GlStateManager.enableBlend();
		}
		if (!wasAlphaEnabled) {
			GlStateManager.disableAlpha();
		}
		
	}
	
	//Looks which settings are enabled and sets the right KeystrokesMode:
	public void updateKeystrokesMode() {
		
		if (areMouseButtonsEnabled) {
			
			if (isSpaceBarEnabled) {
				currentMode = KeystrokesMode.WASD_MOUSE_SPACE;
				updateBounds();
				
			} else {
				currentMode = KeystrokesMode.WASD_MOUSE;
				updateBounds();
			}
			
		} else if (isSpaceBarEnabled) {
			
			if (areMouseButtonsEnabled) {
				currentMode = KeystrokesMode.WASD_MOUSE_SPACE;
				updateBounds();
				
			} else {
				currentMode = KeystrokesMode.WASD_SPACE;
				updateBounds();
			}
			
		} else {
			currentMode = KeystrokesMode.WASD;
			updateBounds();
		}
		
	}
	
	//Updates the size of the module:
	private void updateBounds() {
		this.setWidth(currentMode.getWidth());
		this.setHeight(currentMode.getHeight());
	}
	
	//All the different key collections (modes) of the Keystrokes module:
	public enum KeystrokesMode {
		
		//The different key collections to choose from:
		WASD(KeystrokesKey.KEY_W, KeystrokesKey.KEY_A, KeystrokesKey.KEY_S, KeystrokesKey.KEY_D),
		WASD_MOUSE(KeystrokesKey.KEY_W, KeystrokesKey.KEY_A, KeystrokesKey.KEY_S, KeystrokesKey.KEY_D, KeystrokesKey.KEY_LMB, KeystrokesKey.KEY_RMB),
		WASD_SPACE(KeystrokesKey.KEY_W, KeystrokesKey.KEY_A, KeystrokesKey.KEY_S, KeystrokesKey.KEY_D, new KeystrokesKey("---", Minecraft.getMinecraft().gameSettings.keyBindJump, 1, 41, 58, 14)),
		WASD_MOUSE_SPACE(KeystrokesKey.KEY_W, KeystrokesKey.KEY_A, KeystrokesKey.KEY_S, KeystrokesKey.KEY_D, KeystrokesKey.KEY_LMB, KeystrokesKey.KEY_RMB, new KeystrokesKey("---", Minecraft.getMinecraft().gameSettings.keyBindJump, 1, 61, 58, 14));
		
		private final KeystrokesKey[] keys;
		private int width = 0;
		private int height = 0;
		
		KeystrokesMode(KeystrokesKey... keys) {
			this.keys = keys;
			
			//Calculate the size of the Keystrokes module with this KeystrokesMode:
			for (KeystrokesKey key : keys) {
				this.width = Math.max(this.width, key.getKeyX() + key.getKeyWidth() + 1);
				this.height = Math.max(this.height, key.getKeyY() + key.getKeyHeight() + 1);
			}
		}
		
		public KeystrokesKey[] getKeys() {
			return keys;
		}
		
		public int getWidth() {
			return width;
		}
		
		public int getHeight() {
			return height;
		}
		
	}
	
	//A class representing a key:
	private static class KeystrokesKey {
		
		//Some predefined keys:
		public static final KeystrokesKey KEY_W = new KeystrokesKey("W", Minecraft.getMinecraft().gameSettings.keyBindForward, 21, 1, 18, 18);
		public static final KeystrokesKey KEY_A = new KeystrokesKey("A", Minecraft.getMinecraft().gameSettings.keyBindLeft, 1, 21, 18, 18);
		public static final KeystrokesKey KEY_S = new KeystrokesKey("S", Minecraft.getMinecraft().gameSettings.keyBindBack, 21, 21, 18, 18);
		public static final KeystrokesKey KEY_D = new KeystrokesKey("D", Minecraft.getMinecraft().gameSettings.keyBindRight, 41, 21, 18, 18);
		
		public static final KeystrokesKey KEY_LMB = new KeystrokesKey("LMB", Minecraft.getMinecraft().gameSettings.keyBindAttack, 1, 41, 28, 18);
		public static final KeystrokesKey KEY_RMB = new KeystrokesKey("RMB", Minecraft.getMinecraft().gameSettings.keyBindUseItem, 31, 41, 28, 18);
		
		private final String name;
		private final KeyBinding keyBinding;
		private final int keyX;
		private final int keyY;
		private final int keyWidth;
		private final int keyHeight;
		
		public KeystrokesKey(String name, KeyBinding keyBinding, int keyX, int keyY, int keyWidth, int keyHeight) {
			this.name = name;
			this.keyBinding = keyBinding;
			this.keyX = keyX;
			this.keyY = keyY;
			this.keyWidth = keyWidth;
			this.keyHeight = keyHeight;
		}
		
		public String getName() {
			return name;
		}
		
		public boolean isKeyDown() {
			return keyBinding.isKeyDown();
		}
		
		public int getKeyX() {
			return keyX;
		}
		
		public int getKeyY() {
			return keyY;
		}
		
		public int getKeyWidth() {
			return keyWidth;
		}
		
		public int getKeyHeight() {
			return keyHeight;
		}
		
	}

}

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

package com.obsidianclient.asm;

import com.obsidianclient.ObsidianClient;
import com.obsidianclient.modules.impl.ModuleBow;
import com.obsidianclient.modules.impl.ModuleScoreboard;
import com.obsidianclient.utils.GraphicUtil;
import net.minecraft.client.gui.*;
import net.minecraft.util.EnumParticleTypes;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.obsidianclient.gui.GuiHudSettings;
import com.obsidianclient.gui.override.GuiObsidianClientChat;
import com.obsidianclient.gui.override.GuiObsidianClientIngameMenu;

import net.minecraft.client.Minecraft;

/**
 * The Methods in this class are called from the ASM transformer classes.
 * DO NOT delete them!
 */
public class Hooks {

	/**
	 * Is called on startup.
	 */
	public static void onStart() {

		ObsidianClient.getClient().init();

	}

	/**
	 * Is called on every tick.
	 */
	public static void onTick() {

		if (GraphicUtil.hasResolutionChanged()) {
			ObsidianClient.getClient().getAnchorUtil().refresh();
			ObsidianClient.getClient().getModuleManager().refreshPositions();
		}

		ObsidianClient.getClient().getModuleManager().update();

		if(Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {

			if (!(Minecraft.getMinecraft().currentScreen instanceof GuiHudSettings)) {

				ObsidianClient.getClient().getHudSettingsScreen().setParentScreen(null);
				Minecraft.getMinecraft().displayGuiScreen(ObsidianClient.getClient().getHudSettingsScreen());

				//Need to be called to prevent a few bugs:
				Mouse.setGrabbed(false);

			}

		}

	}

	/**
	 * Is called before a gui opens.
	 * @param screen The gui screen that is about to be opened.
	 * @return Return true to cancel this event.
	 */
	public static boolean onGuiOpen(GuiScreen screen) {

		//### Disabled because of bugs! ###
		//Changing the Main-Menu-Screen:
		//if (screen instanceof GuiMainMenu) {
		//	//If it is already an instance of GuiObsidianClientMainMenu, we cancel the operation
		//	//to prevent an endless loop that would cause a StackOverflowError!
		//	if (screen instanceof GuiObsidianClientMainMenu) {
		//		return false;
		//	}
		//	Minecraft.getMinecraft().displayGuiScreen(new GuiObsidianClientMainMenu());
		//	return true;
		//}

		//Changing the Chat-Screen:
		if (screen instanceof GuiChat) {
			//If it is already an instance of GuiObsidianClientChat, we cancel the operation
			//to prevent an endless loop that would cause a StackOverflowError!
			if (screen instanceof GuiObsidianClientChat) {
				return false;
			}
			Minecraft.getMinecraft().displayGuiScreen(new GuiObsidianClientChat());
			return true;
		}

		//Changing the Ingame-Menu-Screen:
		if (screen instanceof GuiIngameMenu) {
			//If it is already an instance of GuiObsidianClientIngameMenu, we cancel the operation
			//to prevent an endless loop that would cause a StackOverflowError!
			if (screen instanceof GuiObsidianClientIngameMenu) {
				return false;
			}
			Minecraft.getMinecraft().displayGuiScreen(new GuiObsidianClientIngameMenu());
			return true;
		}

		return false;
	}

	/**
	 * Is called when the text overlay in GuiIngame is going to be rendered.
	 */
	public static void onRenderTextOverlay() {

		ObsidianClient.getClient().getModuleManager().render();

	}

	/**
	 * Sets the String for the red numbers in the Scoreboard.
	 * @param valueBefore The original (unmodified) string.
	 * @return The by you modified string.
	 */
	public static String setScoreboardNumbers(String valueBefore) {

		//Checks if the red numbers should be drawn or not:
		if (ObsidianClient.getClient().getModuleManager().getModule(1).isToggled()) {

			if (ModuleScoreboard.hideRedNumbers) {
				return "";
			}

		}

		return valueBefore;

	}

	/**
	 * Decides if arrows have particles:
	 * @param isArrowCritical Is the current arrow critical?
	 * @return True, if the arrow should have particles.
	 */
	public static boolean spawnArrowParticles(boolean isArrowCritical) {

		if (ObsidianClient.getClient().getModuleManager().getModule(0).isToggled() && ModuleBow.alwaysShowParticles) {
			return true;
		} else {
			return isArrowCritical;
		}

	}

	/**
	 * Sets the particle type for arrows.
	 * Default Value: EnumParticleTypes.CRIT
	 * @return the particle type
	 */
	public static EnumParticleTypes setArrowParticleType() {

		if (ObsidianClient.getClient().getModuleManager().getModule(0).isToggled()) {
			return (EnumParticleTypes) ModuleBow.particleList.get(ModuleBow.particleListIndex);
		}

		return EnumParticleTypes.CRIT;

	}
	
}
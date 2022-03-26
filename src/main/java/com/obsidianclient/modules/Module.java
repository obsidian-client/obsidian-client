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

package com.obsidianclient.modules;

import com.obsidianclient.modules.config.GuiModuleConfigScreen;
import com.obsidianclient.utils.Savable;

import net.minecraft.util.ResourceLocation;

public abstract class Module {

	private final String name;
	private final String description;
	private final ResourceLocation image;
	private final int id;
	private GuiModuleConfigScreen optionsScreen;
	@Savable
	private boolean toggled;

	public Module(String name, String description, boolean toggled, int id, ResourceLocation image, GuiModuleConfigScreen optionsScreen) {
		this.name = name;
		this.description = description;
		this.toggled = toggled;
		this.id = id;
		this.image = image;
		this.optionsScreen = optionsScreen;
	}

	/**
	 * Update event, called every tick.
	 */
	public void update() {}

	/**
	 * Enable event, called when the module is enabled.
	 */
	public void enable() {}

	/**
	 * Disable event, called when the module is disabled.
	 */
	public void disable() {}

	/**
	 * Toggles this module.
	 */
	public final void toggle() {
		this.toggled = !toggled;
		
		if (this.toggled) {
			this.enable();
		} else {
			this.disable();
		}
	}
	
	public final String getName() {
		return this.name;
	}
	
	public final int getId() {
		return this.id;
	}
	
	public final boolean isToggled() {
		return this.toggled;
	}

	@Deprecated //Use toggle() instead!
	public final void setToggled(boolean toggled) {
		this.toggled = toggled;
	}

	public final String getDescription() {
		return description;
	}

	public final ResourceLocation getImage() {
		return image;
	}

	public final GuiModuleConfigScreen getOptionsScreen() {
		return optionsScreen;
	}

	public final void setOptionsScreen(GuiModuleConfigScreen optionsScreen) {
		this.optionsScreen = optionsScreen;
	}

}

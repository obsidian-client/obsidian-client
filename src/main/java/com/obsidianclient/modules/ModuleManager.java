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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.obsidianclient.utils.FileUtil;
import com.obsidianclient.modules.impl.*;
import org.apache.logging.log4j.LogManager;

import com.obsidianclient.ObsidianClient;
import com.obsidianclient.modules.impl.ModuleScoreboard;

/**
 * Manages all the modules.
 */
public class ModuleManager {

	private final List<Module> modules;
	
	public ModuleManager() {
		LogManager.getLogger().info("[Obsidian Client - Module Manager] Initializing all modules!");
		this.modules = new ArrayList<Module>();
		
		//Place to register/add all the modules:
		this.addModule(new ModuleBow());
		this.addModule(new ModuleScoreboard());
		this.addModule(new ModuleFullbright());
		
		//Draggable modules:
		this.addModule(new ModuleArmorStatus());
		this.addModule(new ModuleFPS());
		this.addModule(new ModuleCPS());
		this.addModule(new ModulePosition());
		this.addModule(new ModuleKeystrokes());
	}

	/**
	 * Adds a module to the list.
	 * @param module The module to add.
	 */
	public void addModule(Module module) {
		this.modules.add(module);
	}

	/**
	 * Gets a module by the name.
	 * @param name The name of the module.
	 * @return Returns the module.
	 */
	public Module getModule(String name) {
		for (Module module : modules) {
			if (module.getName().equalsIgnoreCase(name)) {
				return module;
			}
		}
		return null;
	}

	/**
	 * Gets a module by the id.
	 * @param id The id of the module.
	 * @return Returns the module.
	 */
	public Module getModule(int id) {
		for (Module module : modules) {
			if (module.getId() == id) {
				return module;
			}
		}
		return null;
	}

	/**
	 * Calls the update event of each module.
	 */
	public void update() {
		for (Module module : this.modules) {
			if (module.isToggled()) {
				module.update();
			}
		}
	}

	/**
	 * Calls the render event of each module.
	 */
	public void render() {
		for (Module module : this.modules) {
			if (module instanceof ModuleDraggable) {
				if (module.isToggled()) {
					ModuleDraggable m = (ModuleDraggable) module;
					if (m.shouldRenderWidget) {
						m.renderWidget();
					}
				}
			}
		}
	}

	/**
	 * Refreshes the position of each module.
	 */
	public void refreshPositions() {
		for (Module m : this.modules) {
			if (m instanceof ModuleDraggable) {
				ModuleDraggable module = (ModuleDraggable) m;
				module.refreshPosition();
			}
		}
	}

	/**
	 * Loads the configuration for a specific module.
	 * @param module The module to load the configuration for.
	 */
	public void loadConfig(Module module) {
		try {
			FileUtil.fillObjectFromFile(module, new File(ObsidianClient.MODULE_CONFIG_FOLDER, module.getClass().getSimpleName() + ".cfg"));
		} catch (FileNotFoundException e){
			//If it's a FileNotFoundException, then most likely the Module was never saved before, like with a new installation:
			LogManager.getLogger().info("[Obsidian Client - Module Manager] No config found for module: " + module.getName() + "!");
		} catch (SecurityException | IllegalAccessException | NoSuchFieldException | IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Saves the configuration for a specific module.
	 * @param module The module to save the configuration from.
	 */
	public void saveConfig(Module module) {
		try {
			FileUtil.saveObjectToFile(module, new File(ObsidianClient.MODULE_CONFIG_FOLDER, module.getClass().getSimpleName() + ".cfg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Loads the configuration for all modules.
	 */
	public void loadWholeConfig() {
		LogManager.getLogger().info("[Obsidian Client - Module Manager] Loading all module settings!");
		for (Module module : this.modules) {
			this.loadConfig(module);
		}
	}

	/**
	 * Saves the configuration for all modules.
	 */
	public void saveWholeConfig() {
		LogManager.getLogger().info("[Obsidian Client - Module Manager] Saving all module settings!");
		for (Module module : this.modules) {
			this.saveConfig(module);
		}
	}

	/**
	 * Gets the list of all modules.
	 * @return the list
	 */
	public List<Module> getModuleList() {
		return modules;
	}
}

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

import com.obsidianclient.modules.Module;

import com.obsidianclient.modules.config.impl.ConfigScoreboard;
import com.obsidianclient.utils.Savable;
import net.minecraft.util.ResourceLocation;

public class ModuleScoreboard extends Module {

	//Settings:
	@Savable
	public static boolean hideRedNumbers = false;

	public ModuleScoreboard() {
		super("Custom Scoreboard", "Changes the behavior of the Scoreboard", false, 1, new ResourceLocation("obsidianclient/scoreboard.png"), null);
		this.setOptionsScreen(new ConfigScoreboard(this));
	}

}

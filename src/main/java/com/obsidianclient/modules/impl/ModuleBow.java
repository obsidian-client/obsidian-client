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

import com.obsidianclient.modules.config.impl.ConfigBow;
import com.obsidianclient.utils.Savable;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class ModuleBow extends Module {

	//Settings:
	@Savable
	public static boolean alwaysShowParticles = false;
	@Savable
	public static int particleListIndex = 0;

	public static List<Enum> particleList = new ArrayList<Enum>();

	public ModuleBow() {
		super("Custom Bow", "Changes the behavior of the Bow Item.", false, 0, new ResourceLocation("textures/items/bow_standby.png"), null);
		this.setOptionsScreen(new ConfigBow(this));

		particleList.add(0, EnumParticleTypes.CRIT);
		particleList.add(1, EnumParticleTypes.CRIT_MAGIC);
		particleList.add(2, EnumParticleTypes.NOTE);
		particleList.add(3, EnumParticleTypes.HEART);
		particleList.add(4, EnumParticleTypes.REDSTONE);
		particleList.add(5, EnumParticleTypes.LAVA);
		particleList.add(6, EnumParticleTypes.WATER_DROP);
		particleList.add(7, EnumParticleTypes.SLIME);
		particleList.add(8, EnumParticleTypes.SNOWBALL);
		particleList.add(9, EnumParticleTypes.SPELL);
		particleList.add(10, EnumParticleTypes.SPELL_INSTANT);
		particleList.add(11, EnumParticleTypes.SPELL_MOB);
		particleList.add(12, EnumParticleTypes.SPELL_WITCH);
		particleList.add(13, EnumParticleTypes.ENCHANTMENT_TABLE);
		particleList.add(14, EnumParticleTypes.VILLAGER_HAPPY);
		particleList.add(15, EnumParticleTypes.VILLAGER_ANGRY);

	}

}

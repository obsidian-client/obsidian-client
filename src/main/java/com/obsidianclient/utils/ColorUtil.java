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

package com.obsidianclient.utils;

import java.awt.Color;
import java.util.Random;

public class ColorUtil {

	/**
	 * Returns a random color out of the standard Java AWT colors.
	 */
	public static Color getRandomColor() {
		Random random = new Random();
		int i = random.nextInt(13);
		Color color = Color.WHITE;		
		switch (i) {
			case 0:
				color = Color.BLACK;
				break;				
			case 1:
				color = Color.BLUE;
				break;
			case 2:
				color = Color.CYAN;
				break;
			case 3:
				color = Color.DARK_GRAY;
				break;
			case 4:
				color = Color.GRAY;
				break;
			case 5:
				color = Color.GREEN;
				break;
			case 6:
				color = Color.LIGHT_GRAY;
				break;
			case 7:
				color = Color.MAGENTA;
				break;
			case 8:
				color = Color.ORANGE;
				break;
			case 9:
				color = Color.PINK;
				break;
			case 10:
				color = Color.RED;
				break;
			case 11:
				color = Color.WHITE;
				break;
			case 12:
				color = Color.YELLOW;
				break;
		}
		return color;
	}
	
}

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

/**
 * Utility for simple math operations.
 */
public class MathUtil {

    /**
     * Gets the largest out of an unlimited amount of values.
     * @param values The list of values to get the largest from.
     * @return Returns the largest value.
     */
    public static int getLargestValue(Integer... values) {
        int i = Math.max(values[0], values[1]);
        for (int counter = 2; counter < values.length; counter++) {
            int j = values[counter];
            i = Math.max(i, j);
        }
        return i;
    }

    /**
     * Gets the smallest out of an unlimited amount of values.
     * @param values The list of values to get the smallest from.
     * @return Returns the smallest value.
     */
    public static int getSmallestValue(Integer... values) {
        int i = Math.min(values[0], values[1]);
        for (int counter = 2; counter < values.length; counter++) {
            int j = values[counter];
            i = Math.min(i, j);
        }
        return i;
    }

}

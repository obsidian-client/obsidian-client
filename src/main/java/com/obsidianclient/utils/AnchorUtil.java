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

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import java.util.HashMap;

/**
 * Utility containing anchors that help the modules to
 * stay in place when the screen resizes.
 */
public class AnchorUtil {

    private static final Minecraft mc = Minecraft.getMinecraft();

    private final HashMap<Point, String> anchorList;

    public AnchorUtil() {
        this.anchorList = new HashMap<Point, String>();
        this.fillAnchorList();
    }

    public void refresh() {
        this.anchorList.clear();
        this.fillAnchorList();
    }

    private void fillAnchorList() {
        this.anchorList.put(this.getTopLeft(), "TopLeft");
        this.anchorList.put(this.getTopRight(), "TopRight");
        this.anchorList.put(this.getTopMiddle(), "TopMiddle");
        this.anchorList.put(this.getBottomLeft(), "BottomLeft");
        this.anchorList.put(this.getBottomRight(), "BottomRight");
        this.anchorList.put(this.getBottomMiddle(), "BottomMiddle");
        this.anchorList.put(this.getMiddle(), "Middle");
        this.anchorList.put(this.getMiddleLeft(), "MiddleLeft");
        this.anchorList.put(this.getMiddleRight(), "MiddleRight");
    }

    public HashMap<Point, String> getAllAnchors() {
        return this.anchorList;
    }

    public Point getTopLeft() { return new Point(0, 0); }

    public Point getTopRight() { return new Point(new ScaledResolution(mc).getScaledWidth_double(), 0); }

    public Point getTopMiddle() { return new Point(new ScaledResolution(mc).getScaledWidth_double() / 2, 0); }

    public Point getBottomLeft() { return new Point(0, new ScaledResolution(mc).getScaledHeight_double()); }

    public Point getBottomRight() { return new Point(new ScaledResolution(mc).getScaledWidth_double(), new ScaledResolution(mc).getScaledHeight_double()); }

    public Point getBottomMiddle() { return new Point(new ScaledResolution(mc).getScaledWidth_double() / 2, new ScaledResolution(mc).getScaledHeight_double()); }

    public Point getMiddle() { return new Point(new ScaledResolution(mc).getScaledWidth_double() / 2, new ScaledResolution(mc).getScaledHeight_double() / 2); }

    public Point getMiddleLeft() { return new Point(0, new ScaledResolution(mc).getScaledHeight_double() / 2); }

    public Point getMiddleRight() { return new Point(new ScaledResolution(mc).getScaledWidth_double(), new ScaledResolution(mc).getScaledHeight_double() / 2); }

}

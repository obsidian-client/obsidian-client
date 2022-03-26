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

import com.obsidianclient.ObsidianClient;
import com.obsidianclient.modules.config.GuiModuleConfigScreen;
import com.obsidianclient.utils.Point;
import com.obsidianclient.utils.Savable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public abstract class ModuleDraggable extends Module {

	//The relative position of the module:
	@Savable
	private String anchor;
	@Savable
	private double offsetX;
	@Savable
	private double offsetY;

	//The absolute position of the module:
	private double virtualX;
	private double virtualY;

	//Boundaries:
	private double width;
	private double height;

	//Whether the widget or the dummy should be rendered:
	public boolean shouldRenderWidget = true;
	
	public ModuleDraggable(String name, String description, boolean toggled, int id, ResourceLocation image, GuiModuleConfigScreen optionsScreen, Point anchor, double offsetX, double offsetY, double width, double height) {
		super(name, description, toggled, id, image, optionsScreen);
		this.anchor = ObsidianClient.getClient().getAnchorUtil().getAllAnchors().get(anchor);
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.width = width;
		this.height = height;
		this.refreshPosition();
	}

	/**
	 * Render dummy event, called on every frame inside the GuiHudSettings screen.
	 */
	public void renderDummy() {}

	/**
	 * Render event, called on every frame outside the GuiHudSettings screen.
	 */
	public void renderWidget() {}

	/**
	 * Recalculates the position of this module.
	 * Has to be called when the display resolution changes!
	 */
	public final void refreshPosition() {
		//Getting the anchor represented by the name:
		AtomicReference<Point> aPoint = new AtomicReference<Point>();
		ObsidianClient.getClient().getAnchorUtil().getAllAnchors().forEach((p, s) -> {
			if (s.equals(this.anchor)) {
				aPoint.set(p);
			}
		});
		Point anchor = aPoint.get();

		//Adding the offset:
		double x = anchor.getX() + offsetX;
		double y = anchor.getY() + offsetY;

		//Setting the new 'virtual' position:
		this.virtualX = x;
		this.virtualY = y;
	}
	
	public final void setWidth(double width) {
		this.width = width;
	}

	public final void setHeight(double height) {
		this.height = height;
	}

	public final double getWidth() {
		return this.width;
	}
	
	public final double getHeight() {
		return this.height;
	}

	public final Point getCenter() {
		return new Point(this.getX() + this.getWidth() / 2.0D, this.getY() + this.getHeight() / 2.0D);
	}

	public final void setPosition(Point position) {
		ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());
		Point closestAnchor = this.getClosestAnchor();
		Point offset = this.getOffsetToAnchor(closestAnchor);
		//Making sure the module can't move out of the screen:
		double x = Math.max(0, Math.min(position.getX(), Math.max(resolution.getScaledWidth_double() - this.width, 0)));
		double y = Math.max(0, Math.min(position.getY(), Math.max(resolution.getScaledHeight_double() - this.height, 0)));
		this.virtualX = x;
		this.virtualY = y;
		this.anchor = ObsidianClient.getClient().getAnchorUtil().getAllAnchors().get(closestAnchor);
		this.offsetX = offset.getX();
		this.offsetY = offset.getY();
	}

	public final void setPosition(double x, double y) {
		this.setPosition(new Point(x, y));
	}

	public final double getX() {
		return this.virtualX;
	}

	public final double getY() {
		return this.virtualY;
	}

	public final int getXInt() {
		return (int) this.getX();
	}

	public final int getYInt() {
		return (int) this.getY();
	}

	public final Point getPosition() {
		return new Point(this.getX(), this.getY());
	}

	/**
	 * Gets the closest anchor to this module.
	 * See AnchorUtil.
	 * @return The closest anchor.
	 */
	public final Point getClosestAnchor() {
		Point moduleCenter = this.getCenter();
		ArrayList<Point> anchorList = new ArrayList<Point>(ObsidianClient.getClient().getAnchorUtil().getAllAnchors().keySet());

		Point anchor = anchorList.get(0);
		double diffX = Math.abs(moduleCenter.getX() - anchor.getX());
		double diffY = Math.abs(moduleCenter.getY() - anchor.getY());
		double diff = diffX + diffY;

		for (int i = 1; i < anchorList.size(); i++) {

			Point currentAnchor = anchorList.get(i);
			double currentDiffX = Math.abs(moduleCenter.getX() - currentAnchor.getX());
			double currentDiffY = Math.abs(moduleCenter.getY() - currentAnchor.getY());
			double currentDiff = currentDiffX + currentDiffY;

			if (currentDiff <= diff) {
				anchor = currentAnchor;
				diff = currentDiff;
			}

		}
		return anchor;
	}

	/**
	 * Gets the offset this module has to a specific anchor.
	 * @param anchor The anchor to calculate the offset to.
	 * @return The offset as Point.
	 */
	public Point getOffsetToAnchor(Point anchor) {
		double diffX = this.virtualX - anchor.getX();
		double diffY = this.virtualY - anchor.getY();
		return new Point(diffX, diffY);
	}

	/**
	 * Gets the offset this module has to the closest anchor.
	 * @return The offset as Point.
	 */
	public Point getOffsetToClosestAnchor() {
		return this.getOffsetToAnchor(this.getClosestAnchor());
	}
	
}

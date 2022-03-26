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

package com.obsidianclient.gui.elements;

import java.util.List;

//The same as the GuiButtonChangeInteger Class, but with Enums:
public class GuiButtonChangeEnum extends GuiButtonObsidianClient {

    private final List<Enum> list;
    private int currentIndex;

    public GuiButtonChangeEnum(int buttonId, int x, int y, List<Enum> list) {
        super(buttonId, x, y, 200, 20, list.get(0).toString());
        this.list = list;
        this.currentIndex = 0;
    }

    public GuiButtonChangeEnum(int buttonId, int x, int y, int widthIn, int heightIn, List<Enum> list) {
        super(buttonId, x, y, widthIn, heightIn, list.get(0).toString());
        this.list = list;
        this.currentIndex = 0;
    }

    //[INFO] Call this if the button was clicked:
    public void onClick() {
        //Changing the Index:
        if (this.currentIndex < this.list.size() - 1) {
            this.currentIndex = this.currentIndex + 1;
            //Changing the display text:
            this.displayString = this.list.get(this.currentIndex).toString();
        } else {
            this.currentIndex = 0;
            //Changing the display text:
            this.displayString = this.list.get(0).toString();
        }
    }

    //To get the currently selected item:
    public Enum getSelectedItem() {
        return this.list.get(this.currentIndex);
    }

    //To set the current index:
    public void setCurrentIndex(int index) {
        this.currentIndex = index;
        this.displayString = list.get(this.currentIndex).toString();
    }

    //Gives back the whole list:
    public List<Enum> getList() {
        return this.list;
    }

}

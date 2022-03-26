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

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;

import java.awt.*;

//The default Minecraft TextField with a hint:
public class GuiTextFieldHint extends GuiTextField {

    private String hint;

    public GuiTextFieldHint(int id, FontRenderer fontRendererObj, int x, int y, int width, int height, String hint) {
        super(id, fontRendererObj, x, y, width, height);
        this.hint = hint;
    }

    @Override
    public void updateCursorCounter() {
        super.updateCursorCounter();

        //Managing the hint:
        if (this.getText().equals("") | this.getText().equals(hint)) {
            if (!this.isFocused()) {
                this.setText(hint);
                this.setTextColor(Color.LIGHT_GRAY.getRGB());
            } else {
                if (this.getText().equals("") | this.getText().equals(hint)) {
                    this.setText("");
                }
                this.setTextColor(Color.WHITE.getRGB());
            }
        }

    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getHint() {
        return hint;
    }
}

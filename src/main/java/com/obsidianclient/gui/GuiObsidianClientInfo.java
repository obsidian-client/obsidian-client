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

package com.obsidianclient.gui;

import com.obsidianclient.ObsidianClient;
import com.obsidianclient.gui.elements.GuiButtonLabel;
import com.obsidianclient.gui.elements.GuiButtonObsidianClient;
import com.obsidianclient.utils.GraphicUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GuiObsidianClientInfo extends GuiScreen {

    //The GuiScreen that opened up this one:
    private GuiScreen parentScreen = null;

    @Override
    public void initGui() {

        int btnWidth = 225;
        int offset = 65;
        this.buttonList.add(new GuiButtonObsidianClient(0, this.width / 2 - btnWidth / 2 - 3, this.height / 2 + offset, btnWidth, 20, "Back"));

        String btnLicenseText = "Obsidian Client License";
        this.buttonList.add(new GuiButtonLabel(10, this.width / 2 - mc.fontRendererObj.getStringWidth(btnLicenseText) / 2, this.height / 2 + 31 + 4, btnLicenseText, ObsidianClient.BLUE_ACCENT_COLOR, false));

        String btn3rdPartyText = "Third-party Software";
        this.buttonList.add(new GuiButtonLabel(20, this.width / 2 - mc.fontRendererObj.getStringWidth(btn3rdPartyText) / 2, this.height / 2 + 42 + 4, btn3rdPartyText, ObsidianClient.BLUE_ACCENT_COLOR, false));

    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) {
            mc.displayGuiScreen(parentScreen);

        } else if (button.id == 10) {
            Desktop.getDesktop().open(new File(ObsidianClient.ROOT_CONFIG_FOLDER, "LICENSE"));

        } else if (button.id == 20) {
            Desktop.getDesktop().open(new File(ObsidianClient.ROOT_CONFIG_FOLDER, "THIRD_PARTY"));
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            mc.displayGuiScreen(parentScreen);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        int cardWidth = 250;
        int cardHeight = 200;
        double cardX = this.width / 2.0D - cardWidth / 2.0D;
        double cardY = this.height / 2.0D - cardHeight / 2.0D;

        //Drawing the background:
        GraphicUtil.drawRect(0, 0, this.width, this.height, Integer.MIN_VALUE);
        GraphicUtil.drawHollowContainerBackground(cardX, cardY, cardWidth, cardHeight, 0, 0, 0, 0);

        //Drawing the Obsidian Client logo:
        this.mc.getTextureManager().bindTexture(new ResourceLocation("obsidianclient/clientLogo.png"));
        GraphicUtil.drawModalRectWithCustomSizedTexture(cardX + 25, cardY + 20, 0, 0, 192.5F, 42F, 192.5F, 42F);

        //Drawing license info:
        int offset = -5;
        GraphicUtil.drawCenteredString(mc.fontRendererObj, "Obsidian Client", this.width / 2.0F, this.height / 2.0F - 20 + offset, Color.WHITE.getRGB());
        GraphicUtil.drawCenteredString(mc.fontRendererObj, "Copyright (C) 2022  Alexander Richter", this.width / 2.0F, this.height / 2.0F - 9 + offset, Color.WHITE.getRGB());
        GraphicUtil.drawCenteredString(mc.fontRendererObj, "Obsidian Client comes", this.width / 2.0F, this.height / 2.0F + 9 + offset, Color.WHITE.getRGB());
        GraphicUtil.drawCenteredString(mc.fontRendererObj, "with ABSOLUTELY NO WARRANTY!", this.width / 2.0F, this.height / 2.0F + 20 + offset, Color.WHITE.getRGB());

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public void setParentScreen(GuiScreen parentScreen) {
        this.parentScreen = parentScreen;
    }

    public GuiScreen getParentScreen() {
        return parentScreen;
    }

}

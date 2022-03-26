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

import java.awt.Color;
import java.io.IOException;

import com.obsidianclient.ObsidianClient;
import com.obsidianclient.gui.elements.GuiButtonChat;
import com.obsidianclient.gui.elements.GuiButtonObsidianClient;
import com.obsidianclient.modules.Module;
import com.obsidianclient.modules.ModuleDraggable;
import com.obsidianclient.utils.GraphicUtil;
import com.obsidianclient.utils.Point;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Keyboard;

public class GuiHudSettings extends GuiScreen {

    //The GuiScreen that opened up this one:
    private GuiScreen parentScreen = null;

    //The mouse position of the last frame:
    private int oldMouseX;
    private int oldMouseY;

    //Buttons:
    private GuiButtonObsidianClient btnBackDialog;
    private GuiButtonObsidianClient btnSettingsDialog;

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (mouseButton == 0) {
            this.oldMouseX = mouseX;
            this.oldMouseY = mouseY;
        }
        for (Module m : ObsidianClient.getClient().getModuleManager().getModuleList()) {
            if (m instanceof ModuleDraggable) {
                ModuleDraggable module = (ModuleDraggable) m;

                if (module.isToggled()) {
                    if (isMouseOverModule(module, mouseX, mouseY)) {
                        if (mouseButton == 2) {
                            //If it's the middle mouse button, we disable the module under the mouse cursor:
                            module.toggle();
                        } else if (mouseButton == 1) {
                            //If it's the left mouse button, we open the config screen of the module under the mouse:
                            module.getOptionsScreen().setParentScreen(this);
                            this.mc.displayGuiScreen(module.getOptionsScreen());
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        if (clickedMouseButton == 0) {
            for (Module m : ObsidianClient.getClient().getModuleManager().getModuleList()) {
                if (m instanceof ModuleDraggable) {
                    ModuleDraggable module = (ModuleDraggable) m;
                    if (module.isToggled()) {
                        if (this.isMouseOverModule(module, mouseX, mouseY)) {
                            //Move the module:
                            module.setPosition(module.getX() + mouseX - oldMouseX, module.getY() + mouseY - oldMouseY);
                        }
                    }
                }
            }
            this.oldMouseX = mouseX;
            this.oldMouseY = mouseY;
        }
    }

    private boolean isMouseOverModule(ModuleDraggable module, int mouseX, int mouseY) {
        return mouseX >= module.getX() && mouseY >= module.getY() && mouseX < module.getX() + module.getWidth() && mouseY < module.getY() + module.getHeight();
    }

    @Override
    public void initGui() {
        int buttonSize = 16;
        //A button to go to the previous screen:
        this.buttonList.add(new GuiButtonChat(1, 7, 7, buttonSize, buttonSize, new ResourceLocation("obsidianclient/arrowBack.png"), "", true));
        //A button to go to the Obsidian Client Settings:
        if (!(this.parentScreen instanceof GuiObsidianClientSettings)) {
            this.buttonList.add(new GuiButtonChat(2, this.width - buttonSize - 7, 7, buttonSize, buttonSize, new ResourceLocation("obsidianclient/optionsWhite.png"), "", true));
        }
        //Buttons for the info dialog:
        int offset = 25;
        this.buttonList.add(btnSettingsDialog = new GuiButtonObsidianClient(2, this.width / 2 - 68 - 4, this.height / 2 + offset, 68, 20, "Settings"));
        this.buttonList.add(btnBackDialog = new GuiButtonObsidianClient(1, this.width / 2, this.height / 2 + offset, 68, 20, "Back"));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 1) {
            this.mc.displayGuiScreen(this.parentScreen);
            //Saving the changes:
            ObsidianClient.getClient().getModuleManager().saveWholeConfig();

        } else if (button.id == 2) {
            //Opening the Obsidian Client Settings:
            ObsidianClient.getClient().getObsidianClientSettings().setParentScreen(this);
            this.mc.displayGuiScreen(ObsidianClient.getClient().getObsidianClientSettings());

        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        //Closes the gui-screen, when the player press escape:
        if (keyCode == Keyboard.KEY_ESCAPE) {
            this.mc.displayGuiScreen(this.parentScreen);
            //Saving the changes:
            ObsidianClient.getClient().getModuleManager().saveWholeConfig();
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        //A boolean to check, that at least one module is active:
        boolean atLeastOneModuleActive = false;

        //Renders all the modules in there dummy version:
        for (Module m : ObsidianClient.getClient().getModuleManager().getModuleList()) {
            if (m instanceof ModuleDraggable) {
                ModuleDraggable module = (ModuleDraggable) m;
                if (module.isToggled()) {
                    module.shouldRenderWidget = false;
                    module.renderDummy();
                    GraphicUtil.drawBox(module.getXInt(), module.getYInt(), module.getWidth(), module.getHeight(), ObsidianClient.BLUE_ACCENT_COLOR, true); //Old value: 16, 148, 237
                    atLeastOneModuleActive = true;
                }
            }
        }

        //If there are no modules active, there will be a dialog:
        if (!atLeastOneModuleActive) {
            this.drawInfoMessage();
        } else {
            this.drawNormalOverlay();
            if (Keyboard.isKeyDown(Keyboard.KEY_F3)) {
                this.drawDebugOverlay();
            }
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    //Draws some instructions on how to use this screen:
    private void drawInstructions() {
        String text = "LMB: Move Module | RMB: Open Module Settings | MMB: Disable Module";
        int textLength = this.fontRendererObj.getStringWidth(text);
        int padding = 3;
        int height = 10;
        GraphicUtil.drawRect(this.width / 2.0D - textLength / 2.0D - padding, height - padding, this.width / 2.0D - textLength / 2.0D + textLength + padding, height + this.fontRendererObj.FONT_HEIGHT + padding, ObsidianClient.GRAY_OVERLAY_COLOR);
        GraphicUtil.drawCenteredStringWithShadow(this.fontRendererObj, text, this.width / 2.0F, height, Color.WHITE.getRGB());
    }

    //Draws an info message if there are no active modules (draggable):
    private void drawInfoMessage() {
        //Enabling buttons:
        this.btnSettingsDialog.enabled = !(this.parentScreen instanceof GuiObsidianClientSettings);
        this.btnSettingsDialog.visible = true;
        this.btnBackDialog.enabled = true;
        this.btnBackDialog.visible = true;
        //Background:
        GraphicUtil.drawRect(0, 0, this.width, this.height, Integer.MIN_VALUE);
        //Instructions:
        this.drawInstructions();
        //Info Dialog:
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        GraphicUtil.drawHollowContainerBackground(this.width / 2.0D - 90, this.height / 2.0D - 60, 180, 120, 13, 0, 0, 0);
        int lineSpacing = 3;
        int textOffset = -33;
        GraphicUtil.drawCenteredString(mc.fontRendererObj, "Information", this.width / 2.0F, this.height / 2.0F - 52, ObsidianClient.BLUE_ACCENT_COLOR);
        GraphicUtil.drawCenteredString(mc.fontRendererObj, "At the moment, there are no", this.width / 2.0F, this.height / 2.0F + textOffset, Color.WHITE.getRGB());
        GraphicUtil.drawCenteredString(mc.fontRendererObj, "modules active!", this.width / 2.0F, this.height / 2.0F + 9 + lineSpacing + textOffset, Color.WHITE.getRGB());
        GraphicUtil.drawCenteredString(mc.fontRendererObj, "Please go to the settings in", this.width / 2.0F, this.height / 2.0F + 18 + (lineSpacing * 2) + textOffset + 5, Color.WHITE.getRGB());
        GraphicUtil.drawCenteredString(mc.fontRendererObj, "order to activate some.", this.width / 2.0F, this.height / 2.0F + 27 + (lineSpacing * 3) + textOffset + 5, Color.WHITE.getRGB());
    }

    //Draws the normal overlay with the instructions on how to use this screen:
    private void drawNormalOverlay() {
        //Disabling unwanted buttons:
        this.btnSettingsDialog.enabled = false;
        this.btnSettingsDialog.visible = false;
        this.btnBackDialog.enabled = false;
        this.btnBackDialog.visible = false;
        //Instructions:
        this.drawInstructions();
    }

    //Draws the debug overlay (Press F3):
    private void drawDebugOverlay() {
        int radius = 3;
        for (Point anchor : ObsidianClient.getClient().getAnchorUtil().getAllAnchors().keySet()) {
            GraphicUtil.drawRect(anchor.getX() - radius, anchor.getY() - radius, anchor.getX() + radius, anchor.getY() + radius, Color.YELLOW.getRGB());
        }
        for (Module m : ObsidianClient.getClient().getModuleManager().getModuleList()) {
            if (m instanceof ModuleDraggable) {
                ModuleDraggable module = (ModuleDraggable) m;
                if (module.isToggled()) {
                    Point moduleCenter = module.getCenter();
                    GraphicUtil.drawRect(module.getX() - radius, module.getY() - radius, module.getX() + radius, module.getY() + radius, Color.CYAN.getRGB());
                    GraphicUtil.drawRect(moduleCenter.getX() - radius, moduleCenter.getY() - radius, moduleCenter.getX() + radius, moduleCenter.getY() + radius, Color.RED.getRGB());
                }
            }
        }
    }

    @Override
    public void onGuiClosed() {
        //Resets the "shouldRenderWidget" variable, so that the modules will be rendered after the screen closed:
        for (Module m : ObsidianClient.getClient().getModuleManager().getModuleList()) {
            if (m instanceof ModuleDraggable) {
                ModuleDraggable module = (ModuleDraggable) m;
                module.shouldRenderWidget = true;
            }
        }
    }

    //This is needed for the gui to function correctly:
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    public GuiScreen getParentScreen() {
        return parentScreen;
    }

    public void setParentScreen(GuiScreen parentScreen) {
        this.parentScreen = parentScreen;
    }
}

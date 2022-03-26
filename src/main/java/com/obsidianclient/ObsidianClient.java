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

package com.obsidianclient;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import com.obsidianclient.gui.GuiAccountLogin;
import com.obsidianclient.gui.GuiHudSettings;
import com.obsidianclient.gui.GuiObsidianClientInfo;
import com.obsidianclient.gui.GuiObsidianClientSettings;
import com.obsidianclient.launch.Environment;
import com.obsidianclient.modules.ModuleManager;
import com.obsidianclient.utils.AnchorUtil;
import com.obsidianclient.utils.FileUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ObsidianClient {

	//The Obsidian Client instance:
	private static final ObsidianClient INSTANCE = new ObsidianClient();
	public static ObsidianClient getClient() { return INSTANCE; }

	//The gray color displayed behind every module:
	public static final int GRAY_OVERLAY_COLOR = new Color(0, 0, 0, 65).getRGB();
	public static final int BLUE_ACCENT_COLOR = new Color(18, 112, 234).getRGB();

	//Config folders:
	public static final File ROOT_CONFIG_FOLDER = new File("obsidianclient");
	public static final File MODULE_CONFIG_FOLDER = new File(ROOT_CONFIG_FOLDER, "modules");

	//Version info:
	public static final String MOD_ID = "@MOD_ID@";
	public static final String MOD_NAME = "@MOD_NAME@";
	public static final String MOD_VERSION = "@MOD_VERSION@";
	public static final String MINECRAFT_VERSION = "@MINECRAFT_VERSION@";
	public static final String FORGE_VERSION = "@FORGE_VERSION@";

	public static final Logger logger = LogManager.getLogger();

	//Important instances:
	private GuiObsidianClientSettings obsidianClientSettings;
	private GuiHudSettings hudSettingsScreen;
	private GuiAccountLogin accountLoginScreen;
	private GuiObsidianClientInfo infoScreen;
	private AnchorUtil anchorUtil;
	private ModuleManager moduleManager;

	/**
	 * Initializes the Obsidian Client.
	 */
	public void init() {

		//The Obsidian Client Logo:
		if (Environment.isDevMode) {
			logger.info("   ___  _         _     _ _                ____ _ _            _   ");
			logger.info("  / _ \\| |__  ___(_) __| (_) __ _ _ __    / ___| (_) ___ _ __ | |_ ");
			logger.info(" | | | | '_ \\/ __| |/ _` | |/ _` | '_ \\  | |   | | |/ _ | '_ \\| __|");
			logger.info(" | |_| | |_) \\__ | | (_| | | (_| | | | | | |___| | |  __| | | | |_ ");
			logger.info("  \\___/|_.__/|___|_|\\__,_|_|\\__,_|_| |_|  \\____|_|_|\\___|_| |_|\\__|");
			logger.info("                                                                   ");
		}

		logger.info("[Obsidian Client - Main] Initializing Obsidian Client!");

		//Makes sure that all necessary directories exist:
		if (!ROOT_CONFIG_FOLDER.exists()) {
			if (!ROOT_CONFIG_FOLDER.mkdirs()) {
				LogManager.getLogger().error("[Obsidian Client - Main] Can't create folder: " + ROOT_CONFIG_FOLDER.getAbsolutePath() + "!");
			}
		}
		if (!MODULE_CONFIG_FOLDER.exists()) {
			if (!MODULE_CONFIG_FOLDER.mkdirs()) {
				LogManager.getLogger().error("[Obsidian Client - Main] Can't create folder: " + MODULE_CONFIG_FOLDER.getAbsolutePath() + "!");
			}
		}
		try {
			FileUtil.extractLicenseAnd3rdParty(ROOT_CONFIG_FOLDER);
		} catch (IOException e) {
			logger.error("[Obsidian Client - Main] Couldn't extract the license and the third-party notice!");
			e.printStackTrace();
		}

		//Initializing the gui's:
		logger.info("[Obsidian Client - Main] Initializing GuiScreens!");
		this.obsidianClientSettings = new GuiObsidianClientSettings();
		this.hudSettingsScreen = new GuiHudSettings();
		this.accountLoginScreen = new GuiAccountLogin();
		this.infoScreen = new GuiObsidianClientInfo();

		//Initializing Module Manager:
		this.anchorUtil = new AnchorUtil();
		this.moduleManager = new ModuleManager();
		this.moduleManager.loadWholeConfig();
		this.moduleManager.refreshPositions();

		logger.info("[Obsidian Client - Main] Successfully initialized Obsidian Client!");

	}

	public GuiObsidianClientSettings getObsidianClientSettings() {
		return obsidianClientSettings;
	}

	public GuiHudSettings getHudSettingsScreen() {
		return hudSettingsScreen;
	}

	public GuiAccountLogin getAccountLoginScreen() {
		return accountLoginScreen;
	}

	public GuiObsidianClientInfo getObsidianClientInfoScreen() {
		return infoScreen;
	}

	public AnchorUtil getAnchorUtil() {
		return anchorUtil;
	}

	public ModuleManager getModuleManager() {
		return this.moduleManager;
	}

}

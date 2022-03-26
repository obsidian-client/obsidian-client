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

package com.obsidianclient.launch;

import com.obsidianclient.ObsidianClient;
import com.obsidianclient.asm.TransformManager;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.apache.logging.log4j.LogManager;

import java.util.Locale;
import java.util.Map;

/**
 * Loads Obsidian Client as a Forge mod,
 * is executed by Minecraft Forge.
 */
@IFMLLoadingPlugin.Name(ObsidianClient.MOD_NAME)
@IFMLLoadingPlugin.MCVersion(ObsidianClient.MINECRAFT_VERSION)
@IFMLLoadingPlugin.TransformerExclusions({"com.obsidianclient.asm"})
public class ForgeLoader implements IFMLLoadingPlugin {

    @Override
    public String[] getASMTransformerClass() {
        return new String[]{TransformManager.class.getName()};
    }

    @Override
    public String getModContainerClass() {
        return ForgeModContainer.class.getName();
    }

    @Override
    public void injectData(Map<String, Object> map) {

        LogManager.getLogger().info("[Obsidian Client - Forge Loader] Loading " + ObsidianClient.MOD_NAME + " " + ObsidianClient.MOD_VERSION + "!");

        //Checking if DevMode is enabled:
        String devMode = System.getProperty("obsidianclient.devmode");
        Environment.isDevMode = devMode != null && devMode.equalsIgnoreCase("true");

        //Checking if the environment is obfuscated:
        Environment.isObfuscated = (Boolean) map.get("runtimeDeobfuscationEnabled");

        //Checking if we are running together with Minecraft Forge:
        Environment.isRunningForge = true;

        //Checking if we are running together with OptiFine:
        Environment.isRunningOptifine = Launch.classLoader.getTransformers().stream().anyMatch(p -> p.getClass().getName().toLowerCase(Locale.ENGLISH).contains("optifine"));

    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }
    
}

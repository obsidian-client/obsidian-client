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

import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.LaunchClassLoader;

import org.apache.logging.log4j.LogManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Loads Obsidian Client for the vanilla (standard) version of Minecraft,
 * is executed by the Minecraft Launcher (Launchwrapper).
 */
public class VanillaLoader implements ITweaker {

    private final List<String> args = new ArrayList<String>();

    @Override
    public void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {
        this.args.addAll(args);

        this.addArg("version", profile);
        this.addArg("gameDir", gameDir);
        this.addArg("assetsDir", assetsDir);
    }

    @Override
    public void injectIntoClassLoader(LaunchClassLoader classLoader) {

        LogManager.getLogger().info("[Obsidian Client - Vanilla Loader] Loading " + ObsidianClient.MOD_NAME + " " + ObsidianClient.MOD_VERSION + "!");

        //Checking if DevMode is enabled:
        String devMode = System.getProperty("obsidianclient.devmode");
        Environment.isDevMode = devMode != null && devMode.equalsIgnoreCase("true");

        //Checking if the environment is obfuscated:
        try {
            Class.forName("net.minecraft.client.Minecraft");
            Environment.isObfuscated = false;
        } catch (Exception e) {
            Environment.isObfuscated = true;
        }

        //Checking if we are running together with Minecraft Forge:
        Environment.isRunningForge = false;

        //Checking if we are running together with OptiFine:
        Environment.isRunningOptifine = classLoader.getTransformers().stream().anyMatch(p -> {
            return p.getClass().getName().toLowerCase().contains("optifine");
        });

        //Registering the Transform Manager:
        classLoader.addTransformerExclusion("com.obsidianclient.asm");
        classLoader.registerTransformer(TransformManager.class.getName());

    }

    @Override
    public String getLaunchTarget() {
        return "net.minecraft.client.main.Main";
    }

    @Override
    public String[] getLaunchArguments() {
        return Environment.isRunningOptifine ? new String[0] : this.args.toArray(new String[]{});
    }

    private void addArg(String label, Object value) {
        this.args.add("--" + label);
        this.args.add(value instanceof String ? (String) value : value instanceof File ? ((File) value).getAbsolutePath() : ".");
    }

}

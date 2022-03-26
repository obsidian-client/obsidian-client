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

package com.obsidianclient.asm;

import com.obsidianclient.launch.Environment;

/**
 * A class containing mappings for the obfuscated Minecraft classes, methods and fields.
 */
public class Mappings {

    //The "Minecraft" class:
    public static final String MINECRAFT = Environment.isObfuscated ? "ave" : "net/minecraft/client/Minecraft";
    public static final String MINECRAFT_START_GAME = Environment.isObfuscated ? "am" : "startGame";
    public static final String MINECRAFT_START_GAME_DESC = Environment.isObfuscated ? "()V" : "()V";
    public static final String MINECRAFT_CREATE_DISPLAY = Environment.isObfuscated ? "ap" : "createDisplay";
    public static final String MINECRAFT_CREATE_DISPLAY_DESC = Environment.isObfuscated ? "()V" : "()V";
    public static final String MINECRAFT_RUN_TICK = Environment.isObfuscated ? "s" : "runTick";
    public static final String MINECRAFT_RUN_TICK_DESC = Environment.isObfuscated ? "()V" : "()V";
    public static final String MINECRAFT_DISPLAY_GUI_SCREEN = Environment.isObfuscated ? "a" : "displayGuiScreen";
    public static final String MINECRAFT_DISPLAY_GUI_SCREEN_DESC = Environment.isObfuscated ? "(Laxu;)V" : "(Lnet/minecraft/client/gui/GuiScreen;)V";

    //The "GuiIngame" class:
    public static final String GUI_INGAME = Environment.isObfuscated ? "avo" : "net/minecraft/client/gui/GuiIngame";
    public static final String GUI_INGAME_RENDER_GAME_OVERLAY = Environment.isObfuscated ? "a" : "renderGameOverlay";
    public static final String GUI_INGAME_RENDER_GAME_OVERLAY_DESC = Environment.isObfuscated ? "(F)V" : "(F)V";
    public static final String GUI_INGAME_RENDER_SCOREBOARD = Environment.isObfuscated ? "a" : "renderScoreboard";
    public static final String GUI_INGAME_RENDER_SCOREBOARD_DESC = Environment.isObfuscated ? "(Lauk;Lavr;)V" : "(Lnet/minecraft/scoreboard/ScoreObjective;Lnet/minecraft/client/gui/ScaledResolution;)V";

    //The "EntityRenderer" class:
    public static final String ENTITY_RENDERER = Environment.isObfuscated ? "bfk" : "net/minecraft/client/renderer/EntityRenderer";
    public static final String ENTITY_RENDERER_UPDATE_CAMERA_AND_RENDER = Environment.isObfuscated ? "a" : "updateCameraAndRender";
    public static final String ENTITY_RENDERER_UPDATE_CAMERA_AND_RENDER_DESC = Environment.isObfuscated ? "(FJ)V" : "(FJ)V";

    //The "RenderGlobal" class:
    public static final String RENDER_GLOBAL = Environment.isObfuscated ? "bfr" : "net/minecraft/client/renderer/RenderGlobal";
    public static final String RENDER_GLOBAL_MAKE_ENTITY_OUTLINE_SHADER = Environment.isObfuscated ? "b" : "makeEntityOutlineShader";
    public static final String RENDER_GLOBAL_MAKE_ENTITY_OUTLINE_SHADER_DESC = Environment.isObfuscated ? "()V" : "()V";

    //The "Profiler" class:
    public static final String PROFILER = Environment.isObfuscated ? "nt" : "net/minecraft/profiler/Profiler";
    public static final String PROFILER_END_SECTION = Environment.isObfuscated ? "b" : "endSection";
    public static final String PROFILER_END_SECTION_DESC = Environment.isObfuscated ? "()V" : "()V";

    //The "GuiScreen" class:
    public static final String GUI_SCREEN = Environment.isObfuscated ? "axu" : "net/minecraft/client/gui/GuiScreen";

    //The "EntityArrow" class:
    public static final String ENTITY_ARROW = Environment.isObfuscated ? "wq" : "net/minecraft/entity/projectile/EntityArrow";
    public static final String ENTITY_ARROW_ON_UPDATE = Environment.isObfuscated ? "t_" : "onUpdate";
    public static final String ENTITY_ARROW_ON_UPDATE_DESC = Environment.isObfuscated ? "()V" : "()V";
    public static final String ENTITY_ARROW_GET_IS_CRITICAL = Environment.isObfuscated ? "l" : "getIsCritical";
    public static final String ENTITY_ARROW_GET_IS_CRITICAL_DESC = Environment.isObfuscated ? "()Z" : "()Z";

    //The "EnumParticleTypes" class:
    public static final String ENUM_PARTICLE_TYPES = Environment.isObfuscated ? "cy" : "net/minecraft/util/EnumParticleTypes";
    public static final String ENUM_PARTICLE_TYPES_DESC = Environment.isObfuscated ? "Lcy;" : "Lnet/minecraft/util/EnumParticleTypes;"; //Desc for all fields listed below
    public static final String ENUM_PARTICLE_TYPES_FLAME = Environment.isObfuscated ? "A" : "FLAME";
    public static final String ENUM_PARTICLE_TYPES_LAVA = Environment.isObfuscated ? "B" : "LAVA";
    public static final String ENUM_PARTICLE_TYPES_REDSTONE = Environment.isObfuscated ? "E" : "REDSTONE";
    public static final String ENUM_PARTICLE_TYPES_SNOWBALL = Environment.isObfuscated ? "F" : "SNOWBALL";
    public static final String ENUM_PARTICLE_TYPES_SLIME = Environment.isObfuscated ? "H" : "SLIME";
    public static final String ENUM_PARTICLE_TYPES_HEART = Environment.isObfuscated ? "I" : "HEART";
    public static final String ENUM_PARTICLE_TYPES_WATER_DROP = Environment.isObfuscated ? "N" : "WATER_DROP";
    public static final String ENUM_PARTICLE_TYPES_WATER_BUBBLE = Environment.isObfuscated ? "e" : "WATER_BUBBLE";
    public static final String ENUM_PARTICLE_TYPES_CRIT = Environment.isObfuscated ? "j" : "CRIT";
    public static final String ENUM_PARTICLE_TYPES_DRIP_WATER = Environment.isObfuscated ? "s" : "DRIP_WATER";
    public static final String ENUM_PARTICLE_TYPES_DRIP_LAVA = Environment.isObfuscated ? "t" : "DRIP_LAVA";

}

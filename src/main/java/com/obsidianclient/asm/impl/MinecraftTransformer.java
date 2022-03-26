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

package com.obsidianclient.asm.impl;

import com.obsidianclient.asm.Hooks;
import com.obsidianclient.ObsidianClient;
import com.obsidianclient.asm.ASMHelper;
import com.obsidianclient.asm.Mappings;
import com.obsidianclient.asm.ObsidianClientTransformer;
import com.obsidianclient.launch.Environment;
import org.apache.logging.log4j.LogManager;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

public class MinecraftTransformer implements ObsidianClientTransformer {

    @Override
    public String getClassToTransform() {
        return Mappings.MINECRAFT.replaceAll("/", ".");
    }

    @Override
    public void transform(ClassNode clazz) {

        this.transformCreateDisplay(clazz);

        this.transformStartGame(clazz);

        this.transformRunTick(clazz);

        this.transformDisplayGuiScreen(clazz);

    }

    private void transformCreateDisplay(ClassNode clazz) {

        //-------------------------------------------------
        //Transforming the "createDisplay" method:
        //-------------------------------------------------

        if (Environment.isDevMode) LogManager.getLogger().info("[Obsidian Client - Transform Manager] Transforming method: createDisplay");

        //Finding the target method:
        MethodNode targetMethod = ASMHelper.findMethod(clazz, Mappings.MINECRAFT_CREATE_DISPLAY, Mappings.MINECRAFT_CREATE_DISPLAY_DESC).get(0);

        //Finding the target node:
        LdcInsnNode targetNode = ASMHelper.findLdcInsnNode(targetMethod, "Minecraft 1.8.9").get(0);

        //Finally transform the instruction (setting a new title):
        targetNode.cst = "Minecraft 1.8.9 | " + ObsidianClient.MOD_NAME + " " + ObsidianClient.MOD_VERSION;

    }

    private void transformStartGame(ClassNode clazz) {

        //-------------------------------------------------
        //Transforming the "startGame" method:
        //-------------------------------------------------

        if (Environment.isDevMode) LogManager.getLogger().info("[Obsidian Client - Transform Manager] Transforming method: startGame");

        //Finding the target method:
        MethodNode targetMethod = ASMHelper.findMethod(clazz, Mappings.MINECRAFT_START_GAME, Mappings.MINECRAFT_START_GAME_DESC).get(0);

        //Finding the target node:
        MethodInsnNode targetNode = ASMHelper.findMethodInsnNode(targetMethod, Opcodes.INVOKEVIRTUAL, Mappings.RENDER_GLOBAL, Mappings.RENDER_GLOBAL_MAKE_ENTITY_OUTLINE_SHADER, Mappings.RENDER_GLOBAL_MAKE_ENTITY_OUTLINE_SHADER_DESC, false).get(0);

        //-------------------------------------------------
        //Objective:
        //Find: Method END (this.renderGlobal.makeEntityOutlineShader();)
        //Insert: Hooks.onStart();
        //-------------------------------------------------

        //Creating a list of the new instructions:
        InsnList instructions = new InsnList();
        instructions.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Type.getInternalName(Hooks.class), "onStart", "()V", false));

        //Putting the new instructions into the method:
        targetMethod.instructions.insert(targetNode, instructions);

    }

    private void transformRunTick(ClassNode clazz) {

        //-------------------------------------------------
        //Transforming the "runTick" method:
        //-------------------------------------------------

        if (Environment.isDevMode) LogManager.getLogger().info("[Obsidian Client - Transform Manager] Transforming method: runTick");

        //Finding the target method:
        MethodNode targetMethod = ASMHelper.findMethod(clazz, Mappings.MINECRAFT_RUN_TICK, Mappings.MINECRAFT_RUN_TICK_DESC).get(0);

        //Finding the target node:
        MethodInsnNode targetNode = ASMHelper.findMethodInsnNode(targetMethod, Opcodes.INVOKEVIRTUAL, Mappings.PROFILER, Mappings.PROFILER_END_SECTION, Mappings.PROFILER_END_SECTION_DESC, false).get(0);

        //-------------------------------------------------
        //Objective:
        //Find: this.mcProfiler.endSection();
        //Insert: Hooks.onTick();
        //-------------------------------------------------

        //Creating a list of the new instructions:
        InsnList instructions = new InsnList();
        instructions.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Type.getInternalName(Hooks.class), "onTick", "()V", false));

        //Putting the new instructions into the method:
        targetMethod.instructions.insertBefore(targetNode.getPrevious().getPrevious().getPrevious(), instructions);

    }

    private void transformDisplayGuiScreen(ClassNode clazz) {

        //-------------------------------------------------
        //Transforming the "displayGuiScreen" method:
        //-------------------------------------------------

        if (Environment.isDevMode) LogManager.getLogger().info("[Obsidian Client - Transform Manager] Transforming method: displayGuiScreen");

        //Finding the target method:
        MethodNode targetMethod = ASMHelper.findMethod(clazz, Mappings.MINECRAFT_DISPLAY_GUI_SCREEN, Mappings.MINECRAFT_DISPLAY_GUI_SCREEN_DESC).get(0);

        //-------------------------------------------------
        //Objective:
        //Find: Method HEAD
        //Insert: if (Hooks.onGuiOpen(screen)) {
        //            return;
        //        }
        //-------------------------------------------------

        //Creating a list of the new instructions:
        InsnList instructions = new InsnList();
        LabelNode labelIsCancelled = new LabelNode();
        instructions.add(new VarInsnNode(Opcodes.ALOAD, 1));
        instructions.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Type.getInternalName(Hooks.class), "onGuiOpen", "(L" + Mappings.GUI_SCREEN + ";)Z", false));
        instructions.add(new JumpInsnNode(Opcodes.IFEQ, labelIsCancelled));
        instructions.add(new InsnNode(Opcodes.RETURN));
        instructions.add(labelIsCancelled);

        //Putting the new instructions into the method:
        targetMethod.instructions.insertBefore(targetMethod.instructions.getFirst(), instructions);

    }

}

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
import com.obsidianclient.asm.ASMHelper;
import com.obsidianclient.asm.Mappings;
import com.obsidianclient.asm.ObsidianClientTransformer;
import com.obsidianclient.launch.Environment;
import org.apache.logging.log4j.LogManager;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

public class EntityRendererTransformer implements ObsidianClientTransformer {

    @Override
    public String getClassToTransform() {
        return Mappings.ENTITY_RENDERER.replaceAll("/", ".");
    }

    @Override
    public void transform(ClassNode clazz) {

        //-------------------------------------------------
        //Transforming the "updateCameraAndRender" method:
        //-------------------------------------------------

        if (Environment.isDevMode) LogManager.getLogger().info("[Obsidian Client - Transform Manager] Transforming method: updateCameraAndRender");

        //Finding the target method:
        MethodNode targetMethod = ASMHelper.findMethod(clazz, Mappings.ENTITY_RENDERER_UPDATE_CAMERA_AND_RENDER, Mappings.ENTITY_RENDERER_UPDATE_CAMERA_AND_RENDER_DESC).get(0);

        //Finding the target node:
        MethodInsnNode targetNode = ASMHelper.findMethodInsnNode(targetMethod, Opcodes.INVOKEVIRTUAL, Mappings.GUI_INGAME, Mappings.GUI_INGAME_RENDER_GAME_OVERLAY, Mappings.GUI_INGAME_RENDER_GAME_OVERLAY_DESC, false).get(0);

        //-------------------------------------------------
        //Objective:
        //Find: this.mc.ingameGUI.renderGameOverlay(p_updateCameraAndRender_1_);
        //Insert: Hooks.onRenderTextOverlay();
        //-------------------------------------------------

        //Creating a list of the new instructions:
        InsnList instructions = new InsnList();
        instructions.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Type.getInternalName(Hooks.class), "onRenderTextOverlay", "()V", false));

        //Putting the new instructions into the method:
        targetMethod.instructions.insert(targetNode, instructions);

    }

}

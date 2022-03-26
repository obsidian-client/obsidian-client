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

import com.obsidianclient.asm.ASMHelper;
import com.obsidianclient.asm.Hooks;
import com.obsidianclient.asm.Mappings;
import com.obsidianclient.asm.ObsidianClientTransformer;
import com.obsidianclient.launch.Environment;
import org.apache.logging.log4j.LogManager;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

public class GuiIngameTransformer implements ObsidianClientTransformer {

    @Override
    public String getClassToTransform() {
        return Mappings.GUI_INGAME.replaceAll("/", ".");
    }

    @Override
    public void transform(ClassNode clazz) {

        //-------------------------------------------------
        //Transforming the "renderScoreboard" method:
        //-------------------------------------------------

        if (Environment.isDevMode) LogManager.getLogger().info("[Obsidian Client - Transform Manager] Transforming method: renderScoreboard");

        //Finding the target method:
        MethodNode targetMethod = ASMHelper.findMethod(clazz, Mappings.GUI_INGAME_RENDER_SCOREBOARD, Mappings.GUI_INGAME_RENDER_SCOREBOARD_DESC).get(0);

        //Finding the target node:
        AbstractInsnNode targetNode = ASMHelper.findLdcInsnNode(targetMethod, "").get(0).getNext().getNext().getNext().getNext().getNext().getNext();

        //-------------------------------------------------
        //Objective:
        //Find: String s = EnumChatFormatting.RED + "" + score.getScorePoints();
        //Insert: s = Hooks.onSetScoreboardNumbers(s);
        //-------------------------------------------------

        //Creating a list of the new instructions:
        InsnList instructions = new InsnList();
        instructions.add(new VarInsnNode(Opcodes.ALOAD, 16));
        instructions.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Type.getInternalName(Hooks.class), "setScoreboardNumbers", "(Ljava/lang/String;)Ljava/lang/String;", false));
        instructions.add(new VarInsnNode(Opcodes.ASTORE, 16));

        //Putting the new instructions into the method:
        targetMethod.instructions.insert(targetNode, instructions);

    }

}

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

public class EntityArrowTransformer implements ObsidianClientTransformer {

    @Override
    public String getClassToTransform() {
        return Mappings.ENTITY_ARROW.replaceAll("/", ".");
    }

    @Override
    public void transform(ClassNode clazz) {

        //-------------------------------------------------
        //Transforming the "onUpdate" method:
        //-------------------------------------------------

        if (Environment.isDevMode) LogManager.getLogger().info("[Obsidian Client - Transform Manager] Transforming method: onUpdate");

        //Finding the target method:
        MethodNode targetMethod = ASMHelper.findMethod(clazz, Mappings.ENTITY_ARROW_ON_UPDATE, Mappings.ENTITY_ARROW_ON_UPDATE_DESC).get(0);

        this.transformOnUpdate1(targetMethod);

        this.transformOnUpdate2(targetMethod);

    }

    private void transformOnUpdate1(MethodNode targetMethod) {

        //Finding the target node:
        MethodInsnNode targetNode = ASMHelper.findMethodInsnNode(targetMethod, Opcodes.INVOKEVIRTUAL, Mappings.ENTITY_ARROW, Mappings.ENTITY_ARROW_GET_IS_CRITICAL, Mappings.ENTITY_ARROW_GET_IS_CRITICAL_DESC, false).get(1);

        //-------------------------------------------------
        //Objective:
        //Find: if (this.getIsCritical()) { ... }
        //Change To: if (Hooks.spawnArrowParticles(this.getIsCritical())) { ... }
        //-------------------------------------------------

        //Creating a list of the new instructions:
        InsnList instructions = new InsnList();
        instructions.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Type.getInternalName(Hooks.class), "spawnArrowParticles", "(Z)Z", false));

        //Putting the new instructions into the method:
        targetMethod.instructions.insert(targetNode, instructions);

    }

    private void transformOnUpdate2(MethodNode targetMethod) {

        //Finding the target node:
        FieldInsnNode targetNode = ASMHelper.findFieldInsnNode(targetMethod, Opcodes.GETSTATIC, Mappings.ENUM_PARTICLE_TYPES, Mappings.ENUM_PARTICLE_TYPES_CRIT, Mappings.ENUM_PARTICLE_TYPES_DESC).get(0);

        //-------------------------------------------------
        //Objective:
        //Find: this.worldObj.spawnParticle(EnumParticleTypes.CRIT, this.posX + this.motionX * (double) k / 4.0D, this.posY + this.motionY * (double) k / 4.0D, this.posZ + this.motionZ * (double) k / 4.0D, -this.motionX, -this.motionY + 0.2D, -this.motionZ, new int[0]);
        //Change To: this.worldObj.spawnParticle(Hooks.onSetArrowParticleType(), this.posX + this.motionX * (double) k / 4.0D, this.posY + this.motionY * (double) k / 4.0D, this.posZ + this.motionZ * (double) k / 4.0D, -this.motionX, -this.motionY + 0.2D, -this.motionZ, new int[0]);
        //-------------------------------------------------

        //Creating a list of the new instructions:
        InsnList instructions = new InsnList();
        instructions.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Type.getInternalName(Hooks.class), "setArrowParticleType", "()" + Mappings.ENUM_PARTICLE_TYPES_DESC, false));

        //Putting the new instructions into the method:
        targetMethod.instructions.insert(targetNode, instructions);

        //Removing the old instructions from the method:
        targetMethod.instructions.remove(targetNode);

    }

}

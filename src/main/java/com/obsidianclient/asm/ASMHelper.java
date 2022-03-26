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

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.ArrayList;
import java.util.List;

public class ASMHelper {

    /**
     * Finds a specific method out of a class.
     * @param clazz The class containing the target method.
     * @param methodName The name of the method.
     * @param methodDescription The method descriptor.
     * @return Returns a list of all methods found.
     */
    public static List<MethodNode> findMethod(ClassNode clazz, String methodName, String methodDescription) {

        List<MethodNode> foundMethods = new ArrayList<MethodNode>();

        //Looping through all methods:
        for (MethodNode method : clazz.methods) {

            //Looking for the right method:
            if (method.name.equals(methodName) && method.desc.equals(methodDescription)) {

                //Successfully found a method! YAY!
                foundMethods.add(method);

            }

        }

        return foundMethods;

    }

    /**
     * Finds a method instruction node out of a method.
     * @param method The method containing the instruction.
     * @param opcode The opcode of the instruction.
     * @param owner The owner of the instruction.
     * @param name The name of the instruction.
     * @param description The descriptor of the instruction.
     * @param isInterface Is the instruction target an interface?
     * @return Returns a list of all instructions found.
     */
    public static List<MethodInsnNode> findMethodInsnNode(MethodNode method, int opcode, String owner, String name, String description, boolean isInterface) {

        List<MethodInsnNode> foundNodes = new ArrayList<MethodInsnNode>();

        //Looping through all instructions:
        for (AbstractInsnNode instruction : method.instructions.toArray()) {

            //Looking for the right instruction (1/2):
            if (instruction.getOpcode() == opcode && instruction.getType() == AbstractInsnNode.METHOD_INSN) {

                //Now we know it must be a MethodInsnNode:
                MethodInsnNode methodInstruction = (MethodInsnNode) instruction;

                //Looking for the right instruction (2/2):
                if (methodInstruction.owner.equals(owner) && methodInstruction.name.equals(name) && methodInstruction.desc.equals(description) && methodInstruction.itf == isInterface) {

                    //Successfully found a node! YAY!
                    foundNodes.add(methodInstruction);

                }

            }

        }

        return foundNodes;

    }

    /**
     * Finds a field instruction node out of a method.
     * @param method The method containing the instruction.
     * @param opcode The opcode of the instruction.
     * @param owner The owner of the instruction.
     * @param name The name of the instruction.
     * @param description The descriptor of the instruction.
     * @return Returns a list of all instructions found.
     */
    public static List<FieldInsnNode> findFieldInsnNode(MethodNode method, int opcode, String owner, String name, String description) {

        List<FieldInsnNode> foundNodes = new ArrayList<FieldInsnNode>();

        //Looping through all instructions:
        for (AbstractInsnNode instruction : method.instructions.toArray()) {

            //Looking for the right instruction (1/2):
            if (instruction.getOpcode() == opcode && instruction.getType() == AbstractInsnNode.FIELD_INSN) {

                //Now we know it must be a FieldInsnNode:
                FieldInsnNode fieldInstruction = (FieldInsnNode) instruction;

                //Looking for the right instruction (2/2):
                if (fieldInstruction.owner.equals(owner) && fieldInstruction.name.equals(name) && fieldInstruction.desc.equals(description)) {

                    //Successfully found the target node! YAY!
                    foundNodes.add(fieldInstruction);

                }

            }

        }

        return foundNodes;

    }

    /**
     * Finds a ldc instruction node out of a method.
     * @param method The method containing the instruction.
     * @param text The value (text) of the ldc instruction.
     * @return Returns a list of all instructions found.
     */
    public static List<LdcInsnNode> findLdcInsnNode(MethodNode method, String text) {

        List<LdcInsnNode> foundNodes = new ArrayList<LdcInsnNode>();

        //Looping through all instructions:
        for (AbstractInsnNode instruction : method.instructions.toArray()) {

            //Looking for the right instruction (1/2):
            if (instruction.getOpcode() == Opcodes.LDC && instruction.getType() == AbstractInsnNode.LDC_INSN) {

                //Now we know it must be a LdcInsnNode:
                LdcInsnNode ldcInstruction = (LdcInsnNode) instruction;

                //Looking for the right instruction (2/2):
                if (ldcInstruction.cst.equals(text)) {

                    //Successfully found the target node! YAY!
                    foundNodes.add(ldcInstruction);

                }

            }

        }

        return foundNodes;

    }

}

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

import com.obsidianclient.asm.impl.EntityArrowTransformer;
import com.obsidianclient.asm.impl.EntityRendererTransformer;
import com.obsidianclient.asm.impl.GuiIngameTransformer;
import com.obsidianclient.asm.impl.MinecraftTransformer;
import net.minecraft.launchwrapper.IClassTransformer;
import org.apache.logging.log4j.LogManager;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.util.HashMap;

/**
 * The main transformer called by the Minecraft Launchwrapper.
 * This executes all the other transformers.
 */
public class TransformManager implements IClassTransformer {

    //List (HashMap) of all transformers:
    private final HashMap<String, ObsidianClientTransformer> registeredTransformers = new HashMap<String, ObsidianClientTransformer>();

    public TransformManager() {

        MinecraftTransformer minecraftTransformer = new MinecraftTransformer();
        this.registeredTransformers.put(minecraftTransformer.getClassToTransform(), minecraftTransformer);

        EntityRendererTransformer entityRendererTransformer = new EntityRendererTransformer();
        this.registeredTransformers.put(entityRendererTransformer.getClassToTransform(), entityRendererTransformer);

        GuiIngameTransformer guiIngameTransformer = new GuiIngameTransformer();
        this.registeredTransformers.put(guiIngameTransformer.getClassToTransform(), guiIngameTransformer);

        EntityArrowTransformer entityArrowTransformer = new EntityArrowTransformer();
        this.registeredTransformers.put(entityArrowTransformer.getClassToTransform(), entityArrowTransformer);

    }

    //Will run once for every class in the game:
    @Override
    public byte[] transform(String name, String transformedName, byte[] rawClassInput) {

        //If there is a transformer for the current class:
        if (this.registeredTransformers.containsKey(name)) {

            try {

                LogManager.getLogger().info("[Obsidian Client - Transform Manager] Transforming class: " + name);

                //Getting the transformer:
                ObsidianClientTransformer transformer = this.registeredTransformers.get(name);

                //Turning the bytecode into a (for us) editable format:
                ClassNode classNode = new ClassNode();
                ClassReader classReader = new ClassReader(rawClassInput);
                classReader.accept(classNode, ClassReader.EXPAND_FRAMES);

                //Calling the method to finally transform the class:
                transformer.transform(classNode);

                //Turning everything back to byte code:
                ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
                classNode.accept(classWriter);
                byte[] modifiedClass = classWriter.toByteArray();

                return modifiedClass;

            } catch (Exception e) {
                //Error message if something goes wrong:
                LogManager.getLogger().error("[Obsidian Client - Transform Manager] Failed to transform class: " + name);
                e.printStackTrace();
            }

        }

        return rawClassInput;

    }

}

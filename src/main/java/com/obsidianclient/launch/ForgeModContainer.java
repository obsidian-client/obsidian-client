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

import com.google.common.eventbus.EventBus;
import com.obsidianclient.ObsidianClient;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.ModMetadata;

import java.util.Arrays;

/**
 * Because Obsidian Client is a core mod, we need this
 * dummy container to get an entry in Forge's mod list.
 * Also, DO NOT delete this! It is used in the ForgeLoader class!
 * Even though your ide maybe says it's unused...
 */
public class ForgeModContainer extends DummyModContainer {

    public ForgeModContainer() {
        super(new ModMetadata());
        ModMetadata meta = this.getMetadata();
        meta.modId = ObsidianClient.MOD_ID;
        meta.name = ObsidianClient.MOD_NAME;
        meta.version = ObsidianClient.MOD_VERSION;
        meta.description = "Obsidian Client is a simple, but functional (and hopefully bug free) PvP Client for Minecraft.";
        meta.url = "https://www.obsidian-client.com/";
        meta.logoFile = "/assets/minecraft/obsidianclient/clientLogo.png";
        meta.credits = "sTom18";
        meta.authorList = Arrays.asList("The Obsidian Client Team");
    }

    @Override
    public boolean registerBus(EventBus bus, LoadController controller) {
        bus.register(this);
        return true;
    }

}

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

package com.obsidianclient.utils;

import java.lang.reflect.Field;
import java.util.UUID;

import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.util.UUIDTypeAdapter;

import com.mojang.authlib.Agent;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

/**
 * Simple utility for changing the current session.
 */
public class LoginUtil {

	private static final String clientToken = UUIDTypeAdapter.fromUUID(UUID.randomUUID());
	private static final YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Minecraft.getMinecraft().getProxy(), clientToken);
	private static final YggdrasilUserAuthentication authentication = new YggdrasilUserAuthentication(service, Agent.MINECRAFT);

	/**
	 * Logs into a Mojang account and changes the current session.
	 */
	public static void loginMojang(String username, String password) throws AuthenticationException {

		authentication.setUsername(username);
		authentication.setPassword(password);
		authentication.logIn();

		String name = authentication.getSelectedProfile().getName();
		String uuid = UUIDTypeAdapter.fromUUID(authentication.getSelectedProfile().getId());
		String token = authentication.getAuthenticatedToken();
		String type = authentication.getUserType().getName();

		setSession(name, uuid, token, type);

		authentication.logOut();

	}

	/**
	 * Changes the current session into a dummy "legacy" account.
	 */
	public static void loginLegacy(String username) {
		setSession(username, UUIDTypeAdapter.fromUUID(UUID.randomUUID()), "", "legacy");
	}

	/**
	 * Sets a custom Minecraft Session.
	 * @param username The username of the player.
	 * @param uuid The uuid of the player.
	 * @param token The session token of the player.
	 * @param sessionType The session type.
	 */
	private static void setSession(String username, String uuid, String token, String sessionType) {
		try {
			Field sessionField = Minecraft.class.getDeclaredField("session");
			sessionField.setAccessible(true);
			sessionField.set(Minecraft.getMinecraft(), new Session(username, uuid, token, sessionType));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2022 RefracDevelopment
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE
 * FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package me.refracdevelopment.simplestaffchat.spigot.listeners;

import me.refracdevelopment.simplestaffchat.shared.Permissions;
import me.refracdevelopment.simplestaffchat.spigot.SimpleStaffChat;
import me.refracdevelopment.simplestaffchat.spigot.utilities.Manager;
import me.refracdevelopment.simplestaffchat.spigot.utilities.files.Config;
import me.refracdevelopment.simplestaffchat.shared.Settings;
import me.refracdevelopment.simplestaffchat.spigot.utilities.chat.Color;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinListener extends Manager implements Listener {

    public JoinListener(SimpleStaffChat plugin) {
        super(plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (Config.JOIN_ENABLED) {
            if (!player.hasPermission(Permissions.STAFFCHAT_JOIN)) return;

            for (Player p : plugin.getServer().getOnlinePlayers()) {
                if (p.hasPermission(Permissions.STAFFCHAT_SEE)) {
                    p.sendMessage(Color.translate(player, Config.JOIN_FORMAT));
                }
            }
            plugin.getServer().getConsoleSender().sendMessage(Color.translate(player, Config.JOIN_FORMAT));
        }

        if (player.getUniqueId().toString().equalsIgnoreCase(Settings.getDevUUID)) {
            sendDevMessage(player);
        } else if (player.getUniqueId().toString().equalsIgnoreCase(Settings.getDevUUID2)) {
            sendDevMessage(player);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (!Config.JOIN_ENABLED) return;
        if (!player.hasPermission(Permissions.STAFFCHAT_QUIT)) return;

        for (Player p : plugin.getServer().getOnlinePlayers()) {
            if (!p.hasPermission(Permissions.STAFFCHAT_SEE)) {
                p.sendMessage(Color.translate(player, Config.QUIT_FORMAT));
            }
        }
        plugin.getServer().getConsoleSender().sendMessage(Color.translate(player, Config.QUIT_FORMAT));
    }

    private void sendDevMessage(Player player) {
        Color.sendMessage(player, " ", false, false);
        Color.sendMessage(player, "&aWelcome " + Settings.getName + " Developer!", true, false);
        Color.sendMessage(player, "&aThis server is currently running " + Settings.getName + " &bv" + Settings.getVersion + "&a.", true, false);
        Color.sendMessage(player, "&aPlugin name&7: &f" + Settings.getName, true, false);
        Color.sendMessage(player, " ", false, false);
        Color.sendMessage(player, "&aServer version&7: &f" + plugin.getServer().getVersion(), true, false);
        Color.sendMessage(player, " ", false, false);
    }
}
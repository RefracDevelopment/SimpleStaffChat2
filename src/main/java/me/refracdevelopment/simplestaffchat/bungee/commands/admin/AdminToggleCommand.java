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
package me.refracdevelopment.simplestaffchat.bungee.commands.admin;

import me.refracdevelopment.simplestaffchat.bungee.utilities.chat.Color;
import me.refracdevelopment.simplestaffchat.bungee.utilities.files.Config;
import me.refracdevelopment.simplestaffchat.shared.Permissions;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AdminToggleCommand extends Command {
    public static List<UUID> inac = new ArrayList<>();

    public AdminToggleCommand() {
        super(Config.ADMINCHAT_TOGGLE_COMMAND, "", Config.ADMINCHAT_TOGGLE_ALIAS);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!Config.ADMINCHAT_TOGGLE_ENABLED) return;
        if (!(sender instanceof ProxiedPlayer)) return;

        ProxiedPlayer player = (ProxiedPlayer) sender;

        if (!player.hasPermission(Permissions.ADMINCHAT_TOGGLE)) {
            Color.sendMessage(player, Config.NO_PERMISSION, true, true);
            return;
        }

        if (inac.contains(player.getUniqueId())) {
            inac.remove(player.getUniqueId());
            Color.sendMessage(player, Config.ADMINCHAT_TOGGLE_OFF, true, true);
        } else {
            inac.add(player.getUniqueId());
            Color.sendMessage(player, Config.ADMINCHAT_TOGGLE_ON, true, true);
        }
    }
}
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
package me.refracdevelopment.simplestaffchat.bungee.commands;

import com.google.common.base.Joiner;
import me.refracdevelopment.simplestaffchat.bungee.BungeeStaffChat;
import me.refracdevelopment.simplestaffchat.bungee.utilities.files.Config;
import me.refracdevelopment.simplestaffchat.bungee.utilities.chat.Color;
import me.refracdevelopment.simplestaffchat.shared.Permissions;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class StaffChatCommand extends Command {

    private final BungeeStaffChat plugin;

    public StaffChatCommand(BungeeStaffChat plugin) {
        super(Config.STAFFCHAT_COMMAND, "", Config.STAFFCHAT_ALIAS);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!Config.STAFFCHAT_ENABLED) return;

        if (!sender.hasPermission(Permissions.STAFFCHAT_USE)) {
            Color.sendMessage(sender, Config.NO_PERMISSION, true, true);
            return;
        }

        if (args.length >= 1) {
            String format;
            String message = Joiner.on(" ").join(args);

            format = (sender instanceof ProxiedPlayer) ? Config.STAFFCHAT_FORMAT.replace("%server%", ((ProxiedPlayer) sender).getServer().getInfo().getName())
                    .replace("%message%", message) : Config.CONSOLE_FORMAT.replace("%message%", message);

            for (ProxiedPlayer p : plugin.getProxy().getPlayers()) {
                if (p.hasPermission(Permissions.STAFFCHAT_SEE)) {
                    p.sendMessage(Color.translate(sender, format));
                }
            }
            plugin.getProxy().getConsole().sendMessage(Color.translate(sender, format));
        } else {
            if (Config.STAFFCHAT_OUTPUT.equalsIgnoreCase("custom") && Config.STAFFCHAT_MESSAGE != null) {
                for (String s : Config.STAFFCHAT_MESSAGE)
                    Color.sendMessage(sender, s, true, true);
            } else if (Config.STAFFCHAT_OUTPUT.equalsIgnoreCase("toggle")) {
                if (sender instanceof ProxiedPlayer) {
                    ProxiedPlayer player = (ProxiedPlayer) sender;

                    if (!player.hasPermission(Permissions.STAFFCHAT_TOGGLE)) {
                        Color.sendMessage(player, Config.NO_PERMISSION, true, true);
                        return;
                    }

                    if (ToggleCommand.insc.contains(player.getUniqueId())) {
                        ToggleCommand.insc.remove(player.getUniqueId());
                        Color.sendMessage(player, Config.TOGGLE_OFF, true, true);
                    } else {
                        ToggleCommand.insc.add(player.getUniqueId());
                        Color.sendMessage(player, Config.TOGGLE_ON, true, true);
                    }
                }
            } else {
                Color.sendMessage(sender, "", false, false);
                Color.sendMessage(sender, "&c&lSimpleStaffChat2 %arrow2% Help", true, true);
                Color.sendMessage(sender, "", false, false);
                Color.sendMessage(sender, "&c/staffchat <message> - Send staffchat messages.", true, true);
                Color.sendMessage(sender, "&c/staffchattoggle - Send staffchat messages without needing to type a command.", true, true);
                Color.sendMessage(sender, "&c/staffchatreload - Reload the config file.", true, true);
                Color.sendMessage(sender, "", false, false);
            }
        }
    }
}
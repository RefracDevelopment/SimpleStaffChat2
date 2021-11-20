/*
 * Copyright (c) Refrac
 * If you have any questions please email refracplaysmc@gmail.com or reach me on Discord
 */
package me.refrac.simplestaffchat.bungee.utilities.files;

public class Config {
    // General
    public static boolean UPDATE_ENABLED;
    public static String STAFFCHAT_SYMBOL;
    public static String STAFFCHAT_FORMAT;

    // Staff Joins
    public static boolean JOIN_ENABLED;
    public static String JOIN_FORMAT;
    public static String SWITCH_FORMAT;
    public static String QUIT_FORMAT;

    // Messages
    public static String PREFIX;
    public static String NO_PERMISSION;
    public static String RELOAD;
    public static String TOGGLE_ON;
    public static String TOGGLE_OFF;

    public static void loadConfig() {
        // General
        UPDATE_ENABLED = Files.getConfig().getBoolean("update.enabled");
        STAFFCHAT_SYMBOL = Files.getConfig().getString("staffchat-symbol");
        STAFFCHAT_FORMAT = Files.getConfig().getString("format.minecraft-format");

        // Staff Joins
        JOIN_ENABLED = Files.getConfig().getBoolean("join.enabled");
        JOIN_FORMAT = Files.getConfig().getString("join.join-format");
        SWITCH_FORMAT = Files.getConfig().getString("join.switch-format");
        QUIT_FORMAT = Files.getConfig().getString("join.quit-format");

        // Messages
        PREFIX = Files.getConfig().getString("messages.prefix");
        NO_PERMISSION = Files.getConfig().getString("messages.no-permission");
        RELOAD = Files.getConfig().getString("messages.reload");
        TOGGLE_ON = Files.getConfig().getString("messages.toggle-on");
        TOGGLE_OFF = Files.getConfig().getString("messages.toggle-off");
    }
}
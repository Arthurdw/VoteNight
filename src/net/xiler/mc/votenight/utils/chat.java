package net.xiler.mc.votenight.utils;

import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

public class chat {

    public static String[] format(Plugin pl, String msg) {
        String[] messages = pl.getConfig().getStringList(msg).toArray(new String[0]);
        String[] data = new String[messages.length];
        for (int i = 0; i < messages.length; i++) {
            data[i] = ChatColor.translateAlternateColorCodes('&', pl.getConfig().getString("prefix") + messages[i]);
        }
        return data;
    }

    public static String parse(Plugin pl, String msg) {
        return ChatColor.translateAlternateColorCodes('&', pl.getConfig().getString("prefix") + msg);
    }
}

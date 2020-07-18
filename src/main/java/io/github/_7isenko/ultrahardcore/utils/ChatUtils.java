package io.github._7isenko.ultrahardcore.utils;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ChatUtils {
    public static void sendTitle(Player player, String message, ChatColor color) {
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "title "+ player.getName() +  " title [{\"text\":\"" + message + "\",\"color\":\"" + color.getName() + "\"}]");
    }
}

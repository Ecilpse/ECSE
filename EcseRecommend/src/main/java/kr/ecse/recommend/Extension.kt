package kr.ecse.recommend

import org.bukkit.ChatColor

fun String.color() : String {
    return ChatColor.translateAlternateColorCodes('&', this)
}
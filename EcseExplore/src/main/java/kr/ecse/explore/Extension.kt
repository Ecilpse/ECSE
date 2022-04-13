package kr.ecse.explore

import org.bukkit.ChatColor

fun String.color() : String {
    return ChatColor.translateAlternateColorCodes('&', this)
}
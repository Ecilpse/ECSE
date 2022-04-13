package kr.ecse.vote

import org.bukkit.ChatColor

fun String.color() : String {
    return ChatColor.translateAlternateColorCodes('&', this)
}
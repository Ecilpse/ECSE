package kr.ecse.coin

import org.bukkit.ChatColor
import org.bukkit.entity.Player

fun String.color() : String {
    return ChatColor.translateAlternateColorCodes('&', this)
}
val Player.coin: Coin
    get() = CoinManager.getData(uniqueId)
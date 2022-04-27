package kr.ecse.coin

import me.clip.placeholderapi.expansion.PlaceholderExpansion
import org.bukkit.entity.Player


class PlaceHolder:PlaceholderExpansion() {
    override fun getIdentifier(): String {
        return "ecsecoin"
    }

    override fun getAuthor(): String {
        return "EclipseMC"
    }

    override fun getVersion(): String {
        return "1.0.0"
    }

    override fun canRegister(): Boolean {
        return true
    }

    override fun onPlaceholderRequest(p: Player?, params: String): String? {
        if (p == null) return null
        return when (params) {
            "ecsecoin.$p" -> p.coin.amount.toString()
            else -> null
        }
    }
}
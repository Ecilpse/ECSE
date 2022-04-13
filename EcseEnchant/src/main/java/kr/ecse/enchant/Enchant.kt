package kr.ecse.enchant

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

object Enchant {
    fun color(string: String) : String {
        return ChatColor.translateAlternateColorCodes('&', string)
    }
    val startButton by lazy {
        val item = ItemStack(Material.NETHER_STAR)
        val meta = item.itemMeta
        meta.apply {
            displayName(Component.text(color("&c강화하기")))
            lore(listOf(
                Component.text(color("&7강화하시려면")),
                Component.text(color("&7클릭하세요."))
            ))
        }
        item.itemMeta = meta
        item
    }
    val wallpaper by lazy {
        val item = ItemStack(Material.GRAY_STAINED_GLASS_PANE)
        val meta = item.itemMeta
        meta.apply {
            displayName(Component.text(color("&7")))
        }
        item.itemMeta = meta
        item
    }
    fun openGui(player: Player) {
        val inv = Bukkit.createInventory(null, 27, Component.text(color("&c강화하기")))
        inv.apply {
            for(slot in 0..26) setItem(slot, wallpaper)
            setItem(12, ItemStack(Material.AIR))
            setItem(14, ItemStack(Material.AIR))
            setItem(22, startButton)
        }
        player.openInventory(inv)
    }
}
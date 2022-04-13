package kr.ecse.recommend

import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object Item {
    val yesIcon by lazy {
        val item = ItemStack(Material.PAPER)
        val meta = item.itemMeta
        meta.apply {
            displayName(Component.text("&a네!".color()))
            setCustomModelData(33)
        }
        item.itemMeta = meta
        item
    }
    val noIcon by lazy {
        val item = ItemStack(Material.PAPER)
        val meta = item.itemMeta
        meta.apply {
            displayName(Component.text("&6아니요".color()))
            setCustomModelData(33)
        }
        item.itemMeta = meta
        item
    }
}
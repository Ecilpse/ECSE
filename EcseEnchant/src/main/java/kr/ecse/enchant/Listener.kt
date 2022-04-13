package kr.ecse.enchant

import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent

object Listener: Listener {
    @EventHandler
    fun onConfirm(event: InventoryCloseEvent) {
        if(event.view.title() == Component.text(Enchant.color("&c강화하기"))) {
            val clickInv = event.inventory.contents!!
            val player = event.player
            if(clickInv[12] != null) player.inventory.addItem(clickInv[12]!!)
            if(clickInv[14] != null) player.inventory.addItem(clickInv[14]!!)
        }
    }

    @EventHandler
    fun cancel(event: InventoryClickEvent) {
        if(event.view.title() == Component.text(Enchant.color("&c강화하기"))) {
            val player = event.whoClicked as Player
            val slot = event.rawSlot
            if (slot <= 26 && slot != 12 && slot != 14) {
                event.isCancelled = true
                if (slot == 22) {
                    val clickInv = event.clickedInventory?.contents!!
                    if (clickInv[12] == null) {
                        event.isCancelled = true
                        player.playSound(player.location, Sound.BLOCK_ANVIL_LAND, 1F, 1F)
                        player.sendMessage("도구를 올려")
                    } else {
                        val type = clickInv[12]!!.type.toString()
                        if (!type.contains("_AXE") && !type.contains("_PICKAXE") && !type.contains("_HOE") && !type.contains(
                                "_SWORD"
                            ) && !type.contains("_SHOVEL")
                        ) {
                            player.playSound(player.location, Sound.BLOCK_ANVIL_LAND, 1F, 1F)
                            player.sendMessage("도구를 올려2")
                        } else if (clickInv[14] == null) {
                            player.playSound(player.location, Sound.BLOCK_ANVIL_LAND, 1F, 1F)
                            player.sendMessage("강화석를 올려")
                        } else if (clickInv[14]!!.type != Material.POTATO) {
                            player.playSound(player.location, Sound.BLOCK_ANVIL_LAND, 1F, 1F)
                            player.sendMessage("강화석를 올려2")
                        } else {
                            event.clickedInventory!!.getItem(14)!!.amount = (clickInv[14]!!.amount - 1)
                            val enchant = clickInv[12]!!.getEnchantLevel(Enchantment.DURABILITY)
                            event.clickedInventory!!.getItem(12)!!.addEnchant(Enchantment.DURABILITY, enchant + 1, true)
                        }
                    }
                }
            }
        }
    }
}
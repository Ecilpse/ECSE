package kr.ecse.hat

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

object Equip {
    fun equip(player: Player) {
        if (player.equipment.itemInMainHand.type == Material.AIR) {
            player.sendMessage("아이템을 손에 들어주세요")
        }
        else {
            if (player.equipment.helmet?.type == Material.AIR) {
                val item = player.equipment.itemInMainHand
                val air = ItemStack(Material.AIR)
                player.equipment.setItemInMainHand(air)
                player.equipment.helmet = item
            }
            else {
                val item1 = player.equipment.itemInMainHand
                val item2 = player.equipment.helmet
                player.equipment.setItemInMainHand(item2)
                player.equipment.helmet = item1
            }
            player.sendMessage("손에 든 아이템을 착용하였습니다.")
        }
    }
}
package kr.ecse.plant

import org.bukkit.Bukkit
import org.bukkit.CropState
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.data.Ageable
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.*

object Plant {
    private val map = HashMap<UUID, Boolean>()
    private val map2 = hashMapOf(
        Material.WHEAT to Material.WHEAT_SEEDS,
        Material.BEETROOTS to Material.BEETROOT_SEEDS,
        Material.CARROTS to Material.CARROT,
        Material.POTATOES to Material.POTATO,
    )
    fun toggle(player: Player) {
        val uuid = player.uniqueId
        if (map.contains(uuid)) {
            map.remove(uuid)
            player.sendMessage("비활성화")
        }
        else {
            map[uuid] = true
            player.sendMessage("활성화")
        }
    }
    fun plant(player: Player, block: Block) {
        val type = block.type
        if (type == Material.WHEAT || type == Material.BEETROOTS || type == Material.CARROTS || type == Material.POTATOES) {
            if (map.contains(player.uniqueId)) {
                if (player.inventory.containsAtLeast(ItemStack(map2[type]!!),1)) {
                    val data = block.blockData as Ageable
                    data.age = 0
                    main.server.scheduler.runTaskLater(main, Runnable {
                        block.blockData = data
                        player.inventory.removeItem((ItemStack(map2[type]!!, 1)))
                    }, 1)
                }
            }
        }
    }
}
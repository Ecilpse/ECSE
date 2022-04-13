package kr.ecse.jump

import org.bukkit.Sound
import org.bukkit.entity.Player
import java.util.*
import kotlin.collections.HashMap

object Jump {
    private val map = HashMap<UUID, Boolean>()
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
    fun jump(player: Player) {
        if (player.isSneaking) {
            if (map.contains(player.uniqueId)) {
                player.velocity = player.location.direction.multiply(1.3)
                player.playSound(player.location, Sound.ENTITY_WITCH_THROW, 1F, 1F)
            }
        }
    }
}
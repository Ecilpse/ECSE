package kr.ecse.sit

import org.bukkit.Material
import org.bukkit.entity.*

object Sit {
    fun sit(player: Player) {
        val block = player.getTargetBlock(5)
        if (block == null || block.type.isAir || block.type == Material.GRASS) {
            player.sendMessage("응안돼")
        }
        else {
            val location = player.getTargetBlock(5)!!.location.add(.5,.1,.5)
            val pig = location.world.spawn(location, Pig::class.java) {
                it.isInvisible = true
                it.setAI(false)
                it.isInvulnerable = true
                it.addPassenger(player)
            }
        }
    }
    fun unSit(player: Player, chair: Entity) {
        if (chair.type == EntityType.PIG) {
            chair.remove()
            player.teleport(player.location.add(.0,1.0,.0))
        }
    }
}
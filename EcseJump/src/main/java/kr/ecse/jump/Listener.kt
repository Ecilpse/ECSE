package kr.ecse.jump

import com.destroystokyo.paper.event.player.PlayerJumpEvent
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

object Listener: Listener {
    @EventHandler
    fun onJump(event: PlayerJumpEvent) {
        Jump.jump(event.player)
    }
}
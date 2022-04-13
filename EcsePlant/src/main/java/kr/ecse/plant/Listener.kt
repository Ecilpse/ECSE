package kr.ecse.plant

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

object Listener: Listener {
    @EventHandler
    fun onBreak(event: BlockBreakEvent) {
        Plant.plant(event.player, event.block)
    }
}
package kr.ecse.sit

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.vehicle.VehicleExitEvent

object Listener: Listener {
    @EventHandler
    fun onExit(event: VehicleExitEvent) {
        Sit.unSit(event.exited as Player, event.vehicle)
    }
}
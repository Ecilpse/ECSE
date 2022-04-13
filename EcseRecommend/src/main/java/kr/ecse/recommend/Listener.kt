package kr.ecse.recommend

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.event.HoverEvent
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.event.player.PlayerResourcePackStatusEvent
import java.util.*
import kotlin.collections.HashSet

object Listener: Listener {
    //   @EventHandler
//   fun onJoin(event: PlayerJoinEvent) {
//       val inv = Bukkit.createInventory(null, 27, Component.text("처음왔나요?"))
//       event.player.openInventory(inv)
//   }
    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        Manager.removeRetry(event.player.uniqueId)
    }

    @EventHandler
    fun onDowloaded(event: PlayerResourcePackStatusEvent) {
        if (event.player.hasPlayedBefore()) {
            return
        }
        Manager.addRetry(event.player.uniqueId)
        println("&e${event.player.name}&f님은 최초접속 플레이어입니다.".color())
        event.player.teleport(Location(Bukkit.getWorld("world"), .5, 60.0, .5, -90F, -7F))
        val inv = Bukkit.createInventory(null, 27, Component.text("&f\uF809\uE609\uF80C\uF809\uF803\uE60A\uF801\uE60B\uF801\uE60C".color()))
        inv.apply {
            for (slot in 10..12) setItem(slot, Item.yesIcon)
            for (slot in 14..16) setItem(slot, Item.noIcon)
        }
        event.player.openInventory(inv)
        Manager.retryMessage(event.player)
    }
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.view.title.contains("&f\uF809\uE609\uF80C\uF809\uF803\uE60A\uF801\uE60B\uF801\uE60C".color())) {
            event.isCancelled = true
            when (val slot = event.rawSlot) { //rawslot 클릭한 칸
                10, 11, 12 -> {
                    Manager.openSign(event.whoClicked as Player)
                }
                14, 15, 16 -> {
                    event.whoClicked.closeInventory()
                }
            }
        }
    }

    //@EventHandler
    //fun onInventoryClose(event: InventoryCloseEvent) {
    //    if (event.view.title.contains("&f\uF809\uE609\uF80C\uF809\uF803\uE60A\uF801\uE60B\uF801\uE60C".color())) {
    //        val message = {
    //            Component.text("&f \uE5D0 &6혹시 실수로 창을 닫으셨나요?\n&7    다시 시도하려면 채팅창을 열어 메세지를 클릭해주세요.".color())
    //                    .hoverEvent(HoverEvent.showText(Component.text("&b클릭하시면, 다시 시도하실 수 있습니다.".color())))
    //                    .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/recommend ${event.player.name}"))
    //        }
    //        event.player.sendMessage(message)
    //    }
    //}
}
package kr.ecse.vote

import com.vexsoftware.votifier.model.VotifierEvent
import kr.ecse.vote.coroutine.SynchronizationContext
import kr.ecse.vote.coroutine.schedule
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class Listener: Listener {
    @EventHandler
    fun onVote(event: VotifierEvent) {
        main.server.scheduler.schedule(main, SynchronizationContext.ASYNC) {
            Data.currentVote += 1
            Bukkit.getPlayerUniqueId(event.vote.username)?.let { Manager.addVote(it) }
            if (Data.currentVote >= Data.totalVote) {
                Data.currentVote = 0
                switchContext(SynchronizationContext.SYNC)
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "추천파티 실행")
            } else {
                switchContext(SynchronizationContext.SYNC)
                main.server.broadcastMessage("&f \uE3C5 &d${event.vote.username}&f님이 &b/추천&f하여 보상을 받았습니다. &d추천파티&f까지 &d${Data.totalVote - Data.currentVote}&f번 남았습니다.".color())
            }
        }
    }

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        if (Manager.checkVote(event.player.uniqueId) || event.player.hasPermission("vote.subaccount")) {
            return
        } else {
            Bukkit.getPlayer(event.player.name)?.sendTitle("&b추천.. 하셨나요?".color(), "&e/추천&7을 하여 보상을 받아보세요!".color(), 5, 40, 5)
        }
    }
}
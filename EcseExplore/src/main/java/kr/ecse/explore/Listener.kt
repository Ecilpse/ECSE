package kr.ecse.explore

import com.vexsoftware.votifier.model.VotifierEvent
import kr.ecse.explore.coroutine.SynchronizationContext
import kr.ecse.explore.coroutine.schedule
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent

class Listener: Listener {
    @EventHandler
    fun onVote(event: VotifierEvent) {
        main.server.scheduler.schedule(main, SynchronizationContext.ASYNC) { Bukkit.getPlayerUniqueId(event.vote.username)?.let { Manager.addVote(it) }}
    }
    @EventHandler
    fun onMove(event: PlayerMoveEvent) {
            if(Manager.checkExplore(event.player)) {
                Manager.removeExplore(event.player)
                Bukkit.getPlayer(event.player.name)?.sendTitle("&b잠수탐사 &c종료".color(), "&7움직임이 감지되어 탐사가 종료됩니다.".color(), 5, 40, 5)
                event.player.sendMessage("&f \uE5EA &f앗, 여기가 어딘가요?\n&7    추천을 하신 뒤 &b30분&7간 움직임이 발생하지 않을 시, 이동하는 공간입니다.\n&7    잠수하시는 동안, 당신의 플레이어는 이곳에서 &9우주탐사&7를 진행합니다.\n&7    맵 중앙에 있는 온실 건물로 입장하시면,\n&7    탐사를 통해 얻은 &9탐사 포인트 &8⇨  &a소지금액&7으로 교환할 수 있습니다!\n&6    실수로 잠수가 풀렸다구요? /잠수".color())
            }
    }
}
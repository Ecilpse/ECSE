package kr.ecse.recommend

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.events.PacketAdapter
import com.comphenix.protocol.events.PacketEvent
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.event.HoverEvent
import org.bukkit.Bukkit
import org.bukkit.Sound
import java.util.*

object PacketListener: PacketAdapter(main, PacketType.Play.Client.UPDATE_SIGN) {
    override fun onPacketReceiving(event: PacketEvent) { //override는 상속받아서 쓴거
        val player = event.player
        val bp = event.packet.blockPositionModifier.read(0) //bp = blockposition
        if(bp.x == 0 && bp.y == 0 && bp.z == 0) {
            val line1 = event.packet.stringArrays.read(0).first() //stringArrays는 문자열들의 모임 표지판 통째로 리스트로 가져옴. read(0)은 보통 그냥 0으로 가져옴
            Bukkit.getPlayerExact(line1)?.let {
                val offlinePlayer = it
                if(it == player) Manager.failedMessage(player, true)
                else Manager.reward(player, offlinePlayer.name)
            } ?: run {
                main.server.scheduler.runTaskAsynchronously(main, Runnable { //Asynchronously 임의로 비동기. 떼면 임의로 동기
                    player.sendMessage("&f  &f플레이어 조회중입니다. 잠시만 기다려주세요..".color())
                    val offlinePlayer = Bukkit.getOfflinePlayers().firstOrNull { it.name == line1 }
                    if (offlinePlayer != null) {
                        offlinePlayer.name?.let { Manager.reward(player, it) }
                    }
                    else {
                        Manager.failedMessage(player, false)
                    }
                })
            }
        }
    }
}
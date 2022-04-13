package kr.ecse.explore

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*
import kotlin.collections.HashSet

object Manager {
    public var vote = HashSet<UUID>()
    fun addVote(uuid: UUID) = vote.add(uuid)
    fun removeVote(uuid: UUID) = vote.remove(uuid)
    fun checkVote(uuid: UUID): Boolean { return vote.contains(uuid) }

    public var explore = HashSet<Player>()
    fun addExplore(player: Player) = explore.add(player)
    fun removeExplore(player: Player) = explore.remove(player)
    fun checkExplore(player: Player): Boolean { return explore.contains(player) }


    fun exploreRun(player: Player) {
        if(Manager.checkVote(player.uniqueId) || player.hasPermission("vote.subaccount")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rt ${player.name}")
            Bukkit.getServer().scheduler.runTaskLater(main, Runnable() {
                Manager.addExplore(player)
                Bukkit.getPlayer(player.name)?.sendTitle("&f\uE5EA &b잠수탐사 &a활성화".color(), "&7움직임 발생시 탐사가 종료됩니다.".color(), 5, 40, 5)
            }, 60)
        }
        else {
            Bukkit.getPlayer(player.name)?.sendTitle("&b추천.. 하셨나요?".color(), "&e/추천&7 후 이용 가능합니다.".color(), 5, 40, 5)
        }
    }

    fun exploreReward() {
        main.server.scheduler.runTaskTimer(main, Runnable {
            Bukkit.getServer().onlinePlayers.forEach {
                if (Manager.checkExplore(it)) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ecoin give ${it.name} 4")
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "전송 give ${it.name} 600")
                }
            }
        }, 6000, 6000)
    }
}
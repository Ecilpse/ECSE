package kr.ecse.vote

import kotlinx.coroutines.delay
import kr.ecse.vote.coroutine.SynchronizationContext
import kr.ecse.vote.coroutine.schedule
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.util.*
import kotlin.collections.HashSet

object Manager {
    public var vote = HashSet<UUID>()
    fun addVote(uuid: UUID) = vote.add(uuid)
    fun removeVote(uuid: UUID) = vote.remove(uuid)
    fun checkVote(uuid: UUID): Boolean { return vote.contains(uuid) }

    fun voteParty() {
        main.server.scheduler.schedule(main, SynchronizationContext.ASYNC) {
            main.server.broadcastMessage("&f  &e2&f분 뒤에 &d추천파티&f가 시작됩니다.".color() + "&6    &6추천을 하셔야 추천파티 보상을 받을 수 있습니다. &7(&e/추천&7)".color())
            waitFor(1200)
            main.server.broadcastMessage("&f  &e1&f분 뒤에 &d추천파티&f가 시작됩니다.".color() + "&6    &6추천을 하셔야 추천파티 보상을 받을 수 있습니다. &7(&e/추천&7)".color())
            waitFor(600)
            main.server.broadcastMessage("&f  &e30&f초 뒤에 &d추천파티&f가 시작됩니다.".color() + "&6    &6추천을 하셔야 추천파티 보상을 받을 수 있습니다. &7(&e/추천&7)".color())
            waitFor(600)
            main.server.broadcastMessage("&f \\uE435 &d추천파티&f 보상이 지급되었습니다! 인벤토리를 확인하세요!".color())
        }
        Bukkit.getServer().onlinePlayers.forEach {
            if (Manager.checkVote(it.uniqueId) || it.hasPermission("vote.subaccount")) {
                if (it.inventory.firstEmpty() == -1) {
                    it.sendMessage("&f꽉참".color())
                }
                else {
                    it.sendMessage("&f수령가능".color())
                }
            }
            else {
                it.sendMessage("&f  &6추천을 하지 않아 추천파티 보상을 받지 못하였습니다..".color() + "&d    매일매일 추천을 통해 다양한 혜택을 받아보세요!".color())
            }
        }
    }


    fun votePartyRewardGui(player: Player, args: String) {
        val inv = Bukkit.createInventory(null, 27, Component.text("추천파티 보상 설정"))
        player.openInventory(inv)
    }
    fun votePartySaveReward(name: String, inventory: Inventory, player: Player) {
        val list = ArrayList<ItemStack>()
        val reward: ItemStack
        inventory.apply {
            if (getItem(0) == null) {
                player.sendMessage("첫번째 칸에 아이템을 장입해주세요.")
                return
            }
            reward = getItem(0)!!
            getItem(0)?.let { list.add(it) } ?: list.add(ItemStack(Material.AIR))
        }
        Data.saveReward(name, reward, list)
        player.sendMessage("&f :notify: 성공적으로 추천파티 보상을 장입하였습니다.".color())
    }
}
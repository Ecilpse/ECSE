package kr.ecse.cook.listener

import kr.ecse.cook.manager.CookManager
import kr.ecse.cook.manager.SettingManager
import kr.ecse.cook.util.color
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

object Listener: Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if(event.view.title == "&f        \uE5D9\uF801\uE5DA\uF801\uE5DB".color()) {
            event.isCancelled = true
            when(val slot = event.rawSlot) { //rawslot 클릭한 칸
                48,50 -> {
                    if(event.currentItem?.type == Material.PAPER) {
                        val page = if(slot == 48) -1 else 1 //48일떄는 page = -1  아닐경우 page = 1
                        CookManager.openRecipeGui(event.whoClicked as Player,event.clickedInventory!!.getItem(49)!!.amount + page)
                    }
                }
                6,15,24,33,42 -> {
                    if(event.currentItem != null) {
                        val index = (event.clickedInventory!!.getItem(49)!!.amount * 5 - 5) + ((slot - 6) / 9)
                        CookManager.openCookGui(event.whoClicked as Player, index)
                    }
                }
            }
        }
        else if(event.view.title == "&f        \uE606\uF801\uE607\uF801\uE608".color()) {
            event.isCancelled = true
            if(event.rawSlot == 22) {
                val list = ArrayList<ItemStack>()
                event.inventory.run { //apply : 블록이 끝나고 다시 사용할 때, run : 블록 안에서 끝낼떄는 run
                    for(slot in 1..19 step 9) {
                        getItem(slot)?.let { list.add(it) }
                    }
                    val food = getItem(16)!!
                    when(event.click) {
                        ClickType.LEFT -> {
                            CookManager.startCook(event.whoClicked as Player, food, list,false)
                        }
                        ClickType.SHIFT_LEFT -> {
                            CookManager.startCook(event.whoClicked as Player, food, list,true)
                        }
                        else -> return
                    }
                }
            }
            else if(event.rawSlot == 4) CookManager.openRecipeGui(event.whoClicked as Player, 1)
        }
    }
    @EventHandler
    fun onInventoryClose(event: InventoryCloseEvent) {
        event.view.title.let {
            if(it == "&f        \uE5D9\uF801\uE5DA\uF801\uE5DB".color() || it == "&f        \uE606\uF801\uE607\uF801\uE608".color()) {
                CookManager.removeViewer(event.player.uniqueId)
                return
            }
            else if(!it.contains("요리법 설정 - ")) return//그냥 return 전체취소
            val name = it.replace("요리법 설정 - ", "")
            SettingManager.saveRecipe(name, event.inventory, event.player as Player)
        }
        //if(event.view.title.contains("요리법 설정"))//플레이어 인벤토리 타이틀 따올떄는 view 씀
    }
}
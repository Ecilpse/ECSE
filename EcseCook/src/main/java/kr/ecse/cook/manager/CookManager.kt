package kr.ecse.cook.manager

import kr.ecse.cook.data.Data
import kr.ecse.cook.item.Item
import kr.ecse.cook.util.color
import kr.ecse.cook.util.countEmptySlot
import kr.ecse.cook.util.countItem
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet

object CookManager {
    var toggle = true
    fun enableCook() {toggle = true}
    fun disableCook() {toggle = false}

    var viewerList = HashSet<UUID>() //list로 해도 되지만 hashset은 탐색할떄 용이함 중복안됨
    fun removeViewer(uuid: UUID) = viewerList.remove(uuid)
    fun addViewer(uuid: UUID) = viewerList.add(uuid)
    fun closeInvOfViewer() {
        viewerList.forEach { Bukkit.getPlayer(it)?.closeInventory() }
        viewerList = HashSet() // clear보다는 그냥 공란으로 새로 선언
    }

    fun openRecipeGui(player: Player, page: Int) {
        if (!toggle) return // if (!toggle) => toggle이 false 일 때
        val inv = Bukkit.createInventory(null,54,Component.text("&f        \uE5D9\uF801\uE5DA\uF801\uE5DB".color()))
        inv.apply {
            for(slot in 0..53) setItem(slot, Item.wallpaper)
            val data = Data.getValues()
            setItem(49,Item.currentButton.clone().asQuantity(page))
            if(data.elementAtOrNull(page * 5 - 6) != null) setItem(48,Item.prevButton)
            if(data.elementAtOrNull(page * 5) != null) setItem(50,Item.nextButton)
            for(i in 0..4) {
                data.elementAtOrNull(page * 5 - 5 + i)?.let {
                    it.values.first().forEachIndexed { index, item -> setItem(i * 9 + 2 + index,item) } //index, item을 추출하여 정렬한다
                    setItem(i * 9 + 6,it.keys.first().clone().apply {
                        val newLore = lore?.plus(listOf("","&a클릭하여 요리를 시작합니다.".color())) ?:
                            listOf("","&a클릭하여 요리를 시작합니다.".color())
                        lore = newLore
                    })
                    setItem(i * 9 + 5,Item.cookpreviewButton)
                } ?: break//for 문을 끊는 것
            }
        }
        addViewer(player.uniqueId)
        player.openInventory(inv)
    }
    fun openCookGui(player: Player, index: Int) {
        val inv = Bukkit.createInventory(null, 27, Component.text("&f        \uE606\uF801\uE607\uF801\uE608".color()))
        inv.apply {
            for(slot in 0..26) setItem(slot, Item.wallpaper)

            setItem(4, Item.recipeButton)
            setItem(12, Item.wallpaper2)
            setItem(13, Item.wallpaper2)
            setItem(14, Item.wallpaper2)
            setItem(22, Item.cookButton)

            val map = Data.getValueWithIndex(index)!!
            val food = map.keys.first()
            val material = map.values.first().iterator() //iterator 리스트를 넘기면서 볼 수 있게끔하는 메소드. listiterator 도 있는데 이건 뒤로 넘길때도 가능
            val air = ItemStack(Material.AIR)
            for(slot in 1..19 step 9) {
                if(material.hasNext()) setItem(slot,material.next())
                else setItem(slot,air)
            }
            setItem(16,food)
        }
        addViewer(player.uniqueId)
        player.openInventory(inv)
    }

    fun startCook(player: Player, food: ItemStack, list: List<ItemStack>, full: Boolean) {
        player.inventory.apply {
            val material = HashMap<ItemStack,Int>()
            var count = Int.MAX_VALUE //21억쯤됨
            var reward: Int? = null
            val emptySlot = countEmptySlot()
            if(emptySlot <= 0) return
            list.sortedByDescending { player.inventory.countItem(it) }.forEach { item -> //countItem : 인벤토리에 해당 아이템의 갯수를 셈
                countItem(item).let {
                    if(it < item.amount) {
                        player.sendMessage("&f \uE5CB 재료가 부족합니다.".color())
                        return
                    } else {
                        if(count > it / item.amount) { //it이 가지고 있는것
                            if(full) {
                                count = it / item.amount //인벤토리재료 나누기 레시피 재료
                                reward = count * food.amount
                                if(emptySlot * 64 < reward!!) {
                                    count = emptySlot * 64 / food.amount
                                    reward = count * food.amount
                                }
                                material.forEach { map -> if(map.value / map.key.amount > count) material[map.key] = count * map.key.amount }
                            } else {
                                count = 1
                                reward = count * food.amount
                            }
                        }
                        material[item] = count * item.amount // 재료 갯수 = 없어져야할 총 갯수
                    }
                }
            }
            material.forEach { removeItem(it.key.asQuantity(it.value)) } //it.key가 아이템, .asQuantity(it.value) it.value의 값 만큼 삭제
            addItem(food.asQuantity(reward!!))
            player.playSound(player.location, Sound.BLOCK_BREWING_STAND_BREW, 1F, 1F) //float 형태는 1F .0 은 double 형태
        }
    }
}
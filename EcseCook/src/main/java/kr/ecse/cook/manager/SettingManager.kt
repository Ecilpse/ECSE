package kr.ecse.cook.manager

import kr.ecse.cook.data.Data
import kr.ecse.cook.item.Item
import kr.ecse.cook.util.color
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

object SettingManager {
    fun openSetGui(player: Player, args: String) {
        val inv = Bukkit.createInventory(null, 27, Component.text("요리법 설정 - $args"))
        val air = ItemStack(Material.AIR)
        inv.apply {
            for(slot in 0..26) setItem(slot, Item.wallpaper)
            for(slot in 1..19 step 9) setItem(slot, air)
            setItem(16, air)
        }
        player.openInventory(inv)
    }
    fun saveRecipe(name: String, inventory: Inventory, player: Player) {
        val list = ArrayList<ItemStack>()
        val reward: ItemStack
        inventory.apply {
            if (getItem(16) == null) {
                player.sendMessage("결과창에 아이템을 장입해주세요.")
                return //결과 값 없이 끝내겠다. (함수 전체가 끝남) return@apply 를 하면 inventory.apply { } 구문을 마친다.
            }
            reward = getItem(16)!!

            getItem(1)?.let { list.add(it) } ?: list.add(ItemStack(Material.AIR)) //?: 없을떄는 뒤 구문 실행. 보통 default 설정값 넣을떄 많이 씀
            getItem(10)?.let { list.add(it) } ?: list.add(ItemStack(Material.AIR))
            getItem(19)?.let { list.add(it) } ?: list.add(ItemStack(Material.AIR))
        }
        Data.save(name, reward, list)
        player.sendMessage("&f :notify: 성공적으로 &e${name}&f을 장입하였습니다.".color())
    }
    fun removeRecipe(player: Player, name: String) {
        Data.remove(name)
        player.sendMessage("&f :notify: 성공적으로 &e${name}&f을 삭제하였습니다.".color())
    }
    fun getRecipe(player: Player, page: Int) {
        val list = Data.getKeys() //hashmap
        if (list.elementAtOrNull(page * 10 - 10) != null) {//해당 index에 값이 없으면 null 반환. 있으면 그 항목 자체가 반환
            for (i in page * 10 - 10 until page * 10) {
                list.elementAtOrNull(i)?.let {
                    player.sendMessage("${i + 1} - $it")
                } ?: player.sendMessage("${i + 1} - ") //?. 해도 ?: 있으면 여기로 넘어감 if -else 문이라 보면 편함
            }
        }
    }
}
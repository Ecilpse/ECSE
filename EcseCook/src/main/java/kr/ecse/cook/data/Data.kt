package kr.ecse.cook.data

import kr.ecse.cook.global.main
import kr.ecse.cook.item.Item
import kr.ecse.cook.manager.CookManager
import kr.ecse.cook.manager.SettingManager
import kr.ecse.cook.util.toItemStack
import kr.ecse.cook.util.toStringData
import org.bukkit.Material
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.io.File

object Data {
    private val file = File(main.dataFolder, "config.yml")
    private var config = YamlConfiguration.loadConfiguration(file)

    private val recipe = HashMap<String, HashMap<ItemStack, List<ItemStack>>>()

    fun save(name: String, reward: ItemStack, material: List<ItemStack>) {
        recipe[name] = hashMapOf(reward to material) //hashMapOf 안에는 A to B 써야함. of가 선언과 동시에 맵을 만듬
        config.apply {
            set("recipe.$name.reward", reward.toStringData())
            material.forEachIndexed { index, itemStack -> //index가 있는 loop를 돌린다.
                if(itemStack.type.isAir) set("recipe.$name.material.item${index+1}", "")
                else set("recipe.$name.material.item${index+1}", itemStack.toStringData())
            }
            save(file)
        }
    }
    fun remove(name: String) {
        recipe.remove(name)
        config.apply {
            set("recipe.$name", null)
            save(file)
        }
    }
    fun load() {
        CookManager.disableCook()
        CookManager.closeInvOfViewer()
        config.run {
            getConfigurationSection("recipe")?.let{ it1 ->
                it1.getKeys(false).forEach { name -> //getKeys(true)로 하면 recipe까지 불러옴
                    lateinit var reward: ItemStack
                    getString("recipe.$name.reward")?.let { reward = it.toItemStack() } ?: main.logger.info("ecsecook/config/$name 의 reward 없음")  //reward의 오른쪽 부분만 따옴

                    val list = ArrayList<ItemStack>()
                    for (i in 1..3) {
                        getString("recipe.$name.material.item$i")?.let {
                            if(it != "") list.add(it.toItemStack())
                            else list.add(ItemStack(Material.AIR))
                        } ?: main.logger.info("ecsecook/config/$name/item$i 의 Item 정보 없음")
                    }
                    recipe[name] = hashMapOf(reward to list) //형태만 있어서 initalize 가 안됨. 40줄에서 나중에 지정하겠다고 선언해야 함. (lateinit)
                }
            }
        }
        CookManager.enableCook()
    }
    fun getKeys(): List<String> {
        return recipe.keys.toList()
    }
    fun getValues(): List<HashMap<ItemStack, List<ItemStack>>> {
        return recipe.values.toList()
    }
    fun getValueWithIndex(index: Int): HashMap<ItemStack, List<ItemStack>>? {
        return recipe.values.toList().elementAtOrNull(index) //hashmap 에는 index가 없으므로 tolist로 index 나열
    }
}
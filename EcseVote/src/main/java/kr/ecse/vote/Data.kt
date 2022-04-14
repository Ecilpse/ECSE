package kr.ecse.vote

import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.inventory.ItemStack
import java.io.File
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

object Data {
    private val fileConfig = File(main.dataFolder, "config.yml") //player로 바꾸고, config에는 current vote, total vote, 서버 spawnm, sv,mf
    private val filePlayer = File(main.dataFolder, "player.yml")
    private var config = YamlConfiguration.loadConfiguration(file)

    var currentVote: Int = 0
    var totalVote: Int = 0

    fun save() {
        config.apply {
            set("currentVote", currentVote)
            set("totalVote", totalVote)
            save(fileConfig)
        }
        config.apply {
            set("voteUUIDs", Manager.vote.map{it.toString()})
            save(file_player)
        }
    }

    fun autoSave() {
        main.server.scheduler.runTaskTimerAsynchronously(main, Runnable {
            save()
        }, 12000, 12000)
    }

    fun load() {
        fileConfig.run {
            currentVote = getInt("currentVote")
            totalVote = getInt("totalVote")
            getString("votePartyReward")?.let { votePartyReward = it.toItemStack() } ?: main.logger.info("ecsevote/config/$name 의 votePartyReward 없음")
        }
        filePlayer.run {
            val server = getString("server")
            val list = getStringList("voteUUIDs")
            list.forEach {
                Manager.vote.add(UUID.fromString(it))
            }
        }
    }

  // fun load() {
  //     CookManager.disableCook()
  //     CookManager.closeInvOfViewer()
  //     config.run {
  //         getConfigurationSection("recipe")?.let{ it1 ->
  //             it1.getKeys(false).forEach { name -> //getKeys(true)로 하면 recipe까지 불러옴
  //                 lateinit var reward: ItemStack
  //                 getString("recipe.$name.reward")?.let { reward = it.toItemStack() } ?: main.logger.info("ecsecook/config/$name 의 reward 없음")  //reward의 오른쪽 부분만 따옴

  //                 val list = ArrayList<ItemStack>()
  //                 for (i in 1..3) {
  //                     getString("recipe.$name.material.item$i")?.let {
  //                         if(it != "") list.add(it.toItemStack())
  //                         else list.add(ItemStack(Material.AIR))
  //                     } ?: main.logger.info("ecsecook/config/$name/item$i 의 Item 정보 없음")
  //                 }
  //                 recipe[name] = hashMapOf(reward to list) //형태만 있어서 initalize 가 안됨. 40줄에서 나중에 지정하겠다고 선언해야 함. (lateinit)
  //             }
  //         }
  //     }
  //     CookManager.enableCook()


    fun saveReward(name: String, reward: ItemStack, material: List<ItemStack>) {
        config.apply {

            save(file2)
        }
    }
}
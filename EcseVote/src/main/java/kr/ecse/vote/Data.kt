package kr.ecse.vote

import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.inventory.ItemStack
import java.io.File
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

object Data {
    private val file = File(main.dataFolder, "config.yml")
    private val file2 = File(main.dataFolder, "reward.yml")
    private var config = YamlConfiguration.loadConfiguration(file)

    var currentVote: Int = 0
    var totalVote: Int = 0

    fun save() {
        config.apply {
            set("currentVote", currentVote)
            set("totalVote", totalVote)
            set("voteUUIDs", Manager.vote.map{it.toString()})
            save(file)
        }
    }

    fun autoSave() {
        main.server.scheduler.runTaskTimerAsynchronously(main, Runnable {
            save()
        }, 12000, 12000)
    }

    fun load() {
        config.run {
            currentVote = getInt("currentVote")
            totalVote = getInt("totalVote")
            val list = getStringList("voteUUIDs")
            list.forEach {
                Manager.vote.add(UUID.fromString(it))
            }
        }
    }

    fun saveReward(name: String, reward: ItemStack, material: List<ItemStack>) {
        config.apply {

            save(file2)
        }
    }
}
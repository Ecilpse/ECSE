package kr.ecse.coin

import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.util.*

object Data {
    private val file = File(main.dataFolder, "config.yml")
    private var config = YamlConfiguration.loadConfiguration(file)

    var currentVote: Int = 0
    var totalVote: Int = 0

    fun save() {
        val map = CoinManager.coinMap.values
        config.run {
            map.forEach {
                set("Data.${it.owner}", it.amount)
            }
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
            val section = getConfigurationSection("Data")
            if (section == null) {
                println("ecsecoin 섹션오류. 플러그인을 종료합니다.")
                main.onDisable()
                return
            }

            val keys = section.getKeys(false)
            keys.forEach {
                CoinManager.loadData(UUID.fromString(it), config.getInt("Data.$it"))
            }
        }
    }
}
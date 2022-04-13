package kr.ecse.explore

import me.clip.placeholderapi.PlaceholderAPI
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class EcseExplore:JavaPlugin() {
    override fun onEnable() {
        main = this
        this.server.pluginManager.registerEvents(Listener(), this)
        main.getCommand("ecsevote")!!.setExecutor(Command())
        PlaceHolder().register()
        Data.load()
        Data.autoSave()
        Manager.exploreReward()
        logger.info("ECSE Explore 활성화")
    }

    override fun onDisable() {
        logger.info("ECSE Explore 비활성화")
    }
}
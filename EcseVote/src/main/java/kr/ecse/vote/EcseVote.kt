package kr.ecse.vote

import me.clip.placeholderapi.PlaceholderAPI
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class EcseVote:JavaPlugin() {
    override fun onEnable() {
        main = this
        this.server.pluginManager.registerEvents(Listener(), this)
        main.getCommand("ecsevote")!!.setExecutor(Command())
        PlaceHolder().register()
        Data.load()
        Data.autoSave()
        logger.info("ECSE VOTE 활성화")
    }

    override fun onDisable() {
        logger.info("ECSE VOTE 비활성화")
    }
}
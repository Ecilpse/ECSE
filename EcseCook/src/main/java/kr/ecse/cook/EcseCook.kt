package kr.ecse.cook

import kr.ecse.cook.data.Data
import kr.ecse.cook.global.Command
import kr.ecse.cook.global.main
import kr.ecse.cook.listener.Listener
import org.bukkit.plugin.java.JavaPlugin

class EcseCook:JavaPlugin() {
    override fun onEnable() {
        main = this
        main.getCommand("요리")!!.setExecutor(Command())
        this.server.pluginManager.registerEvents(Listener, this)
        //this.server.pluginManager.registerEvents(Listener, this)
        Data.load()
        logger.info("ECSE COOK 활성화")
    }

    override fun onDisable() {
        logger.info("ECSE COOK 비활성화")
    }
}
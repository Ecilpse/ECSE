package kr.ecse.sit

import org.bukkit.plugin.java.JavaPlugin

class EcseSit:JavaPlugin() {
    override fun onEnable() {
        main = this
        main.getCommand("앉기")!!.setExecutor(Command())
        this.server.pluginManager.registerEvents(Listener, this)
        logger.info("활성화")
    }

    override fun onDisable() {
        super.onDisable()
        logger.info("비활성화")
    }
}
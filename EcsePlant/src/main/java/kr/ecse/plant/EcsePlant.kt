package kr.ecse.plant

import org.bukkit.plugin.java.JavaPlugin

class EcsePlant:JavaPlugin() {
    override fun onEnable() {
        main = this
        main.getCommand("자동심기")!!.setExecutor(Command())
        this.server.pluginManager.registerEvents(Listener, this)
        logger.info("Ecse plant 활성화")
    }

    override fun onDisable() {
        logger.info("Ecse plant 비활성화")
    }
}
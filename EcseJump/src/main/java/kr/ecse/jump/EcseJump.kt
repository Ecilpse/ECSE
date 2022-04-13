package kr.ecse.jump

import org.bukkit.plugin.java.JavaPlugin

class EcseJump:JavaPlugin() {
    override fun onEnable() {
        main = this
        main.getCommand("더블점프")!!.setExecutor(Command())
        this.server.pluginManager.registerEvents(Listener, this)
        logger.info("EcseJump 활성화")
    }

    override fun onDisable() {
        logger.info("EcseJump 비활성화")
    }
}
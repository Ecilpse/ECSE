package kr.ecse.hat

import org.bukkit.plugin.java.JavaPlugin

class EcseHat:JavaPlugin() {
    override fun onEnable() {
        main = this
        main.getCommand("모자")!!.setExecutor(Command())
        logger.info("EcseHat was enabled")
    }

    override fun onDisable() {
        logger.info("EcseHat was disabled")
    }
}
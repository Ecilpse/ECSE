package kr.ecse.generator

import org.bukkit.plugin.java.JavaPlugin

class EcseGenerator:JavaPlugin() {
    override fun onEnable() {
        main = this
        main.getCommand("eg")!!.setExecutor(Command())
        logger.info("EcseGenerator has enabled.")
    }

    override fun onDisable() {
        logger.info("EcseGenerator has disabled.")
    }
}
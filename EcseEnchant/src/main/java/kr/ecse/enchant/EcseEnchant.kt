package kr.ecse.enchant

import org.bukkit.plugin.java.JavaPlugin

class EcseEnchant: JavaPlugin() {
    override fun onEnable() {
        main = this
        main.getCommand("강화")!!.setExecutor(Command())
        this.server.pluginManager.registerEvents(Listener, this)
        logger.info("플러그인 활성화")
    }
    override fun onDisable() {
        logger.info("플러그인 비활성화")
    }
}
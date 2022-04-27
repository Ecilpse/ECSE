package kr.ecse.coin

import org.bukkit.plugin.java.JavaPlugin

class EcseCoin:JavaPlugin() {
    override fun onEnable() {
        main = this
        main.getCommand("ecoin")!!.setExecutor(Command())
        PlaceHolder().register()
        Data.load()
        Data.autoSave()
        logger.info("ECSE Coin 활성화")
    }

    override fun onDisable() {
        logger.info("ECSE Coin 비활성화")
    }
}
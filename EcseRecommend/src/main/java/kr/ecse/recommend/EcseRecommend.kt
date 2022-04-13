package kr.ecse.recommend

import com.comphenix.protocol.ProtocolLibrary
import org.bukkit.plugin.java.JavaPlugin

class EcseRecommend:JavaPlugin() {
    override fun onEnable() {
        main = this
        main.getCommand("recommend")!!.setExecutor(Command())
        pm = ProtocolLibrary.getProtocolManager()
        this.server.pluginManager.registerEvents(Listener, this)
        pm.addPacketListener(PacketListener)
        logger.info("ECSE RECOMMEND 활성화")
    }

    override fun onDisable() {
        logger.info("ECSE RECOMMEND 비활성화")
    }
}
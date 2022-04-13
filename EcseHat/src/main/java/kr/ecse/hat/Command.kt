package kr.ecse.hat

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import org.bukkit.util.StringUtil

class Command: TabExecutor {
    private val arglist = listOf("reload")
    override fun onTabComplete(sender: CommandSender, p1: Command, p2: String, args: Array<out String>): MutableList<String>? {
        val list = ArrayList<String>()
        when(args.size) {
            1 -> StringUtil.copyPartialMatches(args[0], arglist, list)
        }
        list.sort()
        return list
    }

    override fun onCommand(sender: CommandSender, p1: Command, p2: String, args: Array<out String>): Boolean {
        val player = sender as Player
        if (args.size == 0) {
            Equip.equip(player)
        } else {
            when (args[0]) {
                "reload" -> {
                    if (player.isOp) {
                        player.sendMessage("config가 리로드 되었습니다.")
                    }
                }
            }
        }
        return true
    }
}
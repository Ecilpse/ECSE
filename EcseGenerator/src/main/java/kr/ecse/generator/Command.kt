package kr.ecse.generator

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import org.bukkit.util.StringUtil

class Command: TabExecutor {
    private val argList = listOf("reload", "select")
    override fun onTabComplete(sender: CommandSender, p1: Command, p2: String, args: Array<out String>): MutableList<String>? {
        val player = sender as Player
        val list = ArrayList<String>()
        when(args.size) {
            1 -> StringUtil.copyPartialMatches(args[0], argList, list)
        }
        return list
    }

    override fun onCommand(p0: CommandSender, p1: Command, p2: String, p3: Array<out String>): Boolean {
        TODO("Not yet implemented")
    }
}
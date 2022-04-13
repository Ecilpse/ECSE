package kr.ecse.recommend

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player

class Command: CommandExecutor {
    override fun onCommand(sender: CommandSender, p1: Command, p2: String, args: Array<out String>): Boolean {
        if(sender is Player) {
            if(sender.isOp) {
                Bukkit.getPlayer(args[0])?.let { Manager.openSign(it) }
            }
            else if (Manager.checkRetry(sender.uniqueId)) {
                if(args[0] == sender.name) Bukkit.getPlayer(args[0])?.let { Manager.openSign(it) } //args 0 이 첫번쨰 args
            }
        }
        return true
    }
}
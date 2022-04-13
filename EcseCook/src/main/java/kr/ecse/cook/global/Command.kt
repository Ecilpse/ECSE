package kr.ecse.cook.global

import kr.ecse.cook.data.Data
import kr.ecse.cook.manager.CookManager
import kr.ecse.cook.manager.SettingManager
import org.bukkit.Bukkit
import org.bukkit.command.*
import org.bukkit.command.Command
import org.bukkit.entity.Player
import org.bukkit.util.StringUtil
import java.io.Console

class Command: TabExecutor {
    private val arglist = listOf("리로드", "등록", "목록", "삭제", "창")
    override fun onTabComplete(p0: CommandSender, p1: Command, p2: String, args: Array<out String>): MutableList<String>? {
        val list = ArrayList<String>()
        when(args.size) {
            1 -> StringUtil.copyPartialMatches(args[0], arglist, list)
        }
        list.sort()
        return list
    }

    override fun onCommand(sender: CommandSender, p1: Command, p2: String, args: Array<out String>): Boolean {
        if (args.size == 0) {
        } else {
            if(sender is Player) {
                if(!sender.isOp) {
                    sender.sendMessage(":notify: 권한이 없습니다.")
                    return false
                }
                when (args[0]) {
                    "리로드" -> {
                        Data.load()
                        sender.sendMessage(":notify: 리로드 되었습니다.")
                    }
                    "등록" -> {
                        if (args.size == 2) {
                            SettingManager.openSetGui(sender, args[1])
                        }
                    }
                    "삭제" -> {
                        if (args.size == 2) {
                            SettingManager.removeRecipe(sender, args[1])
                        }
                    }
                    "목록" -> {
                        if (args.size == 2) {
                            SettingManager.getRecipe(sender, args[1].toInt())
                        }
                    }
                    "창" -> {
                        CookManager.openRecipeGui(sender, args[1].toInt())
                    }
                }
            }
            else {
                when (args[0]) {
                    "리로드" -> {
                        Data.load()
                        main.logger.info(":notify: 리로드 되었습니다.")
                    }
                    "창" -> {
                        if (args.size == 2) {
                            Bukkit.getPlayer(args[1])?.let {
                                //if (it.hasPermission("vote.subaccount")) return false
                                CookManager.openRecipeGui(it, 1) } // /요리 목록 1 [player] args 도 list 처럼 0부터 시작임
                        }
                    }
                }
            }
        }
        return true
    }
}
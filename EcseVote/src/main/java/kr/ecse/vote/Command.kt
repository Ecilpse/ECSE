package kr.ecse.vote

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.io.Console
import java.util.*
import kotlin.collections.HashSet

class Command: CommandExecutor {
    override fun onCommand(sender: CommandSender, p1: Command, p2: String, args: Array<out String>): Boolean {
        if(sender is Player) {
            if(!sender.isOp) {
                if (args[0] == "설정") {
                    Manager.addVote(sender.uniqueId)
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ge execute all ecsevote 설정 ${sender.name}")
                    sender.sendMessage("&f :notify: 당신의 추천변수가 설정되었습니다.".color())
                }
            }
            else if(sender.isOp) {
                if (args.isEmpty()) {
                    sender.apply {
                        sendMessage(("&f :notify: 추천파티 명령어" +
                                "\n&f    &e/추천파티 실행 &8| &f추천파티를 즉시 실행합니다." +
                                "\n&f    &e/ecsevote 보기 &8| &f현재 추천파티 갯수를 봅니다." +
                                "\n&f    &e/ecsevote 현재변경 [갯수] &8| &f현재 추천파티 갯수를 변경합니다." +
                                "\n&f    &e/ecsevote 전체변경 [갯수] &8| &f전체 추천파티 갯수를 변경합니다." +
                                "\n&f    &e/ecsevote 리로드 &8| &f추천파티를 리로드 합니다.").color())
                    }
                    return false
                }
                when (args[0]) {
                    "상점" -> {
                        if (args.size == 2) {
                            if (Manager.checkVote(sender.uniqueId) || sender.hasPermission("vote.subaccount")) {
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "shop ${sender.name} ${args[1]}")
                            }
                            else {
                                Bukkit.getPlayer(sender.name)?.sendTitle("&b추천.. 하셨나요?".color(), "&e/추천&7 후 이용 가능합니다.".color(), 5, 40, 5)
                            }
                        }
                    }
                    "보기" -> {
                        sender.sendMessage("&f :notify: 현재 추천갯수는 &e${Data.currentVote}&7/&e${Data.totalVote} &7입니다.".color())
                    }
                    "리로드" -> {
                        Data.save()
                        Data.load()
                        sender.sendMessage("&f :notify: 리로드 되었습니다.".color())
                    }
                    "전체변경" -> {
                        if (args.size == 2) {
                            Data.totalVote = args[1].toIntOrNull() ?: run {  sender.sendMessage("&f :notify: 정수형태로 적어주세요.".color()) ; return false }
                            Data.save()
                            sender.sendMessage("&f :notify: 전체 추천갯수가 &e${Data.totalVote}&f개로 변경되었습니다.".color())
                        }
                    }
                    "현재변경" -> {
                        if (args.size == 2) {
                            Data.currentVote = args[1].toIntOrNull() ?: run {  sender.sendMessage("&f :notify: 정수형태로 적어주세요.".color()) ; return false }
                            Data.save()
                            sender.sendMessage("&f :notify: 현재 추천갯수가 &e${Data.currentVote}&f개로 변경되었습니다.".color())
                        }
                    }
                    "설정" -> {
                        if (args.size == 2) {
                            Bukkit.getPlayerUniqueId(args[1])?.let { Manager.addVote(it) } ?: sender.sendMessage("&f :notify: &e${args[1]}&f님이 UUID가 조회되지 않습니다.".color())
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ge execute all ecsevote 설정 ${args[1]}")
                            sender.sendMessage("&e${args[1]}&f님의 추천변수가 설정되었습니다.".color())
                        }
                        Manager.addVote(sender.uniqueId)
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ge execute all ecsevote 설정 ${sender.name}")
                        sender.sendMessage("&f :notify: 당신의 추천변수가 설정되었습니다.".color())
                    }
                    "목록" -> {
                        if (args.size == 2) {
                            Manager.vote.forEach {
                                sender.sendMessage("$it")
                            }
                        }
                    }
                    "테스트" -> {
                        Manager.voteParty()
                    }
                    else -> {
                        sender.sendMessage("&f :notify: &e${args[0]}&f님의 추천상태는 ${Bukkit.getPlayerUniqueId(args[0])?.let { Manager.checkVote(it) } }".color())
                    }
                }
            }
        }
        else {
            when (args[0]) {
                "초기화" -> {
                    Manager.vote = HashSet()
                    Data.save()
                    Data.load()
                }
                "설정" -> {
                    if (args.size == 2) {
                        Bukkit.getPlayerUniqueId(args[1])?.let { Manager.addVote(it) } ?: sender.sendMessage("&f :notify: &e${args[1]}&f님이 UUID가 조회되지 않습니다.".color())
                        sender.sendMessage("&f :notify: &e${args[1]}&f님의 추천변수가 설정되었습니다.".color())
                    }
                }
            }
        }
        return true
    }
}
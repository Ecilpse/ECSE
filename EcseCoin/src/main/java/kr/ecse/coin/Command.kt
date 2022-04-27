package kr.ecse.coin

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import kotlin.collections.HashSet

class Command: CommandExecutor {
    override fun onCommand(sender: CommandSender, p1: Command, p2: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            if (!sender.isOp) {
                if (args.isEmpty()) {
                    sender.coin.amount += 1
                    sender.apply { "\n&f    &f당신의 이벤트 코인은 &e${sender.coin.amount}".color() }
                }
                when (args[0]) {
                    "give" -> {
                        if (args.size == 3) {
                            val player = Bukkit.getPlayer(args[1])
                            if (player == null) {
                                sender.sendMessage(("&f :notify: 해당 플레이어를 찾을 수 없습니다.".color()))
                                return false
                            }
                            val amount = args[2].toIntOrNull()
                            if (amount == null) {
                                sender.sendMessage(("&f :notify: 정수를 입력해주세요."))
                                return false
                            }
                            player.coin.addCoin(amount)
                        }
                    }
                }
            }
        }; return true
    }
}
//                    "설정" -> {
//                        CoinManager.addVote(sender.uniqueId)
//                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ge execute all ecsevote 설정 ${sender.name}")
//                    sender.sendMessage("&f :notify: 당신의 추천변수가 설정되었습니다.".color())
//                }
//            }
//            }
//            else if(sender.isOp) {
//                if (args.isEmpty()) {
//                    sender.apply {
//                        sendMessage(("&f :notify: 잠수탐사 명령어".color() +
//                                "\n&f    &e/ecsevote 실행 &8| &f잠수탐사를 실행합니다.".color() +
//                                "\n&f    &e/ecsevote 전체변경 [갯수] &8| &f전체 추천파티 갯수를 변경합니다.".color()))
//                    }
//                    return false
//                }
//                when (args[0]) {
//                    "실행" -> {
//                        CoinManager.exploreRun(sender)
//                    }
//                    "보기" -> {
//                        sender.sendMessage("&f :notify: 현재 추천갯수는 &e${Data.currentVote}&7/&e${Data.totalVote} &7입니다.".color())
//                    }
//                    "리로드" -> {
//                        Data.save()
//                        Data.load()
//                        sender.sendMessage("&f :notify: 리로드 되었습니다.".color())
//                    }
//                    "설정" -> {
//                        if (args.size == 2) {
//                            Bukkit.getPlayerUniqueId(args[1])?.let { CoinManager.addVote(it) } ?: sender.sendMessage("&f :notify: &e${args[1]}&f님이 UUID가 조회되지 않습니다.".color())
//                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ge execute all ecsevote 설정 ${args[1]}")
//                            sender.sendMessage("&e${args[1]}&f님의 추천변수가 설정되었습니다.".color())
//                        }
//                        CoinManager.addVote(sender.uniqueId)
//                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ge execute all ecsevote 설정 ${sender.name}")
//                        sender.sendMessage("&f :notify: 당신의 추천변수가 설정되었습니다.".color())
//                    }
//                    "목록" -> {
//                        if (args.size == 2) {
//                            CoinManager.vote.forEach {
//                                sender.sendMessage("$it")
//                            }
//                        }
//                    }
//                    else -> {
//                        sender.sendMessage("&f :notify: &e${args[0]}&f님의 추천상태는 ${Bukkit.getPlayerUniqueId(args[0])?.let { CoinManager.checkVote(it) } }".color())
//                    }
//                }
//            }
//        }
//        else {
//            when (args[0]) {
//                "실행" -> {
//                    if (args.size == 2) {
//                        Bukkit.getPlayer(args[1])?.let { CoinManager.exploreRun(it) }
//                        }
//                }
//                "초기화" -> {
//                    CoinManager.vote = HashSet()
//                    Data.save()
//                    Data.load()
//                }
//                "설정" -> {
//                    if (args.size == 2) {
//                        Bukkit.getPlayerUniqueId(args[1])?.let { CoinManager.addVote(it) } ?: sender.sendMessage("&f :notify: &e${args[1]}&f님이 UUID가 조회되지 않습니다.".color())
//                        sender.sendMessage("&f :notify: &e${args[1]}&f님의 추천변수가 설정되었습니다.".color())
//                    }
//                }
//            }
//        }
//        return true
//    }
//}
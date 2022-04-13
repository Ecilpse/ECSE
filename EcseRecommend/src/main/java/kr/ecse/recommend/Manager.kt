package kr.ecse.recommend

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.event.HoverEvent
import net.minecraft.core.BlockPosition
import net.minecraft.network.protocol.game.PacketPlayOutOpenSignEditor
import net.minecraft.world.item.EnumColor
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.entity.TileEntitySign
import org.bukkit.*
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer
import org.bukkit.craftbukkit.v1_18_R1.util.CraftChatMessage
import org.bukkit.entity.Player
import java.util.*
import kotlin.collections.HashSet

object Manager {
    private val retry = HashSet<UUID>()
    fun addRetry(uuid: UUID) = retry.add(uuid)
    fun removeRetry(uuid: UUID) = retry.remove(uuid)
    fun checkRetry(uuid: UUID) = retry.contains(uuid)

    fun openSign(player: Player) {
        val craftPlayer = (player as CraftPlayer).handle
        val location = Location(player.world, .0, .0, .0)
        val blockPosition = BlockPosition(0, 0, 0)
        player.sendBlockChange(location, Material.DARK_OAK_SIGN.createBlockData()) //플레이어에게만 보이는 블록을 패킷으로 보냄 .createblockdata를 해야함. 앞은 그냥 material 이므로
        val list = arrayOf(
            CraftChatMessage.fromString("")[0],
            CraftChatMessage.fromString("▲ ▲ ▲")[0],
            CraftChatMessage.fromString("맨 윗줄에 추천인")[0],
            CraftChatMessage.fromString("닉네임을 적어주세요.")[0]
        )
        val sign = TileEntitySign(blockPosition, Blocks.cl.n()) //블록위에 입혀지는 데이터 글씨 쓰는 부분은 따로 있다
        sign.apply {
            a(EnumColor.a)
            list.forEachIndexed { index, text ->
                a(index, text)
            }
        }
        craftPlayer.b.a(sign.c())
        val signPacket = PacketPlayOutOpenSignEditor(blockPosition)
        craftPlayer.b.a(signPacket) //가상의 표지판을 여는 행위
    }
    fun reward(player: Player, name: String) {
        player.run {
            removeRetry(uniqueId)
            sendTitle("&d추천인 등록 완료!".color(), "&7추천인에게 크레딧이 지급되었습니다!".color(), 5, 40, 5)
            player.sendMessage("&f  &e$name&f님에게 &d20&f크레딧이 지급되었습니다.".color())
            playSound(player.location, Sound.ENTITY_PLAYER_LEVELUP, 1F, 1F)
        }
        main.server.scheduler.runTask(main, Runnable {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "points give $name 20")
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ge execute all cmi broadcast &f :mc_nether_star: &b$name&f님이 서버에 지인을 초대하여 &e20 &d크레딧&f을 받았습니다. &7(&e${player.name}&f님&7)".color())
        })
    }
    fun failedMessage(player: Player, boolean: Boolean) { //t8
        val message = if(boolean) { //불린이 참일때 메세지라는 변수를 중괄호 안 내용으로 저장
            Component.text("&f \uE5D0 &f자신을 추천인으로 지명할 수 없습니다.\n&7    다시 시도하려면 채팅창을 열어 메세지를 클릭해주세요.".color())
                    .hoverEvent(HoverEvent.showText(Component.text("&b클릭하시면, 다시 입력하실 수 있습니다.".color())))
                    .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/recommend ${player.name}"))
        }
        else {
            Component.text("&f \uE5D0 &6서버에 접속하지 않은 플레이어입니다.\n&7    다시 시도하려면 채팅창을 열어 메세지를 클릭해주세요.".color())
                    .hoverEvent(HoverEvent.showText(Component.text("&b클릭하시면, 다시 입력하실 수 있습니다.".color())))
                    .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/recommend ${player.name}"))
        }
        player.sendMessage(message)
    }
    fun retryMessage(player: Player) {
        val message =
            Component.text("&f \uE5D0 &6혹시 추천인 지명을 못하셨나요?\n&7    다시 시도하려면 채팅창을 열어 메세지를 클릭해주세요.".color())
                    .hoverEvent(HoverEvent.showText(Component.text("&b클릭하시면, 다시 입력하실 수 있습니다.".color())))
                    .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/recommend ${player.name}"))
        player.sendMessage(message)
    }
}
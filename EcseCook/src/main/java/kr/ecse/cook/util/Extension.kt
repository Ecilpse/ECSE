package kr.ecse.cook.util

import kr.ecse.cook.global.main
import kr.ecse.cook.item.Item
import net.kyori.adventure.text.Component
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.util.io.BukkitObjectInputStream
import org.bukkit.util.io.BukkitObjectOutputStream
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.lang.Exception
import java.util.*

fun String.toItemStack(): ItemStack {//extension은 대문자, return자는 itemstack
    try {
        val stream = ByteArrayInputStream(Base64.getDecoder().decode(this))
        val data = BukkitObjectInputStream(stream)
        val item = ItemStack.deserializeBytes(data.readBytes())
        data.close()
        return item
    }
    catch (e: Exception) {
        e.printStackTrace()
    }
    return ItemStack(Material.AIR)
}
fun ItemStack.toStringData(): String {
    try { //try 구문을 실행해서 조건이 맞으면 catch 를 실행
        val string = ByteArrayOutputStream()
        val data = BukkitObjectOutputStream(string)
        data.write(serializeAsBytes()) //원래 this(itemstack).serializeAsBytes() 형식인데, 받아온 객체가 itemstack이라 생략 / serializeAsBytes() : Itemstack을 bytecode로 바꿔주는 메소드
        data.close()
        return Base64.getEncoder().encodeToString(string.toByteArray()) //itemstack 을 byte코드로 변환 후 string으로 변환
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return ""
}

fun String.color() : String {
    return ChatColor.translateAlternateColorCodes('&', this)
}

fun Inventory.countItem(item: ItemStack): Int {
    var amount = 0
    storageContents?.forEach {
        if(it?.asOne() == item.asOne()) { //asOne() => 하나일 때
            amount += it.amount
        }
    }
    return amount
}

fun Inventory.countEmptySlot(): Int {
    return storageContents?.filter { it == null }?.size ?: 0 //contents는 갑옷 슬롯에 있는 아이템을 불러옴 / storagecontents 는 갑옷 제외한 인벤칸에 있는 아이템 불러옴 (둘 다 리스트. 빈공간까지)
                                                                //filter : 리스트에서 쓸 수 있는 메소드 {} 안 조건에 해당되면 반환
}


package kr.ecse.cook.item

import kr.ecse.cook.util.color
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object Item {
    val wallpaper by lazy {
        val item = ItemStack(Material.BLACK_STAINED_GLASS_PANE)
        val meta = item.itemMeta
        meta.apply {
            displayName(Component.text("&7".color()))
        }
        item.itemMeta = meta
        item
    }
    val wallpaper2 by lazy {
        val item = ItemStack(Material.RED_STAINED_GLASS_PANE)
        val meta = item.itemMeta
        meta.apply {
            displayName(Component.text("&7".color()))
        }
        item.itemMeta = meta
        item
    }
    val recipeButton by lazy {
        val item = ItemStack(Material.WRITABLE_BOOK)
        val meta = item.itemMeta
        meta.apply {
            displayName(Component.text("&a요리법 보기".color()))
            lore(listOf(
                    Component.text("&7클릭 시 요리법에 대해".color()),
                    Component.text("&7확인 할 수 있습니다".color())
            ))
            item.itemMeta = meta
        }
        item
    }
    val cookButton by lazy {
        val item = ItemStack(Material.LIME_STAINED_GLASS_PANE)
        val meta = item.itemMeta
        meta.apply {
            displayName(Component.text("&a요리시작".color()))
            lore(listOf(
                    Component.text("&7클릭시 요리법에 맞게".color()),
                    Component.text("&7요리를 시작합니다.".color()),
                    Component.text("&f".color()),
                    Component.text("&f\uE5F6 &e요리 만들기".color()),
                    Component.text("&f\uE5F1&7+&f\uE5F6 &e요리 한 번에 만들기".color())
            ))
            item.itemMeta = meta
        }
        item
    }
    val prevButton by lazy {
        val item = ItemStack(Material.PAPER)
        val meta = item.itemMeta
        meta.apply {
            displayName(Component.text("&a이전 페이지".color()))
            setCustomModelData(11)
        }
        item.itemMeta = meta
        item
    }
    val cookpreviewButton by lazy {
        val item = ItemStack(Material.PAPER)
        val meta = item.itemMeta
        meta.apply {
            displayName(Component.text("&a요리 결과물".color()))
            setCustomModelData(4)
        }
        item.itemMeta = meta
        item
    }
    val currentButton by lazy {
        val item = ItemStack(Material.NETHER_STAR)
        val meta = item.itemMeta
        meta.apply {
            displayName(Component.text("&b현재 페이지".color()))
            item.itemMeta = meta
        }
        item
    }
    val nextButton by lazy {
        val item = ItemStack(Material.PAPER)
        val meta = item.itemMeta
        meta.apply {
            displayName(Component.text("&a다음 페이지".color()))
            setCustomModelData(9)
        }
        item.itemMeta = meta
        item
    }
    val backButton by lazy {
        val item = ItemStack(Material.IRON_DOOR)
        val meta = item.itemMeta
        meta.apply {
            displayName(Component.text("&6돌아가기".color()))
            item.itemMeta = meta
        }
        item
    }
}
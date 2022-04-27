package kr.ecse.coin

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*
import kotlin.collections.HashSet

object CoinManager {
    val coinMap = HashMap<UUID, Coin>() //인스턴스 시키기 위함 저장시키고 계속 쓰기 위함
    fun createData(uuid: UUID): Coin {
        val newCoin = Coin(uuid)
        coinMap[uuid] = newCoin
        return coinMap[uuid]!!
    }
    fun getData(uuid: UUID): Coin = coinMap[uuid] ?: createData(uuid)
    fun loadData(uuid: UUID, amount: Int) = Coin(uuid, amount)
}
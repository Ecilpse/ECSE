package kr.ecse.coin

import java.util.*

class Coin(val owner: UUID) {
    var amount = 0
    var eventCoin : Int?=null
    constructor(owner : UUID, amount: Int) : this(owner) {
        this.amount = amount
    }

    fun addCoin(amount: Int) {
        this.amount += amount
    }
    fun removeCoin(amount: Int) {
        this.amount -= amount
    }
    fun setCoin(amount: Int) {
        this.amount = amount
    }
}
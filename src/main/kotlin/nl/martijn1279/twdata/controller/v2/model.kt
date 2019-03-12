package nl.martijn1279.twdata.controller.v2

import com.fasterxml.jackson.annotation.JsonIgnore


data class World(val worldId: String, val worldName: String) {
    @JsonIgnore
    val tribes = arrayListOf<Tribe>()
    @JsonIgnore
    val players = arrayListOf<Player>()
    @JsonIgnore
    val villages = arrayListOf<Village>()
}

data class Tribe(
        val worldId: String,
        val tribeId: Int,
        val name: String,
        val tag: String,
        val membersCount: Int,
        val villages: Int,
        val points: Int,
        val allPoints: Int,
        val rank: Int) {
    @JsonIgnore
    val players = arrayListOf<Player>()

}

data class Player(val playerId: Int,
                  val name: String,
                  val tribeId: Int,
                  val points: Int,
                  val rank: Int) {
    @JsonIgnore
    val villages = arrayListOf<Village>()
}

data class Village(val villageId: Int,
                   val name: String,
                   val x: Short,
                   val y: Short,
                   val playerId: Int,
                   val points: Int,
                   val rank: Int)
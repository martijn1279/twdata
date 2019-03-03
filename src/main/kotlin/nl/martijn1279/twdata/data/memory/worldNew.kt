package nl.martijn1279.twdata.data.memory

import com.fasterxml.jackson.annotation.JsonIgnore


data class WorldNew(val worldId: String, val worldName: String) {
    @JsonIgnore
    val players = arrayListOf<PlayerNew>()
    @JsonIgnore
    val tribes = arrayListOf<TribeNew>()
    @JsonIgnore
    val villages = arrayListOf<VillageNew>()
}

data class PlayerNew(var playerId: Int,
                     var name: String,
                     var tribeId: Int,
                     var points: Int,
                     var rank: Int) {
    @JsonIgnore
    val villages = arrayListOf<VillageNew>()
}

data class TribeNew(
        var tribeId: Int,
        var name: String,
        var tag: String,
        var members: Int,
        var villages: Int,
        var points: Int,
        var allPoints: Int,
        var rank: Int) {

    @JsonIgnore
    val players = arrayListOf<PlayerNew>()
}

data class VillageNew(var villageId: Int,
                      var name: String,
                      var x: Short,
                      var y: Short,
                      var playerId: Int,
                      var points: Int,
                      var rank: Int)
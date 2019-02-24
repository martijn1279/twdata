package nl.martijn1279.twdata.data

import java.io.Serializable
import java.util.*
import javax.persistence.Column
import javax.persistence.Id

class PlayerPK : Serializable {
    @Column(name = "world_id", nullable = false, length = -1)
    @Id
    var worldId: String? = null
    @Column(name = "player_id", nullable = false)
    @Id
    var playerId: Int = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val playerPK = other as PlayerPK?
        return playerId == playerPK!!.playerId && worldId == playerPK.worldId
    }

    override fun hashCode(): Int {
        return Objects.hash(worldId, playerId)
    }
}

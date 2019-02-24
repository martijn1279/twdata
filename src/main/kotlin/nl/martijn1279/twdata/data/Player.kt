package nl.martijn1279.twdata.data

import java.util.*
import javax.persistence.*

@Entity
@IdClass(PlayerPK::class)
class Player(
        @Id
        @Column(name = "world_id", nullable = false, length = -1)
        var worldId: String,
        @Id
        @Column(name = "player_id", nullable = false)
        var playerId: Int,
        @Basic
        @Column(name = "name", nullable = false, length = 256)
        var name: String,
        @Basic
        @Column(name = "tribe_id", nullable = false)
        var tribeId: Int,
        @Basic
        @Column(name = "points", nullable = false)
        var points: Int,
        @Basic
        @Column(name = "rank", nullable = false)
        var rank: Int
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val player = other as Player?
        return playerId == player!!.playerId &&
                tribeId == player.tribeId &&
                points == player.points &&
                rank == player.rank &&
                worldId == player.worldId &&
                name == player.name
    }

    override fun hashCode(): Int {
        return Objects.hash(worldId, playerId, name, tribeId, points, rank)
    }
}

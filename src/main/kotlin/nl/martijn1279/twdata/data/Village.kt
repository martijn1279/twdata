package nl.martijn1279.twdata.data

import java.util.*
import javax.persistence.*

@Entity
@IdClass(VillagePK::class)
class Village(
        @Id
        @Column(name = "world_id", nullable = false, length = -1)
        var worldId: String,
        @Id
        @Column(name = "village_id", nullable = false)
        var villageId: Int,
        @Basic
        @Column(name = "name", nullable = false, length = 255)
        var name: String,
        @Basic
        @Column(name = "x", nullable = false)
        var x: Short,
        @Basic
        @Column(name = "y", nullable = false)
        var y: Short,
        @Basic
        @Column(name = "player_id", nullable = false)
        var playerId: Int,
        @Basic
        @Column(name = "points", nullable = false)
        var points: Int,
        @Basic
        @Column(name = "rank", nullable = false)
        var rank: Int
) {
    @ManyToOne
    @JoinColumns(
            JoinColumn(name = "player_id", referencedColumnName = "player_id", nullable = false, insertable = false, updatable = false),
            JoinColumn(name = "world_id", referencedColumnName = "world_id", nullable = false, insertable = false, updatable = false))

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val village = other as Village?
        return villageId == village!!.villageId &&
                x == village.x &&
                y == village.y &&
                playerId == village.playerId &&
                points == village.points &&
                rank == village.rank &&
                worldId == village.worldId &&
                name == village.name
    }

    override fun hashCode(): Int {
        return Objects.hash(worldId, villageId, name, x, y, points, rank)
    }
}

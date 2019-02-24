package nl.martijn1279.twdata.data

import java.util.*
import javax.persistence.*

@Entity
@IdClass(TribePK::class)
class Tribe(
        @Id
        @Column(name = "tribe_id", nullable = false)
        var tribeId: Int,
        @Basic
        @Column(name = "name", nullable = false, length = 255)
        var name: String,
        @Basic
        @Column(name = "tag", nullable = false, length = 10)
        var tag: String,
        @Basic
        @Column(name = "members", nullable = false)
        var members: Int,
        @Basic
        @Column(name = "villages", nullable = false)
        var villages: Int,
        @Basic
        @Column(name = "points", nullable = false)
        var points: Int,
        @Basic
        @Column(name = "all_points", nullable = false)
        var allPoints: Int,
        @Basic
        @Column(name = "rank", nullable = false)
        var rank: Int,
        @Id
        @Column(name = "world_id", nullable = false, length = -1)
        var worldId: String
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val tribe = other as Tribe?
        return tribeId == tribe!!.tribeId &&
                members == tribe.members &&
                villages == tribe.villages &&
                points == tribe.points &&
                allPoints == tribe.allPoints &&
                rank == tribe.rank &&
                name == tribe.name &&
                tag == tribe.tag &&
                worldId == tribe.worldId
    }

    override fun hashCode(): Int {
        return Objects.hash(tribeId, name, tag, members, villages, points, allPoints, rank, worldId)
    }
}

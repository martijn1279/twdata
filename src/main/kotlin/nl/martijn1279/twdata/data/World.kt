package nl.martijn1279.twdata.data

import java.util.*
import javax.persistence.Basic
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class World(
        @Id
        @Column(name = "world_id", nullable = false, length = -1)
        var worldId: String,
        @Basic
        @Column(name = "world_name", nullable = true, length = -1)
        var worldName: String) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val world = other as World?
        return worldId == world!!.worldId && worldName == world.worldName
    }

    override fun hashCode(): Int {
        return Objects.hash(worldId, worldName)
    }
}

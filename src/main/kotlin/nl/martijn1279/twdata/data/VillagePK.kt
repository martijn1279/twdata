package nl.martijn1279.twdata.data

import java.io.Serializable
import java.util.*
import javax.persistence.Column
import javax.persistence.Id

class VillagePK : Serializable {
    @Column(name = "world_id", nullable = false, length = -1)
    @Id
    var worldId: String? = null
    @Column(name = "village_id", nullable = false)
    @Id
    var villageId: Int = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val villagePK = other as VillagePK?
        return villageId == villagePK!!.villageId && worldId == villagePK.worldId
    }

    override fun hashCode(): Int {
        return Objects.hash(worldId, villageId)
    }
}

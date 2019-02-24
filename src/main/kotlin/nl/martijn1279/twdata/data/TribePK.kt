package nl.martijn1279.twdata.data

import java.io.Serializable
import java.util.*
import javax.persistence.Column
import javax.persistence.Id

class TribePK : Serializable {
    @Column(name = "tribe_id", nullable = false)
    @Id
    var tribeId: Int = 0
    @Column(name = "world_id", nullable = false, length = -1)
    @Id
    var worldId: String? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val tribePK = other as TribePK?
        return tribeId == tribePK!!.tribeId && worldId == tribePK.worldId
    }

    override fun hashCode(): Int {
        return Objects.hash(tribeId, worldId)
    }
}

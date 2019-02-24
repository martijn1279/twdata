package nl.martijn1279.twdata.data

import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "users", schema = "tribalwars_owner", catalog = "tribalwars")
class Users(
        @Id @Column(name = "id") var id: String,
        @Basic @Column(name = "ipadress", nullable = false, length = 45) var ipadress: String,
        @Basic @Column(name = "first_request", nullable = false) var firstRequest: Timestamp,
        @Basic @Column(name = "last_request", nullable = false) var lastRequest: Timestamp,
        @Basic @Column(name = "request_counter", nullable = false) var requestCounter: Int = 1
) {


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false

        val users = other as Users?

        if (id != users!!.id) return false
        if (ipadress != users.ipadress) return false
        if (!firstRequest.equals(users.firstRequest)) return false
        if (requestCounter != users.requestCounter) return false
        return lastRequest.equals(users.lastRequest)

    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + ipadress.hashCode()
        result = 31 * result + firstRequest.hashCode()
        result = 31 * result + lastRequest.hashCode()
        result = 31 * result + requestCounter.hashCode()
        return result
    }
}

package nl.martijn1279.twdata.data

import nl.martijn1279.twdata.error.ErrorCode
import nl.martijn1279.twdata.error.ServiceException
import org.springframework.data.repository.CrudRepository
import java.util.*

interface WorldCrudRepository : CrudRepository<World, String> {
    fun findWorldByWorldName(worldName: String): Optional<World>
}

interface PlayerCrudRepository : CrudRepository<Player, Int> {
    fun findPlayersByWorldId(worldId: String): Iterable<Player>
    fun findPlayerByWorldIdAndPlayerId(worldId: String, playerId: Int): Optional<Player>
    fun findPlayerByWorldIdAndName(worldId: String, name: String): Optional<Player>
}

interface TribeCrudRepository : CrudRepository<Tribe, Int> {
    fun findTribesByWorldId(worldId: String): Iterable<Tribe>
    fun findTribeByWorldIdAndTribeId(worldId: String, tribeId: Int): Optional<Tribe>
    fun findTribeByWorldIdAndTag(worldId: String, tag: String): Optional<Tribe>
    fun findTribeByWorldIdAndName(worldId: String, name: String): Optional<Tribe>
}

interface VillageCrudRepository : CrudRepository<Village, Int> {
    fun findVillagesByWorldIdAndPlayerId(worldId: String, playerId: Int): Iterable<Village>
    fun findVillageByWorldIdAndVillageId(worldId: String, villageId: Int): Optional<Village>
    fun findVillageByWorldIdAndName(worldId: String, name: String): Optional<Village>
    fun findVillageByWorldIdAndXAndY(worldId: String, x: Int, y: Int): Optional<Village>
}

fun <E> Optional<E>.orNotFound(): E {
    if (this.isPresent) return this.get()
    throw ServiceException(ErrorCode.NON_FOUND)
}

fun <E> E?.orNotFound(): E {
    return this ?: throw ServiceException(ErrorCode.NON_FOUND)
}

fun <E> List<E>.orNotFound(): List<E> {
    if (this.isEmpty()) throw ServiceException(ErrorCode.NON_FOUND)
    return this
}
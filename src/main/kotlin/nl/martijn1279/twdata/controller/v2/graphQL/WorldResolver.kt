package nl.martijn1279.twdata.controller.v2.graphQL

import com.coxautodev.graphql.tools.GraphQLResolver
import nl.martijn1279.twdata.controller.v2.Player
import nl.martijn1279.twdata.controller.v2.Tribe
import nl.martijn1279.twdata.controller.v2.Village
import nl.martijn1279.twdata.controller.v2.World
import org.springframework.stereotype.Component

@Component
class WorldResolver : GraphQLResolver<World> {
    fun tribes(world: World): List<Tribe> = world.tribes.toList()
    fun tribe(world: World, tribeId: Int?, name: String?, tag: String?, membersCount: Int?, villages: Int?, points: Int?, allPoints: Int?, rank: Int?): Tribe? =
            world.tribes.toList()
                    .let { tribes ->
                        var tmp = tribes
                        tribeId?.let { value -> tmp = tmp.filter { it.tribeId == value } }
                        name?.let { value -> tmp = tmp.filter { it.name == value } }
                        tag?.let { value -> tmp = tmp.filter { it.tag == value } }
                        membersCount?.let { value -> tmp = tmp.filter { it.membersCount == value } }
                        villages?.let { value -> tmp = tmp.filter { it.villages == value } }
                        points?.let { value -> tmp = tmp.filter { it.points == value } }
                        allPoints?.let { value -> tmp = tmp.filter { it.allPoints == value } }
                        rank?.let { value -> tmp = tmp.filter { it.rank == value } }
                        return tmp.firstOrNull()
                    }

    fun players(world: World): List<Player> = world.players.toList()
    fun player(world: World, playerId: Int?, name: String?, tribeId: Int?, points: Int?, rank: Int?): Player? =
            world.players.toList()
                    .let { players ->
                        var tmp = players
                        playerId?.let { value -> tmp = tmp.filter { it.playerId == value } }
                        name?.let { value -> tmp = tmp.filter { it.name == value } }
                        tribeId?.let { value -> tmp = tmp.filter { it.tribeId == value } }
                        points?.let { value -> tmp = tmp.filter { it.points == value } }
                        rank?.let { value -> tmp = tmp.filter { it.tribeId == value } }
                        return tmp.firstOrNull()
                    }

    fun villages(world: World): List<Village> = world.villages
    fun village(world: World, villageId: Int?, name: String?, x: Int?, y: Int?, playerId: Int?, points: Int?, rank: Int?): Village? =
            world.villages.toList()
                    .let { villages ->
                        var tmp = villages
                        villageId?.let { value -> tmp = tmp.filter { it.villageId == value } }
                        name?.let { value -> tmp = tmp.filter { it.name == value } }
                        x?.let { value -> tmp = tmp.filter { it.x == value.toShort() } }
                        y?.let { value -> tmp = tmp.filter { it.y == value.toShort() } }
                        playerId?.let { value -> tmp = tmp.filter { it.playerId == value } }
                        points?.let { value -> tmp = tmp.filter { it.points == value } }
                        rank?.let { value -> tmp = tmp.filter { it.rank == value } }
                        return tmp.firstOrNull()
                    }
}
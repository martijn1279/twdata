package nl.martijn1279.twdata.controller.v2.graphQL

import com.coxautodev.graphql.tools.GraphQLResolver
import nl.martijn1279.twdata.controller.v2.Player
import nl.martijn1279.twdata.controller.v2.Village
import org.springframework.stereotype.Component

@Component
class PlayerResolver : GraphQLResolver<Player> {
    fun villages(player: Player): List<Village> = player.villages
    fun village(player: Player, villageId: Int?, name: String?, x: Int?, y: Int?, playerId: Int?, points: Int?, rank: Int?): Village? =
            player.villages.toList()
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
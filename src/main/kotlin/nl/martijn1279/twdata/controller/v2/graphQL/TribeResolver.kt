package nl.martijn1279.twdata.controller.v2.graphQL

import com.coxautodev.graphql.tools.GraphQLResolver
import nl.martijn1279.twdata.controller.v2.Player
import nl.martijn1279.twdata.controller.v2.Tribe
import org.springframework.stereotype.Component

@Component
class TribeResolver : GraphQLResolver<Tribe> {
    fun players(tribe: Tribe): List<Player> = tribe.players.toList()
    fun player(tribe: Tribe, playerId: Int?, name: String?, tribeId: Int?, points: Int?, rank: Int?): Player? =
            tribe.players.toList()
                    .let { players ->
                        var tmp = players
                        playerId?.let { value -> tmp = tmp.filter { it.playerId == value } }
                        name?.let { value -> tmp = tmp.filter { it.name == value } }
                        tribeId?.let { value -> tmp = tmp.filter { it.tribeId == value } }
                        points?.let { value -> tmp = tmp.filter { it.points == value } }
                        rank?.let { value -> tmp = tmp.filter { it.tribeId == value } }
                        return tmp.firstOrNull()
                    }
}
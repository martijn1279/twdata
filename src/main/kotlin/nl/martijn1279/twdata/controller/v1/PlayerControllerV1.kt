package nl.martijn1279.twdata.controller.v1

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import nl.martijn1279.twdata.data.Player
import nl.martijn1279.twdata.data.PlayerCrudRepository
import nl.martijn1279.twdata.data.orNotFound
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import javax.ws.rs.core.MediaType

@RestController
@RequestMapping("/v1/player")
@Api(description = "Operations pertaining to players")
class PlayerControllerV1(@Autowired private val playerCrudRepository: PlayerCrudRepository) {


    @ApiOperation(value = "Get a list of all the players with the given world")
    @ApiResponses(ApiResponse(code = 404, message = "No information was found for the request"))
    @RequestMapping(value = ["/getPlayersByWorldId/{worldId}"], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON])
    fun getPlayersByWorldId(
            @PathVariable worldId: String
    ): List<Player> =
            playerCrudRepository.findPlayersByWorldId(worldId).toList().orNotFound()


    @ApiOperation(value = "Get a player by given 'worldId' and 'playerId'")
    @ApiResponses(ApiResponse(code = 404, message = "No information was found for the request"))
    @RequestMapping(value = ["/getPlayerByWorldIdAndPlayerId/{worldId}/{playerId}"], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON])
    fun getPlayerByWorldIdAndPlayerId(
            @PathVariable worldId: String,
            @PathVariable playerId: Int
    ): Player =
            playerCrudRepository.findPlayerByWorldIdAndPlayerId(worldId, playerId).orNotFound()


    @ApiOperation(value = "Get a player by given 'worldId' and 'playerName'")
    @ApiResponses(ApiResponse(code = 404, message = "No information was found for the request"))
    @RequestMapping(value = ["/getPLayerByWorldIdAndPlayerName/{worldId}/{playerName}"], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON])
    fun getPLayerByWorldIdAndPlayerName(
            @PathVariable worldId: String,
            @PathVariable playerName: String
    ): Player =
            playerCrudRepository.findPlayerByWorldIdAndName(worldId, playerName).orNotFound()


}
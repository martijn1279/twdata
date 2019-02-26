package nl.martijn1279.twdata.controller.v2

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import nl.martijn1279.twdata.data.memory.PlayerNew
import nl.martijn1279.twdata.data.orNotFound
import nl.martijn1279.twdata.error.ErrorCode
import nl.martijn1279.twdata.error.ServiceException
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import javax.ws.rs.core.MediaType

@RestController
@RequestMapping("/v2/player")
@Api(description = "Operations pertaining to players")
class PlayerControllerV2() {

    @ApiOperation(value = "Get a list of all the players with the given world")
    @ApiResponses(ApiResponse(code = 404, message = "No information was found for the request"))
    @RequestMapping(value = ["/getPlayersByWordlId/{worldId}"], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON])
    fun getPlayersByWordlId(
            @PathVariable worldId: String
    ): List<PlayerNew> =
            SyncWorldDataMemory.worlds
                    .find { it.worldId == worldId }
                    ?.players
                    ?.orNotFound()
                    ?: throw ServiceException(ErrorCode.NON_FOUND)

    @ApiOperation(value = "Get a player by given 'worldId' and 'playerId'")
    @ApiResponses(ApiResponse(code = 404, message = "No information was found for the request"))
    @RequestMapping(value = ["/getPlayerByWorldIdAndPlayerId/{worldId}/{playerId}"], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON])
    fun getPlayerByWorldIdAndPlayerId(
            @PathVariable worldId: String,
            @PathVariable playerId: Int
    ): PlayerNew? =
            SyncWorldDataMemory.worlds
                    .find { it.worldId == worldId }
                    ?.players
                    ?.find { it.playerId == playerId }
                    ?: throw ServiceException(ErrorCode.NON_FOUND)


    @ApiOperation(value = "Get a player by given 'worldId' and 'playerName'")
    @ApiResponses(ApiResponse(code = 404, message = "No information was found for the request"))
    @RequestMapping(value = ["/getPLayerByWorldIdAndPlayerName/{worldId}/{playerName}"], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON])
    fun getPLayerByWorldIdAndPlayerName(
            @PathVariable worldId: String,
            @PathVariable playerName: String
    ): PlayerNew =
            SyncWorldDataMemory.worlds
                    .find { it.worldId == worldId }
                    ?.players
                    ?.find { it.name == playerName }
                    ?: throw ServiceException(ErrorCode.NON_FOUND)
}

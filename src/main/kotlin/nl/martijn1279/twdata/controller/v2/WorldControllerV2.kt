package nl.martijn1279.twdata.controller.v2

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import nl.martijn1279.twdata.data.memory.WorldNew
import nl.martijn1279.twdata.data.orNotFound
import nl.martijn1279.twdata.error.ErrorCode
import nl.martijn1279.twdata.error.ServiceException
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import javax.ws.rs.core.MediaType

@RestController
@RequestMapping("/v2/world")
@Api(description = "Operations pertaining to worlds")
class WorldControllerV2 {


    @ApiOperation(value = "get a list of all the worlds")
    @ApiResponses(ApiResponse(code = 404, message = "No information was found for the request"))
    @RequestMapping(value = ["/getWorlds"], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON])
    fun getWorlds(): List<WorldNew> =
            SyncWorldDataMemory.worlds.orNotFound()


    @ApiOperation(value = "Get a world by given 'worldId'")
    @ApiResponses(ApiResponse(code = 404, message = "No information was found for the request"))
    @RequestMapping(value = ["/getWorldByWorldId/{worldId}"], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON])
    fun getWorldByWorldId(
            @PathVariable worldId: String
    ): WorldNew =
            SyncWorldDataMemory.worlds
                    .find { it.worldId == worldId }
                    ?: throw ServiceException(ErrorCode.NON_FOUND)


    @ApiOperation(value = "Get a world by given 'worldName'")
    @ApiResponses(ApiResponse(code = 404, message = "No information was found for the request"))
    @RequestMapping(value = ["/getWorldByWorldName/{worldName}"], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON])
    fun getWorldByWorldName(
            @PathVariable worldName: String
    ): WorldNew =
            SyncWorldDataMemory.worlds
                    .find { it.worldName == worldName }
                    ?: throw ServiceException(ErrorCode.NON_FOUND)


}
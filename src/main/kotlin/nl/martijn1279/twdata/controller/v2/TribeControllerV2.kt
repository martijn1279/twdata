package nl.martijn1279.twdata.controller.v2

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import nl.martijn1279.twdata.data.TribeCrudRepository
import nl.martijn1279.twdata.data.memory.TribeNew
import nl.martijn1279.twdata.data.orNotFound
import nl.martijn1279.twdata.error.ErrorCode
import nl.martijn1279.twdata.error.ServiceException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import javax.ws.rs.core.MediaType

@RestController
@RequestMapping("/v2/tribe")
@Api(description = "Operations pertaining to tribes")
class TribeControllerV2(@Autowired private val tribeCrudRepository: TribeCrudRepository) {


    @ApiOperation(value = "Get a list of all the tribe with the given world")
    @ApiResponses(ApiResponse(code = 404, message = "No information was found for the request"))
    @RequestMapping(value = ["/getTribesByWorldId/{worldId}"], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON])
    fun getTribesByWorldId(
            @PathVariable worldId: String
    ): List<TribeNew> =
            SyncWorldDataMemory.worlds
                    .find { it.worldId == worldId }
                    ?.tribes
                    ?.orNotFound()
                    ?: throw ServiceException(ErrorCode.NON_FOUND)

    @ApiOperation(value = "Get a tribe by given 'worldId' and 'tribeId'")
    @ApiResponses(ApiResponse(code = 404, message = "No information was found for the request"))
    @RequestMapping(value = ["/getTribeByWorldIdAndTribeId/{worldId}/{tribeId}"], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON])
    fun getTribeByWorldIdAndTribeId(
            @PathVariable worldId: String,
            @PathVariable tribeId: Int
    ): TribeNew =
            SyncWorldDataMemory.worlds
                    .find { it.worldId == worldId }
                    ?.tribes
                    ?.find { it.tribeId == tribeId }
                    ?: throw ServiceException(ErrorCode.NON_FOUND)

    @ApiOperation(value = "Get a tribe by given 'worldId' and 'tag'")
    @ApiResponses(ApiResponse(code = 404, message = "No information was found for the request"))
    @RequestMapping(value = ["/getTribeByWorldIdAndTag/{worldId}/{tag}"], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON])
    fun getTribeByWorldIdAndTag(
            @PathVariable worldId: String,
            @PathVariable tag: String
    ): TribeNew =
            SyncWorldDataMemory.worlds
                    .find { it.worldId == worldId }
                    ?.tribes
                    ?.find { it.tag == tag }
                    ?: throw ServiceException(ErrorCode.NON_FOUND)

    @ApiOperation(value = "Get a tribe by given 'worldId' and 'name'")
    @ApiResponses(ApiResponse(code = 404, message = "No information was found for the request"))
    @RequestMapping(value = ["/getTribeByWorldIdAndName/{worldId}/{name}"], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON])
    fun getTribeByWorldIdAndName(
            @PathVariable worldId: String,
            @PathVariable name: String
    ): TribeNew =
            SyncWorldDataMemory.worlds
                    .find { it.worldId == worldId }
                    ?.tribes
                    ?.find { it.name == name }
                    ?: throw ServiceException(ErrorCode.NON_FOUND)
}
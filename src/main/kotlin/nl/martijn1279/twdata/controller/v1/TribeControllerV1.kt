package nl.martijn1279.twdata.controller.v1

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import nl.martijn1279.twdata.data.Tribe
import nl.martijn1279.twdata.data.TribeCrudRepository
import nl.martijn1279.twdata.data.orNotFound
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import javax.ws.rs.core.MediaType

@RestController
@RequestMapping("/v1/tribe")
@Api(description = "Operations pertaining to tribes")
class TribeControllerV1(@Autowired private val tribeCrudRepository: TribeCrudRepository) {


    @ApiOperation(value = "Get a list of all the tribe with the given world")
    @ApiResponses(ApiResponse(code = 404, message = "No information was found for the request"))
    @RequestMapping(value = ["/getTribesByWorldId/{worldId}"], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON])
    fun getTribesByWorldId(
            @PathVariable worldId: String
    ): List<Tribe> =
            tribeCrudRepository.findTribesByWorldId(worldId).toList().orNotFound()


    @ApiOperation(value = "Get a tribe by given 'worldId' and 'tribeId'")
    @ApiResponses(ApiResponse(code = 404, message = "No information was found for the request"))
    @RequestMapping(value = ["/getTribeByWorldIdAndTribeId/{worldId}/{tribeId}"], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON])
    fun getTribeByWorldIdAndTribeId(
            @PathVariable worldId: String,
            @PathVariable tribeId: Int
    ): Tribe =
            tribeCrudRepository.findTribeByWorldIdAndTribeId(worldId, tribeId).orNotFound()


    @ApiOperation(value = "Get a tribe by given 'worldId' and 'tag'")
    @ApiResponses(ApiResponse(code = 404, message = "No information was found for the request"))
    @RequestMapping(value = ["/getTribeByWorldIdAndTag/{worldId}/{tag}"], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON])
    fun getTribeByWorldIdAndTag(
            @PathVariable worldId: String,
            @PathVariable tag: String
    ): Tribe =
            tribeCrudRepository.findTribeByWorldIdAndTag(worldId, tag).orNotFound()

    @ApiOperation(value = "Get a tribe by given 'worldId' and 'name'")
    @ApiResponses(ApiResponse(code = 404, message = "No information was found for the request"))
    @RequestMapping(value = ["/getTribeByWorldIdAndName/{worldId}/{name}"], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON])
    fun getTribeByWorldIdAndName(
            @PathVariable worldId: String,
            @PathVariable name: String
    ): Tribe =
            tribeCrudRepository.findTribeByWorldIdAndName(worldId, name).orNotFound()
}
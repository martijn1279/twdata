package nl.martijn1279.twdata.controller.v1

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import nl.martijn1279.twdata.data.Village
import nl.martijn1279.twdata.data.VillageCrudRepository
import nl.martijn1279.twdata.data.orNotFound
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import javax.ws.rs.core.MediaType

@RestController
@RequestMapping("/v1/village")
@Api(description = "Operations pertaining to villages")
class VillageControllerV1(@Autowired private val villageCrudRepository: VillageCrudRepository) {


    @ApiOperation(value = "Get a list of all the villages with the given 'worldId' and 'playerId'")
    @ApiResponses(ApiResponse(code = 404, message = "No information was found for the request"))
    @RequestMapping(value = ["/getVillagesByWorldIdAndPlayerId/{worldId}/{playerId}"], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON])
    fun getVillagesByWorldIdAndPlayerId(
            @PathVariable worldId: String,
            @PathVariable playerId: Int
    ): List<Village>? =
            villageCrudRepository.findVillagesByWorldIdAndPlayerId(worldId, playerId).toList().orNotFound()


    @ApiOperation(value = "Get a village by given 'worldId' and 'villageId'")
    @ApiResponses(ApiResponse(code = 404, message = "No information was found for the request"))
    @RequestMapping(value = ["/getVillageByWorldIdAndVillageId/{worldId}/{villageId}"], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON])
    fun getVillageByWorldIdAndVillageId(
            @PathVariable worldId: String,
            @PathVariable villageId: Int
    ): Village? =
            villageCrudRepository.findVillageByWorldIdAndVillageId(worldId, villageId).orNotFound()


    @ApiOperation(value = "Get a village by given 'worldId' and 'name'")
    @ApiResponses(ApiResponse(code = 404, message = "No information was found for the request"))
    @RequestMapping(value = ["/getVillageByWorldIdAndName/{worldId}/{name}"], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON])
    fun getVillageByWorldIdAndName(
            @PathVariable worldId: String,
            @PathVariable name: String
    ): Village? =
            villageCrudRepository.findVillageByWorldIdAndName(worldId, name).orNotFound()

    @ApiOperation(value = "Get a village by given 'worldId' and 'x' and 'y'")
    @ApiResponses(ApiResponse(code = 404, message = "No information was found for the request"))
    @RequestMapping(value = ["/getVillageByWorldIdAndXAndY/{worldId}/{x}/y}"], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON])
    fun getVillageByWorldIdAndXAndY(
            @PathVariable worldId: String,
            @PathVariable x: Int,
            @PathVariable y: Int
    ): Village? =
            villageCrudRepository.findVillageByWorldIdAndXAndY(worldId, x, y).orNotFound()
}
package nl.martijn1279.twdata.controller.v1

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import nl.martijn1279.twdata.data.World
import nl.martijn1279.twdata.data.WorldCrudRepository
import nl.martijn1279.twdata.data.orNotFound
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import javax.ws.rs.core.MediaType

@RestController
@RequestMapping("/v1/world")
@Api(description = "Operations pertaining to worlds")
class WorldControllerV1(@Autowired private val worldCrudRepository: WorldCrudRepository) {


    @ApiOperation(value = "get a list of all the worlds")
    @ApiResponses(ApiResponse(code = 404, message = "No information was found for the request"))
    @RequestMapping(value = ["/getWorlds"], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON])
    fun getWorlds(): List<World> =
            worldCrudRepository.findAll().toList().orNotFound()


    @ApiOperation(value = "Get a world by given 'worldId'")
    @ApiResponses(ApiResponse(code = 404, message = "No information was found for the request"))
    @RequestMapping(value = ["/getWorldByWorldId/{worldId}"], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON])
    fun getWorldByWorldId(
            @PathVariable worldId: String
    ): World =
            worldCrudRepository.findById(worldId).orNotFound()


    @ApiOperation(value = "Get a world by given 'worldName'")
    @ApiResponses(ApiResponse(code = 404, message = "No information was found for the request"))
    @RequestMapping(value = ["/getWorldByWorldName/{worldName}"], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON])
    fun getWorldByWorldName(
            @PathVariable worldName: String
    ): World = worldCrudRepository.findWorldByWorldName(worldName).orNotFound()

}
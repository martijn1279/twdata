package nl.martijn1279.twdata.controller.v3

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import nl.martijn1279.twdata.controller.v2.SyncWorldDataMemory
import nl.martijn1279.twdata.data.memory.WorldNew
import nl.martijn1279.twdata.data.orNotFound

class Query : GraphQLQueryResolver {

    fun getWorlds(): List<WorldNew> =
            SyncWorldDataMemory.worlds.orNotFound()
}
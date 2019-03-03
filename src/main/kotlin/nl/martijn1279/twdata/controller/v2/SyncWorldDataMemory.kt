package nl.martijn1279.twdata.controller.v2

import nl.martijn1279.twdata.data.memory.PlayerNew
import nl.martijn1279.twdata.data.memory.TribeNew
import nl.martijn1279.twdata.data.memory.VillageNew
import nl.martijn1279.twdata.data.memory.WorldNew
import nl.martijn1279.twdata.error.ErrorCode
import nl.martijn1279.twdata.error.ServiceException
import org.jsoup.Jsoup
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import java.net.URLDecoder
import java.util.*
import kotlin.collections.ArrayList

@Component
@RestController
@RequestMapping("/v2/syncWorldDataDB")
class SyncWorldDataMemory {

    companion object {
        private val LOG = LoggerFactory.getLogger(SyncWorldDataMemory::class.java)
        val worlds = arrayListOf<WorldNew>()
    }

    @Scheduled(fixedRate = 3600000)
    @RequestMapping(value = ["/syncTribalwarsInfo"], method = [RequestMethod.GET])
    fun syncTribalwarsInfo() {
        LOG.info("Begin sync world data")
        val tmp = getAllActiveWorlds().map { WorldNew(it.key, it.value) }
        tmp.forEach { world ->
            syncTribeInfo(world)
            syncPlayerInfo(world)
            syncVillageInfo(world)
        }
        worlds.clear()
        worlds.addAll(tmp.asIterable())
        System.gc()
        LOG.info("Finished sync world data")
    }

    private fun syncTribeInfo(world: WorldNew) {
        "https://${world.worldId}.tribalwars.nl/map/ally.txt"
                .getData()
                .let { data ->
                    val tmpList = arrayListOf(TribeNew(0,"No tribe","NON",0,0,0,0,0))
                    data.forEach {
                        tmpList.add(TribeNew(it[0].toInt(), it[1], it[2], it[3].toInt(), it[4].toInt(), it[5].toInt(), it[6].toInt(), it[7].toInt()))
                    }
                    world.tribes.clear()
                    world.tribes.addAll(tmpList.asIterable())
                }
        LOG.info("Synced tribeinfo: ${world.worldId}")
    }

    private fun syncPlayerInfo(world: WorldNew) {
        "https://${world.worldId}.tribalwars.nl/map/player.txt"
                .getData()
                .let { data ->
                    val tmpList = arrayListOf(PlayerNew(0, "BarBaar", 0, 0, 0))
                    data.forEach { p ->
                        PlayerNew(p[0].toInt(), p[1], p[2].toInt(), p[4].toInt(), p[5].toInt()).also { player ->
                            world.tribes.find { it.tribeId == p[2].toInt() }
                                    ?.players?.add(player)
                                    ?: error("")
                            tmpList.add(player)
                        }
                    }
                    world.players.clear()
                    world.players.addAll(tmpList.asIterable())
                }
        LOG.info("Synced playerinfo: ${world.worldId}")
    }

    private fun syncVillageInfo(world: WorldNew) {
        "https://${world.worldId}.tribalwars.nl/map/village.txt"
                .getData()
                .let { data ->
                    val tmp = arrayListOf<VillageNew>()
                    data.forEach { v ->
                        VillageNew(v[0].toInt(), v[1], v[2].toShort(), v[3].toShort(), v[4].toInt(), v[5].toInt(), v[6].toInt()).also { village ->
                            world.players.find { it.playerId == v[4].toInt() }
                                    ?.villages?.add(village)
                                    ?: error("")
                            tmp.add(village)
                        }
                    }
                    world.villages.clear()
                    world.villages.addAll(tmp.asIterable())
                }
        LOG.info("Synced villageinfo: ${world.worldId}")
    }

    private fun getAllActiveWorlds(): HashMap<String, String> {
        val worlds = hashMapOf<String, String>()
        val doc = Jsoup.connect("http://nl.twstats.com/").userAgent("Mozilla").get()
        doc.getElementsByClass("world")
                .filter { !it.parent().parent().text().contains("closed") }
                .forEach {
                    worlds[it.text().toLowerCase()] = it.parent().parent().child(1).child(0).text()
                }
        return worlds
    }

    private fun String.getData() = RestTemplate().getForEntity(this, String::class.java).body
            ?.split()
            ?: throw ServiceException(ErrorCode.UNKNOWN_ERROR)


    private fun String.split(): ArrayList<List<String>> {
        val newLine = "\\n"
        val cvsSplitBy = ","
        val list = ArrayList<List<String>>()
        this.split(newLine.toRegex())
                .dropLastWhile { it.isEmpty() }
                .toTypedArray()
                .forEach { row ->
                    list.add(row.split(cvsSplitBy
                            .toRegex())
                            .dropLastWhile { it.isEmpty() }
                            .toTypedArray()
                            .map { URLDecoder.decode(it, "UTF-8") })
                }
        return list
    }
}
package nl.martijn1279.twdata.controller.v2

import org.jsoup.Jsoup
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.net.URLDecoder
import java.util.*
import kotlin.collections.ArrayList

@Component
class SyncWorldDataMemory {

    companion object {
        private val LOG = LoggerFactory.getLogger(SyncWorldDataMemory::class.java)
        val worlds = arrayListOf<World>()
    }

    @Scheduled(fixedRate = 3600000)
    private fun syncTribalwarsInfo() {
        LOG.info("Begin sync world data")
        val tmp = getAllActiveWorlds().map { World(it.key, it.value) }
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

    private fun syncTribeInfo(world: World) {
        "https://${world.worldId}.tribalwars.nl/map/ally.txt"
                .getData()
                .let { data ->
                    val tmpList = arrayListOf(Tribe(world.worldId, 0, "No tribe", "NON", 0, 0, 0, 0, 0))
                    data.forEach {
                        tmpList.add(Tribe(world.worldId, it[0].toInt(), it[1], it[2], it[3].toInt(), it[4].toInt(), it[5].toInt(), it[6].toInt(), it[7].toInt()))
                    }
                    world.tribes.clear()
                    world.tribes.addAll(tmpList.asIterable())
                }
        LOG.info("Synced tribeinfo: ${world.worldId}")
    }

    private fun syncPlayerInfo(world: World) {
        "https://${world.worldId}.tribalwars.nl/map/player.txt"
                .getData()
                .let { data ->
                    val tmpList = arrayListOf(Player(0, "BarBaar", 0, 0, 0))
                    data.forEach { p ->
                        Player(p[0].toInt(), p[1], p[2].toInt(), p[4].toInt(), p[5].toInt()).also { player ->
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

    private fun syncVillageInfo(world: World) {
        "https://${world.worldId}.tribalwars.nl/map/village.txt"
                .getData()
                .let { data ->
                    val tmp = arrayListOf<Village>()
                    data.forEach { v ->
                        Village(v[0].toInt(), v[1], v[2].toShort(), v[3].toShort(), v[4].toInt(), v[5].toInt(), v[6].toInt()).also { village ->
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
            ?: emptyList<List<String>>()

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
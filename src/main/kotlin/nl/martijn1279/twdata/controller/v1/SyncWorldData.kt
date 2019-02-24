package nl.martijn1279.twdata.controller.v1

import nl.martijn1279.twdata.data.*
import nl.martijn1279.twdata.error.ErrorCode
import nl.martijn1279.twdata.error.ServiceException
import org.jsoup.Jsoup
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.net.URLDecoder
import java.util.*
import kotlin.collections.ArrayList


@Component
class SyncWorldData(@Autowired private val worldRepository: WorldCrudRepository,
                    @Autowired private val playerRepository: PlayerCrudRepository,
                    @Autowired private val tribeRepository: TribeCrudRepository,
                    @Autowired private val villageRepository: VillageCrudRepository) {

    companion object {
        private val LOG = LoggerFactory.getLogger(SyncWorldData::class.java)
    }

    @Scheduled(fixedRate = 3600000)
    fun syncTribalwarsInfo() {
        val worlds = getAllActiveWorlds()
        syncWorld(worlds)
        worlds.forEach {
            syncTribeInfo(it.key)
            syncPlayerInfo(it.key)
            syncVillageInfo(it.key)
        }
    }

    private fun syncWorld(activeWorlds: HashMap<String, String>) {
        val savedWorlds = worldRepository.findAll().toList().map { it.worldId }
        savedWorlds.forEach {
            if (it !in activeWorlds.keys) worldRepository.deleteById(it)
        }
        activeWorlds.forEach {
            worldRepository.save(World(it.key, it.value))
        }
        LOG.info("Synced active worlds")
    }

    private fun syncTribeInfo(world: String) {
        tribeRepository.findTribeByWorldIdAndTribeId(world, 0).takeIf { it.isPresent }
                ?: tribeRepository.save(Tribe(0, "NO TRIBE", "NON", 0, 0, 0, 0, 0, world))
        "https://$world.tribalwars.nl/map/ally.txt".getData().forEach {
            tribeRepository.save(Tribe(it[0].toInt(), it[1], it[2], it[3].toInt(), it[4].toInt(), it[5].toInt(), it[6].toInt(), it[7].toInt(), world))
        }
        LOG.info("Synced tribeinfo: $world")
    }

    private fun syncPlayerInfo(world: String) {
        "https://$world.tribalwars.nl/map/player.txt".getData().forEach {
            playerRepository.save(Player(world, it[0].toInt(), it[1], it[2].toInt(), it[4].toInt(), it[5].toInt()))
        }
        LOG.info("Synced playerinfo: $world")
    }

    private fun syncVillageInfo(world: String) {
        "https://$world.tribalwars.nl/map/village.txt".getData().forEach {
            if (it[4].toInt() != 0) {
                val village = Village(world, it[0].toInt(), it[1], it[2].toShort(), it[3].toShort(), it[4].toInt(), it[5].toInt(), it[6].toInt())
                        .also { player -> player.player = playerRepository.findPlayerByWorldIdAndPlayerId(world, it[4].toInt()).orNotFound() }
                villageRepository.save(village)
            }
        }
        LOG.info("Synced villageinfo: $world")
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
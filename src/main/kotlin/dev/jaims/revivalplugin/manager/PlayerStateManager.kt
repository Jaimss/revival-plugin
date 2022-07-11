package dev.jaims.revivalplugin.manager

import dev.jaims.revivalplugin.RevivalPlugin
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

class UUIDStateManager(private val plugin: RevivalPlugin) {

    init {
        startRevivalTimer()
    }

    private val UUID.bukkitPlayer: Player?
        get() = Bukkit.getPlayer(this)

    private val revivablePlayers: MutableMap<UUID, Long> = mutableMapOf()
    private val deadPlayers: MutableMap<UUID, Long> = mutableMapOf()

    fun setAlive(uuid: UUID) {
        if (uuid !in revivablePlayers.keys) {
            plugin.logger.severe("Tried to set a player to ALIVE that is not currently revivable or is dead!")
            return
        }
        revivablePlayers.remove(uuid)
    }

    /**
     * Set a [uuid] to the state of being able to be revived
     */
    fun setRevivable(uuid: UUID) {
        if (uuid in revivablePlayers.keys || uuid in deadPlayers) {
            plugin.logger.severe("Tried to set a player to REVIVABLE that is not currently alive or is currently dead.")
            return
        }
        // add the player to the list of revivable
        revivablePlayers[uuid] = System.currentTimeMillis()
    }

    /**
     * Set a [uuid] to completely dead.
     */
    private fun setDead(uuid: UUID) {
        if (uuid !in revivablePlayers.keys) {
            plugin.logger.severe("Tried to set a player to DEAD that is not currently revivable!")
            return
        }
        revivablePlayers.remove(uuid)
        deadPlayers[uuid] = System.currentTimeMillis()
    }

    private fun startRevivalTimer() {
        plugin.server.scheduler.scheduleSyncRepeatingTask(plugin, {
            val currTime = System.currentTimeMillis()
            // handle moving players from the revivable state to the dead state
            revivablePlayers
                // only take players where the time is greater than
                .filter { (_, deathTime) -> (currTime - deathTime) / 1000 >= 60 }
                // move all these people to dead
                .forEach { (uuid, _) ->
                    setDead(uuid)
                }
        }, 0, 20L)
    }

}
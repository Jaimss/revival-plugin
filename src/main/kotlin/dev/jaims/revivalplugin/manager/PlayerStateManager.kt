package dev.jaims.revivalplugin.manager

import dev.jaims.revivalplugin.RevivalPlugin
import dev.jaims.revivalplugin.const.PlayerState
import dev.jaims.revivalplugin.func.bukkitPlayer
import org.bukkit.GameMode
import org.bukkit.entity.ArmorStand
import java.util.*

class PlayerStateManager(private val plugin: RevivalPlugin) {

    init {
        startRevivalTimer()
    }

    private val revivablePlayers: MutableMap<UUID, Long> = mutableMapOf()
    private val deadPlayers: MutableMap<UUID, Long> = mutableMapOf()

    /**
     * @return the [PlayerState] of [uuid]
     */
    fun getState(uuid: UUID): PlayerState {
        if (uuid in revivablePlayers.keys && uuid in deadPlayers.keys) {
            error("Player cannot be two states at once!")
        }
        return when (uuid) {
            in revivablePlayers.keys -> PlayerState.REVIVABLE
            in deadPlayers.keys -> PlayerState.DEAD
            else -> PlayerState.ALIVE
        }
    }

    /**
     * Set a [uuid] as alive status again if they were revived
     * ONLY call this if the [uuid] is revivable
     */
    fun setAlive(uuid: UUID) {
        if (uuid !in revivablePlayers.keys) {
            plugin.logger.severe("Tried to set a player to ALIVE that is not currently revivable or is dead!")
            return
        }
        revivablePlayers.remove(uuid)
        uuid.bukkitPlayer?.let { player ->
            // clear effects
            plugin.effectManager.clear(player)
            player.gameMode = GameMode.SURVIVAL
            // remove player from armor stand
            player.vehicle?.let { stand ->
                stand.removePassenger(player)
                stand.remove()
            }
        }
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
        // make player ride armor stand and blind him
        uuid.bukkitPlayer?.let { player ->
            plugin.effectManager.blind(player)
            player.gameMode = GameMode.ADVENTURE

            player.location.world?.spawn(player.location, ArmorStand::class.java) { stand ->
//                stand.isInvisible = true
                stand.addPassenger(player)
            }
        }
    }

    /**
     * Set a [uuid] to completely dead.
     * ONLY call if the [uuid] is revivable
     */
    fun setDead(uuid: UUID) {
        if (uuid !in revivablePlayers.keys) {
            plugin.logger.severe("Tried to set a player to DEAD that is not currently revivable!")
            return
        }
        revivablePlayers.remove(uuid)
        deadPlayers[uuid] = System.currentTimeMillis()
        uuid.bukkitPlayer?.let { player ->
            plugin.effectManager.clear(player)
            player.gameMode = GameMode.SPECTATOR
            player.vehicle?.let { stand ->
                stand.removePassenger(player)
                stand.remove()
            }
        }
    }

    private fun startRevivalTimer() {
        plugin.server.scheduler.scheduleSyncRepeatingTask(plugin, {
            val currTime = System.currentTimeMillis()
            // handle moving players from the revivable state to the dead state
            revivablePlayers
                // only take players where the time is greater than
                .filter { (_, deathTime) -> (currTime - deathTime) / 1000 >= 60 }
                // move all these people to dead
                .forEach { (uuid, _) -> setDead(uuid) }
        }, 0, 20L)
    }

}
package dev.jaims.revivalplugin.listener

import dev.jaims.revivalplugin.RevivalPlugin
import dev.jaims.revivalplugin.config.Config
import dev.jaims.revivalplugin.const.PlayerState
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent

class PlayerDamagePlayerListener(private val plugin: RevivalPlugin) : Listener {

    @EventHandler
    fun EntityDamageByEntityEvent.onEntityDamage() {
        if (damager !is Player) return
        if (entity !is Player) return

        val player = damager as Player

        val state = plugin.playerStateManager.getState(entity.uniqueId)
        if (state != PlayerState.REVIVABLE) return

        val material = Material.valueOf(Config.REVIVAL_MATERIAL.get<String>().uppercase())

        if (material == player.inventory.itemInMainHand.type) {
            // if the player is holding golden apple, revive
            plugin.playerStateManager.setAlive(entity.uniqueId)
            return
        }

        // no golden apple, damage was done, set them to fully dead
        plugin.playerStateManager.setDead(entity.uniqueId)
    }

}
package dev.jaims.revivalplugin.listener

import dev.jaims.revivalplugin.RevivalPlugin
import dev.jaims.revivalplugin.config.Config
import dev.jaims.revivalplugin.const.PlayerState
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractAtEntityEvent

class PlayerInteractEntityListener(private val plugin: RevivalPlugin) : Listener {

    @EventHandler
    fun PlayerInteractAtEntityEvent.onInteract() {
        val state = plugin.playerStateManager.getState(rightClicked.uniqueId)
        if (state != PlayerState.REVIVABLE) return

        val material = Material.valueOf(Config.REVIVAL_MATERIAL.get<String>().uppercase())

        if (material != player.inventory.itemInMainHand.type) return
        // set the player to alive if they are clicked with a golden apple
        plugin.playerStateManager.setAlive(rightClicked.uniqueId)
    }

}
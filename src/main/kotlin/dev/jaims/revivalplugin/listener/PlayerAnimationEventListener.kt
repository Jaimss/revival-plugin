package dev.jaims.revivalplugin.listener

import dev.jaims.revivalplugin.RevivalPlugin
import dev.jaims.revivalplugin.const.PlayerState
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerAnimationEvent
import org.bukkit.event.player.PlayerAnimationType

class PlayerAnimationEventListener(private val plugin: RevivalPlugin) : Listener {

    @EventHandler
    fun PlayerAnimationEvent.onPlayerInteract() {
        val targetBlock = player.getTargetBlock(null, 4)
        println("targetBlock = ${targetBlock}")
        // get the target entity
        val targetEntity = targetBlock.chunk.entities.firstOrNull {
            targetBlock.location.distance(it.location) < 2
        } ?: return
        println("targetEntity.uniqueId = ${targetEntity.uniqueId}")
        // get the target player
        val targetPlayer = targetEntity as? Player ?: return
        println("targetPlayer = ${targetPlayer.uniqueId}")

        val state = plugin.playerStateManager.getState(targetPlayer.uniqueId)
        // only handle revivable interactions
        if (state != PlayerState.REVIVABLE) return
        println("1")

        // if they have the golden apple, heal the player
        if (player.inventory.itemInMainHand.type == Material.GOLDEN_APPLE) {
            plugin.playerStateManager.setAlive(targetPlayer.uniqueId)
        } else if (this.animationType == PlayerAnimationType.ARM_SWING) {
            // attack, so the player should be dead
            plugin.playerStateManager.setDead(targetPlayer.uniqueId)
        }
    }

}
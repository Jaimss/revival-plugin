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
        // get the target entity
        val targetEntity = targetBlock.chunk.entities.firstOrNull {
            targetBlock.location.distance(it.location) < 2
        } ?: return
        // get the target player
        val targetPlayer = targetEntity as? Player ?: return

        val state = plugin.playerStateManager.getState(targetPlayer.uniqueId)
        // only handle revivable interactions
        if (state != PlayerState.REVIVABLE) return

        // if they have the golden apple, heal the player
        if (player.itemInUse?.type == Material.GOLDEN_APPLE) {
            plugin.playerStateManager.setAlive(targetPlayer.uniqueId)
            return
        }

        if (this.animationType == PlayerAnimationType.OFF_ARM_SWING) {
            // attack, so the player should be dead
            plugin.playerStateManager.setDead(targetPlayer.uniqueId)
        }
    }

}
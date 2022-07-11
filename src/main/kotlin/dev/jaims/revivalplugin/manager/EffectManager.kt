package dev.jaims.revivalplugin.manager

import dev.jaims.revivalplugin.config.Config
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class EffectManager {

    /**
     * Give a player blindness
     */
    fun blind(player: Player) = player.addPotionEffect(
        PotionEffect(
            PotionEffectType.BLINDNESS,
            Integer.MAX_VALUE,
            Config.REVIVAL_BLINDNESS_AMPLIFIER.get(),
            Config.REVIVAL_BLINDNESS_AMBIENT.get(),
            Config.REVIVAL_BLINDNESS_PARTICLES.get(),
            Config.REVIVAL_BLINDNESS_ICON.get(),
        )
    )

    /**
     * Clear potion effects
     */
    fun clear(player: Player) = player.activePotionEffects.forEach { effect -> player.removePotionEffect(effect.type) }

}
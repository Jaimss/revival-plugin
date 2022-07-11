package dev.jaims.revivalplugin.manager

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
            255,
            true,
            false,
            false,
        )
    )

    /**
     * Clear potion effects
     */
    fun clear(player: Player) = player.activePotionEffects.forEach { effect -> player.removePotionEffect(effect.type) }

}
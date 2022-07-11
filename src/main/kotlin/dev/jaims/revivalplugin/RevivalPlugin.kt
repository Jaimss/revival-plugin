package dev.jaims.revivalplugin

import dev.jaims.revivalplugin.listener.*
import dev.jaims.revivalplugin.manager.EffectManager
import dev.jaims.revivalplugin.manager.PlayerStateManager
import org.bukkit.plugin.java.JavaPlugin
import kotlin.system.measureTimeMillis

class RevivalPlugin : JavaPlugin() {

    lateinit var playerStateManager: PlayerStateManager
    lateinit var effectManager: EffectManager

    override fun onEnable() {
        val millis = measureTimeMillis {
            logger.info("Enabling ${description.fullName}...")
        }

        playerStateManager = PlayerStateManager(this)
        effectManager = EffectManager()

        registerListeners()

        logger.info("Enabled ${description.fullName} in ${millis}ms.")
    }

    override fun onDisable() {
        val millis = measureTimeMillis {
            logger.info("Disabling ${description.fullName}...")
        }
        logger.info("Disabled ${description.fullName} in ${millis}ms.")
    }

    private fun registerListeners() {
        with(server.pluginManager) {
            registerEvents(PlayerDeathListener(this@RevivalPlugin), this@RevivalPlugin)
            registerEvents(EntityMoveListener(this@RevivalPlugin), this@RevivalPlugin)
            registerEvents(PlayerDamagePlayerListener(this@RevivalPlugin), this@RevivalPlugin)
            registerEvents(PlayerInteractEntityListener(this@RevivalPlugin), this@RevivalPlugin)
            registerEvents(EntityDismountListener(this@RevivalPlugin), this@RevivalPlugin)
        }
    }

}
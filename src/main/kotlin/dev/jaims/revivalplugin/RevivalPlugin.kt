package dev.jaims.revivalplugin

import dev.jaims.revivalplugin.listener.PlayerDeathListener
import dev.jaims.revivalplugin.manager.UUIDStateManager
import org.bukkit.plugin.java.JavaPlugin
import kotlin.system.measureTimeMillis

class RevivalPlugin : JavaPlugin() {

    lateinit var playerStateManager: UUIDStateManager

    override fun onEnable() {
        val millis = measureTimeMillis {
            logger.info("Enabling ${description.fullName}...")
        }

        playerStateManager = UUIDStateManager(this)

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
            registerEvents(PlayerDeathListener(this@RevivalPlugin), this@RevivalPlugin)
        }
    }

}
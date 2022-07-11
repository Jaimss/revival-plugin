package dev.jaims.revivalplugin

import dev.jaims.revivalplugin.command.ReloadCommand
import dev.jaims.revivalplugin.command.ResetCommand
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

        saveDefaultConfig()

        playerStateManager = PlayerStateManager(this)
        effectManager = EffectManager()

        registerListeners()
        registerCommands()

        logger.info("Enabled ${description.fullName} in ${millis}ms.")
    }

    override fun onDisable() {
        val millis = measureTimeMillis {
            logger.info("Disabling ${description.fullName}...")
        }

        // reset before disable
        playerStateManager.reset()

        logger.info("Disabled ${description.fullName} in ${millis}ms.")
    }

    private fun registerCommands() {
        getCommand("reload")?.setExecutor(ReloadCommand(this))
        getCommand("reset")?.setExecutor(ResetCommand(this))
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
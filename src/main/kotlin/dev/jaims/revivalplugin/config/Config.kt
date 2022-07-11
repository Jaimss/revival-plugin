package dev.jaims.revivalplugin.config

import dev.jaims.revivalplugin.RevivalPlugin
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.plugin.java.JavaPlugin

enum class Config(val path: String, val default: Any) {

    GENERAL_MESSAGE_NO_PERMISSION("general.message.no_permission", "&c(!) No permission!"),
    GENERAL_MESSAGE_RELOAD("general.message.reload", "&a(!) Reload success!"),
    GENERAL_MESSAGE_RESET("general.message.reset", "&a(!) Successfully reset all dead and revivable players to alive!"),

    ALIVE_GAMEMODE("alive.gamemode", GameMode.SURVIVAL.toString()),
    ALIVE_MESSAGE_REVIVED("alive.message.revived", "&a(!) You have been revived!"),

    REVIVAL_GAMEMODE("revival.gamemode", GameMode.ADVENTURE.toString()),
    REVIVAL_TIME_TO_REVIVE("revival.time_to_revive", 60),
    REVIVAL_MATERIAL("revival.material", Material.GOLDEN_APPLE.toString()),
    REVIVAL_BLINDNESS_AMPLIFIER("revival.blindness.amplifier", 255),
    REVIVAL_BLINDNESS_AMBIENT("revival.blindness.ambient", true),
    REVIVAL_BLINDNESS_PARTICLES("revival.blindness.particles", false),
    REVIVAL_BLINDNESS_ICON("revival.blindness.icon", false),
    REVIVAL_ARMOR_STAND_OFFSET_Y("revival.armor_stand_offset_y", -1.0),
    REVIVAL_MESSAGE_WAITING_FOR_REVIVAL(
        "revival.message.waiting_for_revival",
        "&3(!) Waiting for revival for the next &e{time} &3seconds!"
    ),
    REVIVAL_MESSAGE_EXPIRED(
        "revival.message.expired",
        "&c(!) Your revival time limit expired."
    ),

    DEAD_GAMEMODE("dead.gamemode", GameMode.SPECTATOR.toString()),
    DEAD_MESSAGE_KILLED("dead.message.killed", "&c(!) You have been killed permanently!");

    inline fun <reified T> get(): T {
        val plugin = JavaPlugin.getPlugin(RevivalPlugin::class.java)
        val value = plugin.config.get(path, default)
        if (value !is T) error("Invalid Type ${T::class.java} for $path!")
        return value
    }

}
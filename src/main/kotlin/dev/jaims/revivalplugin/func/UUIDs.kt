package dev.jaims.revivalplugin.func

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

val UUID.bukkitPlayer: Player?
    get() = Bukkit.getPlayer(this)

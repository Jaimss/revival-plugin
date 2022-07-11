package dev.jaims.revivalplugin.const

import org.bukkit.permissions.Permissible

enum class Permission(private val perm: String) {
    ADMIN("admin"),

    RELOAD("command.reload"),
    RESET("command.reset");

    private val fullNode: String
        get() = "revivalplugin.$perm"

    fun has(permissible: Permissible): Boolean {
        return permissible.hasPermission(ADMIN.fullNode) || permissible.hasPermission(fullNode)
    }


}
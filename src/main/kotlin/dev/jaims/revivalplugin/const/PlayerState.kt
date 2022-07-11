package dev.jaims.revivalplugin.const

enum class PlayerState {
    /**
     * Player is alive and activej
     */
    ALIVE,

    /**
     * Player has been killed but can be revived. Is alive with blindness
     */
    REVIVABLE,

    /**
     * Player is completely dead
     */
    DEAD
}
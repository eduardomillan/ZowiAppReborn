package com.bq.zowi.models

data class Achievement(
    val id: String,
    val type: String,
    var unlocked: Boolean = false
) {
    enum class Id {
        crusaito, flapping, shake_leg, tip_toe, jitter, ascending_turn,
        swing, super_happy, sleepy, fart, confused, in_love, angry,
        anxious, magic, wave, mouths_editor
    }

    enum class Type { movement, animation, game }
}

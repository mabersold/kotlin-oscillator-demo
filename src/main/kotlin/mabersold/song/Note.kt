package song

import player.Pitch

data class Note(val pitch: Pitch, val volume: Short = 128, val panning: Int = 64)
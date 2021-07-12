package song

import oscillator.Oscillator

data class Instrument(val oscillator: Oscillator, val phrases: List<Phrase>, val sequence: List<Int>, val decay: Int)
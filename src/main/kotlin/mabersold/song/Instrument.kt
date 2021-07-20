package song

data class Instrument(
    val oscillatorType: OscillatorType,
    val phrases: List<Phrase>,
    val sequence: List<Int>,
    val decay: Int,
    val pulseWidth: Double = 0.5
)
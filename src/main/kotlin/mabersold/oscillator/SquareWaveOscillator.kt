package oscillator

class SquareWaveOscillator(private val pulseWidth: Double = 0.5) : Oscillator() {
    override fun getSignal(frequency: Double, sampleRate: Double): Double {
        val xValue = getXValue(frequency, sampleRate)
        position++
        return if (xValue < pulseWidth) 1.0 else -1.0
    }
}
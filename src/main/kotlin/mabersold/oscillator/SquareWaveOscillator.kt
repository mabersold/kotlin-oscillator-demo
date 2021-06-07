package oscillator

class SquareWaveOscillator(private val pulseWidth: Double = 0.5) : Oscillator() {
    override fun getSignal(position: Int, amplitude: Short, frequency: Double, sampleRate: Double): Short {
        val yValue = if (getXValue(position, frequency, sampleRate) < pulseWidth) 1 else -1
        return (amplitude * yValue).toShort()
    }
}
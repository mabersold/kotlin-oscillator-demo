package oscillator

import kotlin.math.floor

class SawtoothWaveOscillator : Oscillator() {
    override fun getSignal(frequency: Double, sampleRate: Double): Double {
        val xValue = getXValue(frequency, sampleRate)
        position++
        return 2 * (xValue - floor(0.5 + xValue))
    }
}
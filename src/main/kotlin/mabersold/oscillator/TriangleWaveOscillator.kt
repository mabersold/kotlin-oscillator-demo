package oscillator

import kotlin.math.PI
import kotlin.math.asin
import kotlin.math.sin

class TriangleWaveOscillator : Oscillator() {
    override fun getSignal(frequency: Double, sampleRate: Double): Double {
        val xValue = getXValue(frequency, sampleRate)
        position++
        return (2 / PI) * asin(sin(2 * PI * xValue))
    }
}

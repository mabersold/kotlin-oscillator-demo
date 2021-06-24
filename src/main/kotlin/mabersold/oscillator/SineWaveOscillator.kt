package oscillator

import kotlin.math.PI
import kotlin.math.roundToInt
import kotlin.math.sin

class SineWaveOscillator : Oscillator() {
    override fun getSignal(frequency: Double, sampleRate: Double): Double {
        val xValue = getXValue(frequency, sampleRate)
        this.position++
        return sin(2 * PI * xValue)
    }
}
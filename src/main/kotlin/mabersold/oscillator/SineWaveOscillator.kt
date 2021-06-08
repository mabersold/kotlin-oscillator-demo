package oscillator

import kotlin.math.PI
import kotlin.math.roundToInt
import kotlin.math.sin

class SineWaveOscillator : Oscillator() {
    override fun getSignal(position: Int, amplitude: Short, frequency: Double, sampleRate: Double): Short =
        (amplitude * sin(2 * PI * getXValue(position, frequency, sampleRate))).roundToInt().toShort()
}
package oscillator

import kotlin.math.PI
import kotlin.math.sin

class SineWaveOscillator : Oscillator() {
    override fun getSignal(position: Int, amplitude: Short, frequency: Double, sampleRate: Double): Short =
        (amplitude * sin(2 * PI * (position * frequency / sampleRate))).toInt().toShort()
}
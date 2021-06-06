package oscillator

import kotlin.math.PI
import kotlin.math.sign
import kotlin.math.sin

class SquareWaveOscillator : Oscillator() {
    override fun getSignal(position: Int, amplitude: Short, frequency: Double, sampleRate: Double): Short =
        (amplitude * sign(sin( 2 * PI * (position * frequency / sampleRate)))).toInt().toShort()
}
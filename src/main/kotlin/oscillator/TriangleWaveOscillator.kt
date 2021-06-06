package oscillator

import kotlin.math.PI
import kotlin.math.asin
import kotlin.math.sin

class TriangleWaveOscillator : Oscillator() {
    override fun getSignal(position: Int, amplitude: Short, frequency: Double, sampleRate: Double): Short =
        (amplitude * ((2 / PI) * asin(sin(2 * PI * (position * frequency / sampleRate)))))
            .toInt()
            .toShort()
}

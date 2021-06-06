package oscillator

import kotlin.math.floor

class SawtoothWaveOscillator : Oscillator() {
    override fun getSignal(position: Int, amplitude: Short, frequency: Double, sampleRate: Double): Short =
        (amplitude * (2 * (position * frequency / sampleRate) - floor(0.5 + position * frequency / sampleRate))).toInt().toShort()
}
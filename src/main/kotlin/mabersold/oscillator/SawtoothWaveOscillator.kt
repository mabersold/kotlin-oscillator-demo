package oscillator

import kotlin.math.floor

class SawtoothWaveOscillator : Oscillator() {
    override fun getSignal(position: Int, amplitude: Short, frequency: Double, sampleRate: Double): Short =
        (
            amplitude * 2 * (
                getXValue(position, frequency, sampleRate)
                - floor(0.5 + getXValue(position, frequency, sampleRate))
            )
        ).toInt().toShort()
}
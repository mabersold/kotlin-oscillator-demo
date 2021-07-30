package oscillator

import kotlin.random.Random.Default.nextDouble

class NoiseOscillator : Oscillator() {
    override fun getSignal(frequency: Double, sampleRate: Double): Double {
        position++
        return if ((position - 1) % 2 > 0) 0 - nextDouble() else nextDouble()
    }
}
package oscillator

abstract class Oscillator {
    abstract fun getSignal(position: Int, amplitude: Short, frequency: Double, sampleRate: Double): Short
}
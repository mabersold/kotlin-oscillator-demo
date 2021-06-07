package oscillator

abstract class Oscillator {
    abstract fun getSignal(position: Int, amplitude: Short, frequency: Double, sampleRate: Double): Short

    protected fun getXValue(position: Int, frequency: Double, sampleRate: Double): Double {
        val periodLength = (sampleRate / frequency).toInt()
        return (position % periodLength) * frequency / sampleRate
    }
}
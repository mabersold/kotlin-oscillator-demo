package oscillator

import kotlin.math.pow
import kotlin.math.roundToInt

abstract class Oscillator {
    abstract fun getSignal(position: Int, amplitude: Short, frequency: Double, sampleRate: Double): Short

    protected fun getXValue(position: Int, frequency: Double, sampleRate: Double): Double {
        val periodLength = (sampleRate / frequency).toInt()
        return (position % periodLength) / periodLength.toDouble()
    }

    protected fun getAdjustedXValue(position: Int, frequency: Double, sampleRate: Double): Double {
        val periodLength = sampleRate / frequency
        val expectedXValue = (position % periodLength.roundToInt()) / periodLength
        val normalExpectedChange = periodLength.pow(-1)

        val currentSegment = when (expectedXValue) {
            in 0.75..1.0 -> 0.75
            in 0.5..0.75 -> 0.5
            in 0.25..0.5 -> 0.25
            else -> 0.0
        }

        val distanceToNextSegment = currentSegment + 0.25 - expectedXValue
        val distanceFromPreviousSegment = expectedXValue - currentSegment

        //find out how many times the normal change in x value fits into that difference
        val samplesRemainingInSegment = (distanceToNextSegment / normalExpectedChange).roundToInt()
        val samplesPassedInSegment = (distanceFromPreviousSegment / normalExpectedChange).roundToInt()

        val expectDifferenceInRadians = 0.25 / (samplesPassedInSegment + samplesRemainingInSegment)
        val positionOffset = position - samplesPassedInSegment

        return expectDifferenceInRadians * (position - positionOffset) + currentSegment
    }
}
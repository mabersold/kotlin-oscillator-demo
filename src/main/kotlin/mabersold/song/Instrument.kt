package song

import MAX_AMPLITUDE
import NUMBER_OF_CHANNELS
import SAMPLE_RATE
import VOLUME_MULTIPLE
import oscillator.Oscillator
import kotlin.math.pow
import kotlin.math.roundToInt

class Instrument(private val oscillator: Oscillator, private val phrase: Phrase, private val decay: Int) {
    private var currentPosition: Int = 0
    private var activeNote: Note? = null

    fun hasMorePositions(): Boolean {
        return phrase.length * 6 > currentPosition
    }

    fun getNextPositionAudioData(bpm: Int): ShortArray {
        val note = phrase.getNote(currentPosition)
        activeNote = note ?: activeNote

        val samplesPerPosition = samplesPerPosition(secondsPerBeat(bpm.toDouble()))
        val decayInSamples = (SAMPLE_RATE * (decay / 1000.0)).roundToInt()

        // If there is no active note, fill the array with zeroes
        if (activeNote == null) {
            return ShortArray(samplesPerPosition) { 0 }
        }

        if (note != null) {
            oscillator.resetPosition()
        }

        val noteAmplitude = getMaxAmplitude(activeNote!!.volume)

        val returnArray = ShortArray(samplesPerPosition * NUMBER_OF_CHANNELS)

        for (i in returnArray.indices step 2) {
            val signal = oscillator.getSignal(activeNote!!.pitch.frequency, SAMPLE_RATE.toDouble())

            val amplitude = getDecayedAmplitude(noteAmplitude, decayInSamples, oscillator.position)
            val amplifiedSignal = (signal * amplitude).roundToInt().toShort()

            val rightPanning = activeNote!!.panning / 128F
            val leftSignal = (amplifiedSignal * (1 - rightPanning)).roundToInt().toShort()
            val rightSignal = (amplifiedSignal - leftSignal).toShort()

            returnArray[i] = leftSignal
            returnArray[i + 1] = rightSignal
        }

        currentPosition++

        return returnArray
    }

    private fun secondsPerBeat(bpm: Double): Double =
        bpm.pow(-1) * 60

    private fun samplesPerPosition(secondsPerBeat: Double): Int =
        (SAMPLE_RATE * secondsPerBeat / 6).roundToInt()

    private fun getMaxAmplitude(volume: Short): Short =
        if (volume * VOLUME_MULTIPLE > MAX_AMPLITUDE) MAX_AMPLITUDE else (volume * VOLUME_MULTIPLE).toShort()

    private fun getDecayedAmplitude(amplitude: Short, decay: Int, position: Int): Short =
        (amplitude / decay.toDouble() * 0.coerceAtLeast((decay - position))).roundToInt().toShort()
}
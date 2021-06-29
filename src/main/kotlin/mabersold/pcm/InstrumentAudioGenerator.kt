package pcm

import MAX_AMPLITUDE
import NUMBER_OF_CHANNELS
import SAMPLE_RATE
import VOLUME_MULTIPLE
import song.Instrument
import song.Note
import kotlin.math.pow
import kotlin.math.roundToInt

class InstrumentAudioGenerator(private val instrument: Instrument) {
    private var activeNote: Note? = null
    private var position: Int = 0

    fun generateSamplesForNextPosition(bpm: Int): ShortArray {
        val note = instrument.phrase.getNote(position)
        activeNote = note ?: activeNote

        val secondsPerBeat = bpm.toDouble().pow(-1) * 60
        val samplesPerPosition = (SAMPLE_RATE * secondsPerBeat / 6).roundToInt()
        val decayInSamples = (SAMPLE_RATE * (instrument.decay / 1000.0)).roundToInt()

        if (activeNote == null) {
            return ShortArray(samplesPerPosition * NUMBER_OF_CHANNELS) { 0 }
        }

        if (note != null) {
            instrument.oscillator.resetPosition()
        }

        val noteAmplitude = getNoteAmplitude(activeNote?.volume!!)
        val returnArray = ShortArray(samplesPerPosition * NUMBER_OF_CHANNELS)

        for (i in returnArray.indices step 2) {
            val signal = instrument.oscillator.getSignal(activeNote!!.pitch.frequency, SAMPLE_RATE.toDouble())
            val amplifiedSignal = (signal * getDecayedAmplitude(noteAmplitude!!, decayInSamples, instrument.oscillator.position)).roundToInt().toShort()

            val rightPanning = activeNote!!.panning / 128F
            val leftSignal = (amplifiedSignal * (1 - rightPanning)).roundToInt().toShort()
            val rightSignal = (amplifiedSignal - leftSignal).toShort()

            returnArray[i] = leftSignal
            returnArray[i + 1] = rightSignal
        }

        position++
        return returnArray
    }

    fun songStillActive(): Boolean = instrument.phrase.length * 6 > position

    private fun getNoteAmplitude(volume: Short): Short = if (volume * VOLUME_MULTIPLE > MAX_AMPLITUDE) MAX_AMPLITUDE else (volume * VOLUME_MULTIPLE).toShort()
    private fun getDecayedAmplitude(amplitude: Short, decay: Int, position: Int): Short = (amplitude / decay.toDouble() * 0.coerceAtLeast((decay - position))).roundToInt().toShort()
}
package pcm

import MAX_AMPLITUDE
import NUMBER_OF_CHANNELS
import SAMPLE_RATE
import VOLUME_MULTIPLE
import oscillator.NoiseOscillator
import oscillator.SawtoothWaveOscillator
import oscillator.SineWaveOscillator
import oscillator.SquareWaveOscillator
import oscillator.TriangleWaveOscillator
import song.Instrument
import song.Note
import song.OscillatorType
import kotlin.math.pow
import kotlin.math.roundToInt

class InstrumentAudioGenerator(private val instrument: Instrument) {
    private var activeNote: Note? = null
    private var sequencePosition: Int = 0
    private var phrasePosition: Int = 0
    private val oscillator = when(instrument.oscillatorType) {
        OscillatorType.SINE -> SineWaveOscillator()
        OscillatorType.TRIANGLE -> TriangleWaveOscillator()
        OscillatorType.SAW -> SawtoothWaveOscillator()
        OscillatorType.SQUARE -> SquareWaveOscillator(instrument.pulseWidth)
        OscillatorType.NOISE -> NoiseOscillator()
    }

    private fun getSecondsPerBeat(bpm: Int) =
        bpm.toDouble().pow(-1) * 60

    private fun getSamplesPerPosition(secondsPerBeat: Double) =
        (SAMPLE_RATE * secondsPerBeat / 6).roundToInt()

    fun getLengthOfReturnArray(bpm: Int) =
        getSamplesPerPosition(getSecondsPerBeat(bpm)) * NUMBER_OF_CHANNELS

    fun generateSamplesForNextPosition(bpm: Int): ShortArray {
        val returnArrayLength = getLengthOfReturnArray(bpm)

        if (sequencePosition >= instrument.sequence.size) {
            return ShortArray(returnArrayLength) { 0 }
        }

        val currentPhraseNumber = instrument.sequence[sequencePosition]
        val noteAtCurrentPosition = instrument.phrases[currentPhraseNumber].getNote(phrasePosition)
        activeNote = noteAtCurrentPosition ?: activeNote

        if (activeNote == null) {
            return ShortArray(returnArrayLength) { 0 }
        }

        val decayInSamples = (SAMPLE_RATE * (instrument.decay / 1000.0)).roundToInt()

        if (noteAtCurrentPosition != null) {
            oscillator.resetPosition()
        }

        val noteAmplitude = getNoteAmplitude(activeNote?.volume!!)
        val returnArray = ShortArray(returnArrayLength)

        for (i in returnArray.indices step 2) {
            val signal = oscillator.getSignal(activeNote!!.pitch.frequency, SAMPLE_RATE.toDouble())
            val amplifiedSignal = (signal * getDecayedAmplitude(noteAmplitude, decayInSamples, oscillator.position)).roundToInt().toShort()

            val rightPanning = activeNote!!.panning / 128F
            val leftSignal = (amplifiedSignal * (1 - rightPanning)).roundToInt().toShort()
            val rightSignal = (amplifiedSignal - leftSignal).toShort()

            returnArray[i] = leftSignal
            returnArray[i + 1] = rightSignal
        }

        phrasePosition++
        //if phrase position is outside of bounds, advance to the next part of the sequence
        if (phrasePosition >= instrument.phrases[currentPhraseNumber].length * 6) {
            sequencePosition++
            phrasePosition = 0
        }
        return returnArray
    }

    fun instrumentStillActive(): Boolean = instrument.sequence.size > sequencePosition

    private fun getNoteAmplitude(volume: Short): Short = if (volume * VOLUME_MULTIPLE > MAX_AMPLITUDE) MAX_AMPLITUDE else (volume * VOLUME_MULTIPLE).toShort()
    private fun getDecayedAmplitude(amplitude: Short, decay: Int, position: Int): Short = (amplitude / decay.toDouble() * 0.coerceAtLeast((decay - position))).roundToInt().toShort()
}
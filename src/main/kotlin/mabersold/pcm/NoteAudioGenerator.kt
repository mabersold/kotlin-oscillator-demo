package pcm

import MAX_AMPLITUDE
import NUMBER_OF_CHANNELS
import SAMPLE_RATE
import VOLUME_MULTIPLE
import oscillator.Oscillator
import song.Note
import kotlin.math.roundToInt

class NoteAudioGenerator {
    /*
     * This method simply generates the complete sample array for a given note, with the specified oscillator and decay.
     *
     * @note: The note we are to play, including the pitch, volume, and panning
     * @oscillator: The oscillator that will be used to generate the signal
     * @decay: the time in milliseconds it will take for the volume to reach zero, at a linear rate
     */
    fun generateSamples(note: Note, oscillator: Oscillator, decay: Int): ShortArray {
        //determine the total length of the note and create an array to store it
        val decayInSamples = (SAMPLE_RATE * (decay / 1000.0)).roundToInt()
        val samples = ShortArray(decayInSamples * NUMBER_OF_CHANNELS)
        val amplitude = getAmplitudeFromVolume(note.volume)

        var cursor = 0
        while (cursor < decayInSamples) {
            val signal = oscillator.getSignal(note.pitch.frequency, SAMPLE_RATE.toDouble())
            val decayedAmplitude = getDecayedAmplitude(amplitude, decayInSamples, oscillator.position)
            val amplifiedSignal = (signal * decayedAmplitude).roundToInt().toShort()

            val stereoSignal = getStereoSignal(amplifiedSignal, note.panning)
            samples[(cursor * 2)] = stereoSignal.leftSignal
            samples[(cursor * 2) + 1] = stereoSignal.rightSignal

            cursor++
        }

        return samples
    }

    private fun getAmplitudeFromVolume(volume: Short): Short =
        if (volume * VOLUME_MULTIPLE > MAX_AMPLITUDE) MAX_AMPLITUDE else (volume * VOLUME_MULTIPLE).toShort()

    private fun getDecayedAmplitude(amplitude: Short, decay: Int, position: Int): Short =
        (amplitude / decay.toDouble() * 0.coerceAtLeast((decay - position))).roundToInt().toShort()

    private fun getStereoSignal(signal: Short, panningPosition: Int): StereoSignal {
        val rightPanning = panningPosition / 128F
        val leftSignal = (signal * (1 - rightPanning)).toInt().toShort()
        val rightSignal = (signal - leftSignal).toShort()

        return StereoSignal(leftSignal, rightSignal)
    }

    class StereoSignal(val leftSignal: Short, val rightSignal: Short)
}
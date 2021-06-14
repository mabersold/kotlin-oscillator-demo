package player

import oscillator.Oscillator
import java.nio.ByteBuffer
import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.SourceDataLine
import kotlin.math.roundToInt

class AudioPlayer(private val oscillator: Oscillator) {
    private lateinit var audioLine: SourceDataLine

    companion object {
        private const val VOLUME_MULTIPLE: Short = 128
        private const val MAX_AMPLITUDE: Short = 8192
        private const val SAMPLE_RATE = 44100
        private const val SAMPLE_SIZE = 2
    }

    fun setUpAudio() {
        this.audioLine = AudioSystem.getSourceDataLine(AudioFormat(SAMPLE_RATE.toFloat(), 16, 2, true, true))
        this.audioLine.open()
        this.audioLine.start()
    }

    fun tearDownAudio() {
        this.audioLine.drain()
        this.audioLine.close()
    }

    fun playNote(note: Note, volume: Short, panningPosition: Short, duration: Int, decayInMilliseconds: Int) {
        val oscillatorBuffer = ByteBuffer.allocate(audioLine.bufferSize)
        var currentSample = 0

        val decay = (SAMPLE_RATE * (decayInMilliseconds / 1000.0)).roundToInt()
        val maxAmplitude = getMaxAmplitude(volume)

        while (currentSample < duration) {
            oscillatorBuffer.clear()
            val samplesInThisPass = audioLine.available() / SAMPLE_SIZE / 2

            for (i in currentSample until (currentSample + samplesInThisPass)) {
                val signal = oscillator.getSignal(
                    i,
                    getAmplitude(maxAmplitude, decay, i),
                    note.frequency,
                    SAMPLE_RATE.toDouble()
                )
                putSignal(oscillatorBuffer, signal, panningPosition)
            }

            audioLine.write(oscillatorBuffer.array(), 0, oscillatorBuffer.position())
            currentSample += samplesInThisPass

            while (audioLine.bufferSize / 2 < audioLine.available()) {
                Thread.sleep(1)
            }
        }
    }

    private fun putSignal(buffer: ByteBuffer, signal: Short, panningPosition: Short) {
        val rightPanning = getRightPanning(panningPosition)
        val leftSignal = (signal * (1 - rightPanning)).toInt().toShort()
        val rightSignal = (signal - leftSignal).toShort()

        buffer.putShort(leftSignal).putShort(rightSignal)
    }

    private fun getRightPanning(panningPosition: Short): Float =
        panningPosition / 128F

    private fun getMaxAmplitude(volume: Short): Short =
        if (volume * VOLUME_MULTIPLE > MAX_AMPLITUDE) MAX_AMPLITUDE else (volume * VOLUME_MULTIPLE).toShort()

    private fun getAmplitude(amplitude: Short, decay: Int, position: Int): Short =
        (amplitude / decay.toDouble() * 0.coerceAtLeast((decay - position))).roundToInt().toShort()
}
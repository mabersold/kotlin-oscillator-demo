package player

import oscillator.Oscillator
import java.nio.ByteBuffer
import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.SourceDataLine
import kotlin.math.sign

class AudioPlayer(private val oscillator: Oscillator) {
    companion object {
        private const val VOLUME_MULTIPLE: Short = 256
        private const val SAMPLE_RATE = 44100
        private const val SAMPLE_SIZE = 2
    }

    init {

    }

    fun playAudio() {
        playAudio(428, 64)
    }

    private fun playAudio(frequency: Int, volume: Short = 128, panningPosition: Short = 64) {
        val amplitude = getAmplitude(volume)

        val audioLine = startDataLine(AudioFormat(SAMPLE_RATE.toFloat(), 16, 2, true, true))

        val oscillatorBuffer = ByteBuffer.allocate(audioLine.bufferSize)
        val totalSamples = SAMPLE_RATE

        var currentSample = 0

        while (currentSample < totalSamples) {
            oscillatorBuffer.clear()

            val samplesInThisPass = audioLine.available() / SAMPLE_SIZE / 2

            for (i in currentSample until (currentSample + samplesInThisPass)) {
                val signal = oscillator.getSignal(i, amplitude, frequency.toDouble(), SAMPLE_RATE.toDouble())
//                if (signal.compareTo(0) == 0) println("signal is zero")

                putSignal(oscillatorBuffer, signal, panningPosition)
            }

            //first period is from 0 to about 103/104
            //there are 44100 samples per second
            //428 = number of periods per second
            //duration of one period is 0.00233 seconds
            //Yes, if you divide 44100 by 428 you get about 103.03
            //a single sample

            audioLine.write(oscillatorBuffer.array(), 0, oscillatorBuffer.position())
            currentSample += samplesInThisPass

            while (audioLine.bufferSize / 2 < audioLine.available()) {
                Thread.sleep(1)
            }
        }

        closeDataLine(audioLine)
    }

    private fun putSignal(buffer: ByteBuffer, signal: Short, panningPosition: Short) {
        val rightPanning = getRightPanning(panningPosition)
        val leftSignal = (signal * (1 - rightPanning)).toInt().toShort()
        val rightSignal = (signal - leftSignal).toShort()

        buffer.putShort(leftSignal).putShort(rightSignal)
    }

    private fun getRightPanning(panningPosition: Short): Float =
        panningPosition / 128F

    private fun getAmplitude(volume: Short): Short =
        if (volume * 256 > Short.MAX_VALUE) Short.MAX_VALUE else (volume * VOLUME_MULTIPLE).toShort()

    private fun startDataLine(audioFormat: AudioFormat): SourceDataLine {
        val audioLine = AudioSystem.getSourceDataLine(audioFormat)

        audioLine.open()
        audioLine.start()

        return audioLine
    }

    private fun closeDataLine(dataLine: SourceDataLine) {
        dataLine.drain()
        dataLine.close()
    }
}
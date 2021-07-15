package player

import NUMBER_OF_CHANNELS
import SAMPLE_RATE
import SAMPLE_SIZE_IN_BITS
import pcm.InstrumentAudioGenerator
import song.Song
import java.nio.ByteBuffer
import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.SourceDataLine

class AudioPlayer {
    private lateinit var audioLine: SourceDataLine

    private fun setUpAudio() {
        this.audioLine = AudioSystem.getSourceDataLine(AudioFormat(SAMPLE_RATE.toFloat(), SAMPLE_SIZE_IN_BITS, NUMBER_OF_CHANNELS, true, true))
        this.audioLine.open()
        this.audioLine.start()
    }

    private fun tearDownAudio() {
        this.audioLine.drain()
        this.audioLine.close()
    }

    fun playSignal(signals: ShortArray) {
        setUpAudio()
        val buffer = ByteBuffer.allocate(signals.size)

        for (sample in signals) {
            if (!buffer.hasRemaining()) {
                audioLine.write(buffer.array(), 0, buffer.position())
                buffer.clear()
            }

            buffer.putShort(sample)
        }

        audioLine.write(buffer.array(), 0, buffer.position())
        tearDownAudio()
    }

    fun playSong(song: Song) {
        setUpAudio()

        val buffer = ByteBuffer.allocate(audioLine.bufferSize)
        val generator = InstrumentAudioGenerator(song.instruments[0])

        while (generator.songStillActive()) {
            val audioData = generator.generateSamplesForNextPosition(song.bpm)
            for (sample in audioData) {
                buffer.putShort(sample)
            }
            audioLine.write(buffer.array(), 0, buffer.position())
            buffer.clear()
        }
        tearDownAudio()
    }
}
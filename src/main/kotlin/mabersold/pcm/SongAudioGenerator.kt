package pcm

import song.Song
import java.lang.RuntimeException

class SongAudioGenerator(private val song: Song) {
    private val instrumentGenerators = arrayListOf<InstrumentAudioGenerator>()

    init {
        song.instruments.forEach { instrument ->
            instrumentGenerators.add(InstrumentAudioGenerator(instrument))
        }
    }

    fun songStillActive(): Boolean =
        instrumentGenerators.any { instrument -> instrument.instrumentStillActive() }

    fun generateSamplesForNextPosition(): ShortArray {
        val arraySize = instrumentGenerators.maxOf { generator -> generator.getLengthOfReturnArray(song.bpm) }
        val mixedSamples = ShortArray(arraySize) { 0 }

        instrumentGenerators.forEach { generator ->
            val instrumentSamples = generator.generateSamplesForNextPosition(song.bpm)
            mixAudio(instrumentSamples, mixedSamples)
        }

        return mixedSamples
    }

    private fun mixAudio(sourceAudio: ShortArray, destinationAudio: ShortArray) {
        if (sourceAudio.size != destinationAudio.size) {
            throw RuntimeException("Source audio and destination audio sample arrays must be identical length. Source: ${sourceAudio.size}, Destination: ${destinationAudio.size}")
        }

        for (index in sourceAudio.indices) {
            destinationAudio[index] = mixSamples(destinationAudio[index], sourceAudio[index])
        }
    }

    private fun mixSamples(firstSample: Short, secondSample: Short) =
        (firstSample + secondSample).coerceAtMost(Short.MAX_VALUE.toInt()).toShort()
}
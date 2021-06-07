import oscillator.SawtoothWaveOscillator
import oscillator.SineWaveOscillator
import oscillator.SquareWaveOscillator
import oscillator.TriangleWaveOscillator
import player.AudioPlayer

fun main(args: Array<String>) {
    println("Hello World!")

    val sineWavePlayer = AudioPlayer(SineWaveOscillator())
    val sawPlayer = AudioPlayer(SawtoothWaveOscillator())
    val squarePlayer = AudioPlayer(SquareWaveOscillator())
    val triangleWavePlayer = AudioPlayer(TriangleWaveOscillator())

    sineWavePlayer.playAudio()
    triangleWavePlayer.playAudio()
    sawPlayer.playAudio()
    squarePlayer.playAudio()
}

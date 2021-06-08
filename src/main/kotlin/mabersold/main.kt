import oscillator.SawtoothWaveOscillator
import oscillator.SineWaveOscillator
import oscillator.SquareWaveOscillator
import oscillator.TriangleWaveOscillator
import player.AudioPlayer
import player.Note.A4
import player.Note.B4
import player.Note.C4
import player.Note.C5
import player.Note.D4
import player.Note.E4
import player.Note.F4
import player.Note.G4

fun main(args: Array<String>) {
    println("Hello World!")

    val sineWavePlayer = AudioPlayer(SineWaveOscillator())
    val sawPlayer = AudioPlayer(SawtoothWaveOscillator())
    val squarePlayer = AudioPlayer(SquareWaveOscillator())
    val triangleWavePlayer = AudioPlayer(TriangleWaveOscillator())

    try {
        sineWavePlayer.setUpAudio()

        for (i in listOf(C4, D4, E4, F4, G4, A4, B4, C5)) {
            sineWavePlayer.playNote(i, 128, 64, 22050)
        }
    } finally {
        sineWavePlayer.tearDownAudio()
    }
}
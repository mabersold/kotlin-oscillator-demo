import oscillator.SineWaveOscillator
import pcm.NoteAudioGenerator
import player.AudioPlayer
import player.Pitch.*
import song.Instrument
import song.Note
import song.Phrase
import song.Song

/*
 * Command line args (for now)
 *
 * 1: Play a single note with playSignal
 * 2: Play a song using playSong
 */
fun main(args: Array<String>) {
    when(getArg(args)) {
        "1" -> playSignal()
        "2" -> playSong()
        else -> playSong()
    }
}

private fun getArg(args: Array<String>): String = if (args.isEmpty()) "2" else args[0]

private fun playSignal() {
    val noteSignalGenerator = NoteAudioGenerator()
    val signals = noteSignalGenerator.generateSamples(Note(C4, 128, 64), SineWaveOscillator(), 300)
    val player = AudioPlayer()
    player.playSignal(signals)
}

private fun playSong() {
    val phrase = Phrase(4)
    phrase.insertNote(0, Note(C4, 128, 0))
    phrase.insertNote(2, Note(D4, 128, 18))
    phrase.insertNote(4, Note(E4, 128, 36))
    phrase.insertNote(6, Note(F4, 128, 54))
    phrase.insertNote(8, Note(G4, 128, 72))
    phrase.insertNote(10, Note(A4, 128, 90))
    phrase.insertNote(12, Note(B4, 128, 108))
    phrase.insertNote(14, Note(C5, 128, 128))
    val instrument = Instrument(SineWaveOscillator(), phrase, 300)
    val song = Song(instrument, 80)
    val audioPlayer = AudioPlayer()

    audioPlayer.playSong(song)
}

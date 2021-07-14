import oscillator.SineWaveOscillator
import oscillator.SquareWaveOscillator
import pcm.NoteAudioGenerator
import player.AudioPlayer
import player.Pitch.*
import song.Instrument
import song.Note
import song.OscillatorType
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
    val phrase0 = Phrase(8)
    phrase0.insertNote(0, Note(E5))
    phrase0.insertNote(3, Note(E5))
    phrase0.insertNote(9, Note(E5))
    phrase0.insertNote(15, Note(C5))
    phrase0.insertNote(18, Note(E5))
    phrase0.insertNote(24, Note(G5))
    phrase0.insertNote(36, Note(G4))

    val phrase1 = Phrase(16)
    phrase1.insertNote(0, Note(C5))
    phrase1.insertNote(9, Note(G4))
    phrase1.insertNote(18, Note(E4))

    phrase1.insertNote(27, Note(A4))
    phrase1.insertNote(33, Note(B4))
    phrase1.insertNote(39, Note(ASH4))
    phrase1.insertNote(42, Note(A4))

    phrase1.insertNote(48, Note(G4))
    phrase1.insertNote(52, Note(E5))
    phrase1.insertNote(56, Note(G5))
    phrase1.insertNote(60, Note(A5))
    phrase1.insertNote(66, Note(F5))
    phrase1.insertNote(69, Note(G5))

    phrase1.insertNote(75, Note(E5))
    phrase1.insertNote(81, Note(C5))
    phrase1.insertNote(84, Note(D5))
    phrase1.insertNote(87, Note(B4))

    val phrase2 = Phrase(8)
    phrase2.insertNote(6, Note(G5))
    phrase2.insertNote(9, Note(FSH5))
    phrase2.insertNote(12, Note(F5))
    phrase2.insertNote(15, Note(DSH5))
    phrase2.insertNote(21, Note(E5))

    phrase2.insertNote(27, Note(GSH4))
    phrase2.insertNote(30, Note(A4))
    phrase2.insertNote(33, Note(C5))

    phrase2.insertNote(39, Note(A4))
    phrase2.insertNote(42, Note(C5))
    phrase2.insertNote(45, Note(D5))

    val phrase3 = Phrase(8)
    phrase3.insertNote(6, Note(G5))
    phrase3.insertNote(9, Note(FSH5))
    phrase3.insertNote(12, Note(F5))
    phrase3.insertNote(15, Note(DSH5))

    phrase3.insertNote(21, Note(E5))
    phrase3.insertNote(27, Note(C6))
    phrase3.insertNote(33, Note(C6))
    phrase3.insertNote(36, Note(C6))

    val phrase4 = Phrase(8)
    phrase4.insertNote(6, Note(DSH5))
    phrase4.insertNote(15, Note(D5))
    phrase4.insertNote(24, Note(C5))

    val phrase5 = Phrase(24)
    phrase5.insertNote(0, Note(C5))
    phrase5.insertNote(3, Note(C5))
    phrase5.insertNote(9, Note(C5))
    phrase5.insertNote(15, Note(C5))
    phrase5.insertNote(18, Note(D5))
    phrase5.insertNote(24, Note(E5))
    phrase5.insertNote(27, Note(C5))
    phrase5.insertNote(33, Note(A4))
    phrase5.insertNote(36, Note(G4))

    phrase5.insertNote(48, Note(C5))
    phrase5.insertNote(51, Note(C5))
    phrase5.insertNote(57, Note(C5))
    phrase5.insertNote(63, Note(C5))
    phrase5.insertNote(66, Note(D5))
    phrase5.insertNote(69, Note(E5))

    phrase5.insertNote(96, Note(C5))
    phrase5.insertNote(99, Note(C5))
    phrase5.insertNote(105, Note(C5))
    phrase5.insertNote(111, Note(C5))
    phrase5.insertNote(114, Note(D5))
    phrase5.insertNote(120, Note(E5))
    phrase5.insertNote(123, Note(C5))
    phrase5.insertNote(129, Note(A4))
    phrase5.insertNote(132, Note(G4))

    val phrase6 = Phrase(8)
    phrase6.insertNote(0, Note(E5))
    phrase6.insertNote(3, Note(C5))
    phrase6.insertNote(9, Note(G4))
    phrase6.insertNote(18, Note(GSH4))
    phrase6.insertNote(24, Note(A4))
    phrase6.insertNote(27, Note(F5))
    phrase6.insertNote(33, Note(F5))
    phrase6.insertNote(36, Note(A4))

    val phrase7 = Phrase(8)
    phrase7.insertNote(0, Note(B4))
    phrase7.insertNote(4, Note(A5))
    phrase7.insertNote(8, Note(A5))
    phrase7.insertNote(12, Note(A5))
    phrase7.insertNote(16, Note(G5))
    phrase7.insertNote(20, Note(F5))
    phrase7.insertNote(24, Note(E5))
    phrase7.insertNote(27, Note(C5))
    phrase7.insertNote(33, Note(A4))
    phrase7.insertNote(36, Note(G4))

    val phrase8 = Phrase(8)
    phrase8.insertNote(0, Note(B4))
    phrase8.insertNote(3, Note(F5))
    phrase8.insertNote(9, Note(F5))
    phrase8.insertNote(12, Note(F5))
    phrase8.insertNote(16, Note(E5))
    phrase8.insertNote(20, Note(D5))
    phrase8.insertNote(24, Note(C5))

    val instrument = Instrument(
        OscillatorType.SQUARE,
        listOf(phrase0, phrase1, phrase2, phrase3, phrase4, phrase5, phrase6, phrase7, phrase8),
        listOf(0, 1, 1, 2, 3, 2, 4, 2, 3, 2, 4, 5, 0, 1, 1, 6, 7, 6, 8, 6, 7, 6, 8, 5, 0),
        228
    )
    val song = Song(instrument, 192)
    val audioPlayer = AudioPlayer()

    audioPlayer.playSong(song)
}

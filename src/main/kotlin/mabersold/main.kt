import oscillator.SineWaveOscillator
import pcm.NoteAudioGenerator
import player.AudioPlayer
import song.Pitch.*
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
    val audioPlayer = AudioPlayer()
    audioPlayer.playSong(generateSong())
}

private fun generateSong(): Song {
    return Song(listOf(generateMelodyInstrument(), generateHarmonyInstrument(), generateBassInstrument()), 192)
}

private fun generateMelodyInstrument(): Instrument {
    return Instrument(
        OscillatorType.SQUARE,
        generateMelodyPhrases(),
        listOf(0, 1, 1, 2, 3, 2, 4, 2, 3, 2, 4, 5, 0, 1, 1, 6, 7, 6, 8, 6, 7, 6, 8, 5, 0),
        228
    )
}

private fun generateHarmonyInstrument(): Instrument {
    return Instrument(
        OscillatorType.SQUARE,
        generateHarmonyPhrases(),
        listOf(0, 1, 1, 2, 3, 2, 4, 2, 3, 2, 4, 5, 0, 1, 1, 6, 7, 6, 8, 6, 7, 6, 8, 5, 0),
        228
    )
}

private fun generateBassInstrument(): Instrument {
    return Instrument(
        OscillatorType.TRIANGLE,
        generateBassPhrases(),
        listOf(0, 1, 1, 2, 3, 2, 4, 2, 3, 2, 4, 5, 5, 5, 0, 1, 1, 6, 7, 6, 8, 6, 7, 6, 8, 5, 5, 5, 0),
        228
    )
}

private fun generateMelodyPhrases(): List<Phrase> {
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

    return listOf(phrase0, phrase1, phrase2, phrase3, phrase4, phrase5, phrase6, phrase7, phrase8)
}

private fun generateHarmonyPhrases(): List<Phrase> {
    val phrase0 = Phrase(8)
    phrase0.insertNote(0, Note(FSH4))
    phrase0.insertNote(3, Note(FSH4))
    phrase0.insertNote(9, Note(FSH4))
    phrase0.insertNote(15, Note(FSH4))
    phrase0.insertNote(18, Note(FSH4))
    phrase0.insertNote(24, Note(G4))

    val phrase1 = Phrase(16)
    phrase1.insertNote(0, Note(E4))
    phrase1.insertNote(9, Note(C4))
    phrase1.insertNote(18, Note(G3))

    phrase1.insertNote(27, Note(C4))
    phrase1.insertNote(33, Note(D4))
    phrase1.insertNote(39, Note(CSH4))
    phrase1.insertNote(42, Note(C4))

    phrase1.insertNote(48, Note(C4))
    phrase1.insertNote(52, Note(G4))
    phrase1.insertNote(56, Note(B4))
    phrase1.insertNote(60, Note(C5))
    phrase1.insertNote(66, Note(A4))
    phrase1.insertNote(69, Note(B4))

    phrase1.insertNote(75, Note(A4))
    phrase1.insertNote(81, Note(E4))
    phrase1.insertNote(84, Note(F4))
    phrase1.insertNote(87, Note(D4))

    val phrase2 = Phrase(8)
    phrase2.insertNote(6, Note(E5))
    phrase2.insertNote(9, Note(DSH5))
    phrase2.insertNote(12, Note(D5))
    phrase2.insertNote(15, Note(B4))
    phrase2.insertNote(21, Note(C5))

    phrase2.insertNote(27, Note(E4))
    phrase2.insertNote(30, Note(F4))
    phrase2.insertNote(33, Note(G4))

    phrase2.insertNote(39, Note(C4))
    phrase2.insertNote(42, Note(E4))
    phrase2.insertNote(45, Note(F4))

    val phrase3 = Phrase(8)
    phrase3.insertNote(6, Note(E5))
    phrase3.insertNote(9, Note(DSH5))
    phrase3.insertNote(12, Note(D5))
    phrase3.insertNote(15, Note(B4))

    phrase3.insertNote(21, Note(C5))
    phrase3.insertNote(27, Note(G5))
    phrase3.insertNote(33, Note(G5))
    phrase3.insertNote(36, Note(G5))

    val phrase4 = Phrase(8)
    phrase4.insertNote(6, Note(GSH4))
    phrase4.insertNote(15, Note(F4))
    phrase4.insertNote(24, Note(E4))

    val phrase5 = Phrase(24)
    phrase5.insertNote(0, Note(GSH4))
    phrase5.insertNote(3, Note(GSH4))
    phrase5.insertNote(9, Note(GSH4))
    phrase5.insertNote(15, Note(GSH4))
    phrase5.insertNote(18, Note(ASH4))
    phrase5.insertNote(24, Note(G4))
    phrase5.insertNote(27, Note(E4))
    phrase5.insertNote(33, Note(E4))
    phrase5.insertNote(36, Note(C4))

    phrase5.insertNote(48, Note(GSH4))
    phrase5.insertNote(51, Note(GSH4))
    phrase5.insertNote(57, Note(GSH4))
    phrase5.insertNote(63, Note(GSH4))
    phrase5.insertNote(66, Note(ASH4))
    phrase5.insertNote(69, Note(G4))

    phrase5.insertNote(96, Note(GSH4))
    phrase5.insertNote(99, Note(GSH4))
    phrase5.insertNote(105, Note(GSH4))
    phrase5.insertNote(111, Note(GSH4))
    phrase5.insertNote(114, Note(ASH4))
    phrase5.insertNote(120, Note(G4))
    phrase5.insertNote(123, Note(E4))
    phrase5.insertNote(129, Note(E4))
    phrase5.insertNote(132, Note(C4))

    val phrase6 = Phrase(8)
    phrase6.insertNote(0, Note(C5))
    phrase6.insertNote(3, Note(A4))
    phrase6.insertNote(9, Note(E4))
    phrase6.insertNote(18, Note(E4))
    phrase6.insertNote(24, Note(F4))
    phrase6.insertNote(27, Note(C5))
    phrase6.insertNote(33, Note(C5))
    phrase6.insertNote(36, Note(F4))

    val phrase7 = Phrase(8)
    phrase7.insertNote(0, Note(G4))
    phrase7.insertNote(4, Note(F5))
    phrase7.insertNote(8, Note(F5))
    phrase7.insertNote(12, Note(F5))
    phrase7.insertNote(16, Note(E5))
    phrase7.insertNote(20, Note(D5))
    phrase7.insertNote(24, Note(C5))
    phrase7.insertNote(27, Note(A4))
    phrase7.insertNote(33, Note(G4))
    phrase7.insertNote(36, Note(E4))

    val phrase8 = Phrase(8)
    phrase8.insertNote(0, Note(G4))
    phrase8.insertNote(3, Note(D5))
    phrase8.insertNote(9, Note(D5))
    phrase8.insertNote(12, Note(D5))
    phrase8.insertNote(16, Note(C5))
    phrase8.insertNote(20, Note(B4))
    phrase8.insertNote(24, Note(G4))
    phrase8.insertNote(27, Note(E4))
    phrase8.insertNote(33, Note(E4))
    phrase8.insertNote(36, Note(C4))

    return listOf(phrase0, phrase1, phrase2, phrase3, phrase4, phrase5, phrase6, phrase7, phrase8)
}

private fun generateBassPhrases(): List<Phrase> {
    val phrase0 = Phrase(8)
    phrase0.insertNote(0, Note(D3))
    phrase0.insertNote(3, Note(D3))
    phrase0.insertNote(9, Note(D3))
    phrase0.insertNote(15, Note(D3))
    phrase0.insertNote(18, Note(D3))
    phrase0.insertNote(24, Note(G3))
    phrase0.insertNote(36, Note(G3))

    val phrase1 = Phrase(16)
    phrase1.insertNote(0, Note(G3))
    phrase1.insertNote(9, Note(E3))
    phrase1.insertNote(18, Note(C3))
    phrase1.insertNote(27, Note(F3))
    phrase1.insertNote(33, Note(G3))
    phrase1.insertNote(39, Note(FSH3))
    phrase1.insertNote(42, Note(F3))

    phrase1.insertNote(48, Note(E3))
    phrase1.insertNote(52, Note(C4))
    phrase1.insertNote(56, Note(E4))
    phrase1.insertNote(60, Note(F4))
    phrase1.insertNote(66, Note(D4))
    phrase1.insertNote(69, Note(E4))
    phrase1.insertNote(75, Note(C4))
    phrase1.insertNote(81, Note(A3))
    phrase1.insertNote(84, Note(B3))
    phrase1.insertNote(87, Note(G3))

    val phrase2 = Phrase(8)
    phrase2.insertNote(0, Note(C3))
    phrase2.insertNote(9, Note(G3))
    phrase2.insertNote(18, Note(C4))
    phrase2.insertNote(24, Note(F3))
    phrase2.insertNote(33, Note(C4))
    phrase2.insertNote(36, Note(C4))
    phrase2.insertNote(42, Note(F3))

    val phrase3 = Phrase(8)
    phrase3.insertNote(0, Note(C3))
    phrase3.insertNote(9, Note(E3))
    phrase3.insertNote(18, Note(G3))
    phrase3.insertNote(21, Note(C4))
    phrase3.insertNote(42, Note(G3))

    val phrase4 = Phrase(8)
    phrase4.insertNote(0, Note(C3))
    phrase4.insertNote(6, Note(GSH3))
    phrase4.insertNote(15, Note(ASH3))
    phrase4.insertNote(24, Note(C4))
    phrase4.insertNote(33, Note(G3))
    phrase4.insertNote(36, Note(G3))
    phrase4.insertNote(42, Note(C3))

    val phrase5 = Phrase(8)
    phrase5.insertNote(0, Note(GSH2))
    phrase5.insertNote(9, Note(DSH3))
    phrase5.insertNote(18, Note(GSH3))
    phrase5.insertNote(24, Note(G3))
    phrase5.insertNote(33, Note(C3))
    phrase5.insertNote(42, Note(G2))

    val phrase6 = Phrase(8)
    phrase6.insertNote(0, Note(C3))
    phrase6.insertNote(9, Note(FSH3))
    phrase6.insertNote(12, Note(G3))
    phrase6.insertNote(18, Note(C4))
    phrase6.insertNote(24, Note(F3))
    phrase6.insertNote(30, Note(F3))
    phrase6.insertNote(36, Note(C4))
    phrase6.insertNote(39, Note(C4))
    phrase6.insertNote(42, Note(F3))

    val phrase7 = Phrase(8)
    phrase7.insertNote(0, Note(D3))
    phrase7.insertNote(9, Note(F3))
    phrase7.insertNote(12, Note(G3))
    phrase7.insertNote(18, Note(B3))
    phrase7.insertNote(24, Note(G3))
    phrase7.insertNote(30, Note(G3))
    phrase7.insertNote(36, Note(C4))
    phrase7.insertNote(39, Note(C4))
    phrase7.insertNote(42, Note(G3))

    val phrase8 = Phrase(8)
    phrase8.insertNote(0, Note(G3))
    phrase8.insertNote(9, Note(G3))
    phrase8.insertNote(12, Note(G3))
    phrase8.insertNote(16, Note(A3))
    phrase8.insertNote(20, Note(B3))
    phrase8.insertNote(24, Note(C4))
    phrase8.insertNote(30, Note(G3))
    phrase8.insertNote(36, Note(C3))

    return listOf(phrase0, phrase1, phrase2, phrase3, phrase4, phrase5, phrase6, phrase7, phrase8)
}

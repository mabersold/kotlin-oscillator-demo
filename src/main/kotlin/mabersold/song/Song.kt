package song

class Song(val instrument: Instrument, val bpm: Int) {
    fun songStillActive(): Boolean =
        instrument.hasMorePositions()
}
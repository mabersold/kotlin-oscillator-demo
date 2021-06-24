package song

import java.lang.RuntimeException

class Phrase(val length: Int) {
    private var noteData: HashMap<Int, Note> = HashMap()

    fun insertNote(position: Int, note: Note) {
        if (position > this.length * 6 - 1) {
            throw RuntimeException("Invalid note position $position for phrase with length ${this.length}")
        }

        noteData[position] = note
    }

    fun getNote(position: Int): Note? {
        return noteData[position]
    }
}
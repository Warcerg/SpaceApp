package com.example.spaceapp.model.entities


class Note(
    var Heading: String = "Title",
    var Text: String = ""
) {

    companion object {
        fun generateNotes(): MutableList<Pair<Note, Boolean>> {
            val noteText =
                "Space is the boundless three-dimensional extent in which objects and events have relative position and direction. In classical physics, physical space is often conceived in three linear dimensions."
            val notes: MutableList<Pair<Note, Boolean>> = ArrayList()
            notes.add(Pair(Note("1st Note", "Some very important text"), false))
            notes.add(Pair(Note("2nd Note", noteText), false))
            notes.add(Pair(Note("3rd Note", noteText), false))
            notes.add(Pair(Note("4th Note", noteText), false))
            notes.add(Pair(Note("5th Note", noteText), false))
            return notes
        }
    }
}
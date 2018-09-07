package com.jshvarts.notesnavigation.presentation.notedetail

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.jshvarts.notesnavigation.domain.Note
import com.jshvarts.notesnavigation.domain.NotesManager

class NoteDetailViewModel : ViewModel() {
    private val note = MutableLiveData<Note>()

    val observableNote: LiveData<Note>
        get() = note

    fun getNote(id: Int) {
        note.value = NotesManager.getNote(id)
    }
}
package com.jshvarts.notesnavigation.presentation.notelist

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.jshvarts.notesnavigation.domain.Note
import com.jshvarts.notesnavigation.domain.NotesManager

class NoteListViewModel : ViewModel() {
    private val noteList = MutableLiveData<List<Note>>()

    val observableNoteList: LiveData<List<Note>>
        get() = noteList

    init {
        load()
    }

    fun load() {
        noteList.value = NotesManager.getNoteList()
    }
}
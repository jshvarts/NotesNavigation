package com.jshvarts.notesnavigation.presentation.deletenote

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.jshvarts.notesnavigation.domain.Note
import com.jshvarts.notesnavigation.domain.NotesManager

class DeleteNoteViewModel : ViewModel() {
    private val currentNote = MutableLiveData<Note>()

    private val deleteStatus = MutableLiveData<Boolean>()

    val observableCurrentNote: LiveData<Note>
        get() = currentNote

    val observableDeleteStatus: LiveData<Boolean>
        get() = deleteStatus

    fun deleteNote(id: Int) {
        deleteStatus.value = try {
            NotesManager.deleteNote(id)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }

    fun initNote(id: Int) {
        currentNote.value = NotesManager.getNote(id)
    }
}
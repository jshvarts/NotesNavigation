package com.jshvarts.notesnavigation.presentation.editnote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation.findNavController
import com.jshvarts.notesnavigation.R
import com.jshvarts.notesnavigation.domain.Note
import com.jshvarts.notesnavigation.presentation.closeSoftKeyboard
import com.jshvarts.notesnavigation.presentation.notedetail.NoteDetailFragmentArgs.fromBundle
import kotlinx.android.synthetic.main.edit_note_fragment.*

class EditNoteFragment : Fragment() {

    private lateinit var viewModel: EditNoteViewModel

    private val noteId by lazy {
        fromBundle(arguments).noteId
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.edit_note_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(EditNoteViewModel::class.java)
        viewModel.observableCurrentNote.observe(this, Observer { currentNote ->
            currentNote?.let { initCurrentNote(currentNote) }
        })
        viewModel.observableEditStatus.observe(this, Observer { editStatus ->
            editStatus?.let { render(editStatus) }
        })

        viewModel.initNote(noteId)

        setupEditNoteSubmitHandling()
    }

    private fun initCurrentNote(note: Note) {
        editNoteText.setText(note.text)
    }

    private fun render(editStatus: Boolean) {
        when (editStatus) {
            true -> {
                view?.let {
                    findNavController(it).popBackStack()
                }
            }
            false -> editNoteText.error = getString(R.string.error_validating_note)
        }
    }

    private fun setupEditNoteSubmitHandling() {
        editNoteText.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val args = fromBundle(arguments)
                viewModel.editNote(noteId, v.text.toString())
                v.closeSoftKeyboard()
                true
            } else {
                false
            }
        }
    }
}
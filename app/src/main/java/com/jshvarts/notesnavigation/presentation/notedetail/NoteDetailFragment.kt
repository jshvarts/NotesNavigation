package com.jshvarts.notesnavigation.presentation.notedetail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.jshvarts.notesnavigation.R
import com.jshvarts.notesnavigation.domain.Note
import com.jshvarts.notesnavigation.presentation.notedetail.NoteDetailFragmentArgs.fromBundle
import com.jshvarts.notesnavigation.presentation.notedetail.NoteDetailFragmentDirections.actionNoteDetailToDeleteNote
import com.jshvarts.notesnavigation.presentation.notedetail.NoteDetailFragmentDirections.actionNoteDetailToEditNote
import kotlinx.android.synthetic.main.note_detail_fragment.*

class NoteDetailFragment : Fragment() {

    private lateinit var viewModel: NoteDetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.note_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(NoteDetailViewModel::class.java)
        viewModel.observableNote.observe(this, Observer { note ->
            note?.let { render(note) } ?: renderNoteNotFound()
        })

        val args = fromBundle(arguments)
        editNoteButton.setOnClickListener {
            val navDirections = actionNoteDetailToEditNote().setNoteId(args.noteId)
            it.findNavController().navigate(navDirections)
        }

        deleteNoteButton.setOnClickListener {
            val navDirections = actionNoteDetailToDeleteNote().setNoteId(args.noteId)
            it.findNavController().navigate(navDirections)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getNote(fromBundle(arguments).noteId)
    }

    private fun render(note: Note) {
        noteId.text = String.format(getString(R.string.note_detail_id), note.id)
        noteText.text = String.format(getString(R.string.note_detail_text), note.text)
    }

    private fun renderNoteNotFound() {
        noteId.visibility = View.GONE
        noteText.visibility = View.GONE
        view?.let {
            Snackbar.make(it, R.string.error_loading_note, Snackbar.LENGTH_LONG).show()
        }
    }
}
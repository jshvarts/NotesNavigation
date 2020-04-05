package com.jshvarts.notesnavigation.presentation.notedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.jshvarts.notesnavigation.R
import com.jshvarts.notesnavigation.domain.Note
import kotlinx.android.synthetic.main.note_detail_fragment.*

class NoteDetailFragment : Fragment() {

    private lateinit var viewModel: NoteDetailViewModel

    private val args by navArgs<NoteDetailFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.note_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(NoteDetailViewModel::class.java)
        viewModel.observableNote.observe(viewLifecycleOwner, Observer { note ->
            note?.let { render(note) } ?: renderNoteNotFound()
        })

        editNoteButton.setOnClickListener {
            val action = NoteDetailFragmentDirections.actionNoteDetailToEditNote(args.noteId)
            findNavController(it).navigate(action)
        }

        deleteNoteButton.setOnClickListener {
            val action = NoteDetailFragmentDirections.actionNoteDetailToDeleteNote(args.noteId)
            findNavController(it).navigate(action)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getNote(args.noteId)
    }

    private fun render(note: Note) {
        noteIdView.text = String.format(getString(R.string.note_detail_id), note.id)
        noteText.text = String.format(getString(R.string.note_detail_text), note.text)
    }

    private fun renderNoteNotFound() {
        noteIdView.visibility = View.GONE
        noteText.visibility = View.GONE
        view?.let {
            Snackbar.make(it, R.string.error_loading_note, Snackbar.LENGTH_LONG).show()
        }
    }
}

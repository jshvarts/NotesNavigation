package com.jshvarts.notesnavigation.presentation.deletenote

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
import com.jshvarts.notesnavigation.presentation.deletenote.DeleteNoteFragmentArgs.fromBundle
import kotlinx.android.synthetic.main.delete_note_fragment.*

class DeleteNoteFragment : Fragment() {

    private lateinit var viewModel: DeleteNoteViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.delete_note_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DeleteNoteViewModel::class.java)
        viewModel.observableCurrentNote.observe(this, Observer { currentNote ->
            currentNote?.let { initCurrentNote(currentNote) }
        })
        viewModel.observableDeleteStatus.observe(this, Observer { deleteStatus ->
            deleteStatus?.let { render(deleteStatus) }
        })

        val args = fromBundle(arguments)
        viewModel.initNote(args.noteId)

        cancelDeleteButton.setOnClickListener {
            it.findNavController().navigateUp()
        }

        confirmDeleteButton.setOnClickListener {
            viewModel.deleteNote(args.noteId)
        }
    }

    private fun initCurrentNote(note: Note) {
        noteId.text = String.format(getString(R.string.note_detail_id), note.id)
        noteText.text = String.format(getString(R.string.note_detail_text), note.text)
    }

    private fun render(deleteStatus: Boolean) {
        when (deleteStatus) {
            true -> view?.findNavController()?.popBackStack(R.id.notesFragment, false)
            else -> Snackbar.make(confirmDeleteButton, R.string.error_deleting_note, Snackbar.LENGTH_LONG).show()
        }
    }
}
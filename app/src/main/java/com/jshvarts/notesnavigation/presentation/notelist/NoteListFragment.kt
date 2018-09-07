package com.jshvarts.notesnavigation.presentation.notelist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.jshvarts.notesnavigation.R
import com.jshvarts.notesnavigation.domain.Note
import com.jshvarts.notesnavigation.presentation.notelist.NoteListFragmentDirections.actionNotesToAddNote
import kotlinx.android.synthetic.main.note_list_fragment.*


class NoteListFragment : Fragment() {

    private val clickListener: ClickListener = this::onNoteClicked

    private val recyclerViewAdapter = NoteAdapter(clickListener)

    private lateinit var viewModel: NoteListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.note_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        viewModel = ViewModelProviders.of(this).get(NoteListViewModel::class.java)
        viewModel.observableNoteList.observe(this, Observer { notes ->
            notes?.let { render(notes) }
        })

        fab.setOnClickListener {
            it.findNavController().navigate(actionNotesToAddNote())
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.load()
    }

    private fun render(noteList: List<Note>) {
        recyclerViewAdapter.updateNotes(noteList)
        if (noteList.isEmpty()) {
            notesRecyclerView.visibility = View.GONE
            notesNotFound.visibility = View.VISIBLE
        } else {
            notesRecyclerView.visibility = View.VISIBLE
            notesNotFound.visibility = View.GONE
        }
    }

    private fun onNoteClicked(note: Note) {
        view?.let {
            Snackbar.make(it, "note ${note.id} clicked", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun setupRecyclerView() {
        notesRecyclerView.layoutManager = LinearLayoutManager(this.context)
        notesRecyclerView.adapter = recyclerViewAdapter
        notesRecyclerView.setHasFixedSize(true)
    }
}
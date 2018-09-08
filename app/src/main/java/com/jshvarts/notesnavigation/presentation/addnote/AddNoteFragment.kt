package com.jshvarts.notesnavigation.presentation.addnote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.jshvarts.notesnavigation.R
import com.jshvarts.notesnavigation.presentation.closeSoftKeyboard
import kotlinx.android.synthetic.main.add_note_fragment.*

class AddNoteFragment : Fragment() {

    private lateinit var viewModel: AddNoteViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.add_note_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AddNoteViewModel::class.java)
        viewModel.observableStatus.observe(this, Observer { status ->
            status?.let { render(status) }
        })

        addNoteText.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.addNote(v.text.toString())
                v.closeSoftKeyboard()
                true
            } else {
                false
            }
        }
    }

    private fun render(status: Boolean) {
        when (status) {
            true -> {
                view?.let { v ->
                    v.findNavController().navigateUp()
                }
            }
            false -> addNoteText.error = getString(R.string.error_validating_note)
        }
    }
}
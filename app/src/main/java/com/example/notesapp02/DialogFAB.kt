package com.example.notesapp02

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import java.text.SimpleDateFormat
import java.util.*


class DialogFAB : DialogFragment() {

    private val viewModel by lazy {
        ViewModelProvider(requireActivity()).get(NoteViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detailed_note_fragment, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        return dialog
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            dialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title = view.findViewById<AppCompatEditText>(R.id.dialogTitle)
        val body = view.findViewById<AppCompatEditText>(R.id.dialogBody)
        val save = view.findViewById<AppCompatImageView>(R.id.check)
        val titleText = title.text
        val bodyText = body.text
        val sdf = SimpleDateFormat("MMMM dd yyyy,  hh:mm a")
        val currentDate = sdf.format(Date())
        save.setOnClickListener {
            if (titleText.isNullOrBlank().not() and bodyText.isNullOrBlank().not()) {
                val note = Note(title = titleText.toString(), text = bodyText.toString(), date = currentDate)
                viewModel.insert(note = note)
                it.context.hideKeyboard(it)
                dismissAllowingStateLoss()
            } else {
                Toast.makeText(requireContext(), "Please enter valid input", Toast.LENGTH_SHORT)
                    .show()
            }

        }

        view.findViewById<AppCompatImageView>(R.id.delete).setOnClickListener {
            if (titleText.isNullOrBlank().not() and bodyText.isNullOrBlank().not()) {
                val note = Note(
                    title = titleText.toString(),
                    text = bodyText.toString(),
                    uid = id,
                    date = currentDate
                )
                viewModel.delete(note)
                it.context.hideKeyboard(it)
                requireActivity().onBackPressed()
            }

        }

        view.findViewById<AppCompatImageView>(R.id.back).setOnClickListener {
            it.context.hideKeyboard(it)
            requireActivity().onBackPressed()
        }

    }
}
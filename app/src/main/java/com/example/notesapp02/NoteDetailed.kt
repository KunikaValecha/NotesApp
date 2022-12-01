package com.example.notesapp02

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.SimpleDateFormat
import java.util.*

class NoteDetailed : BottomSheetDialogFragment() {
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = requireArguments()
        val id = args.getInt("ID")
        val title = args.getString("TITLE")
        val body = args.getString("BODY")
        view.findViewById<AppCompatEditText>(R.id.dialogTitle).setText(title)
        view.findViewById<AppCompatEditText>(R.id.dialogBody).setText(body)
        val titleText = view.findViewById<AppCompatEditText>(R.id.dialogTitle).text
        val bodyText = view.findViewById<AppCompatEditText>(R.id.dialogBody).text
        val sdf = SimpleDateFormat("MMMM dd yyyy,  hh:mm a")
        val currentDate = sdf.format(Date())
        view.findViewById<AppCompatImageView>(R.id.back).setOnClickListener {
            it.context.hideKeyboard(it)
            requireActivity().onBackPressed()
        }
        view.findViewById<AppCompatImageView>(R.id.check).setOnClickListener {
            if (titleText.isNullOrBlank().not() and bodyText.isNullOrBlank().not()) {
                val note = Note(
                    title = titleText.toString(),
                    text = bodyText.toString(),
                    uid = id,
                    date = currentDate
                )
                viewModel.edit(note = note)
                dismissAllowingStateLoss()
                it.context.hideKeyboard(it)
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

    }


}




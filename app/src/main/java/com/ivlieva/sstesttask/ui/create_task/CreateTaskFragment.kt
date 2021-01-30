package com.ivlieva.sstesttask.ui.create_task

import android.app.TimePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.ivlieva.sstesttask.R
import com.ivlieva.sstesttask.entyty.Task
import com.ivlieva.sstesttask.util.*
import com.theartofdev.edmodo.cropper.CropImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.add_task_fragment.*
import kotlinx.android.synthetic.main.choice_upload.*
import kotlinx.android.synthetic.main.create_task_fragment.buttonSave
import kotlinx.android.synthetic.main.create_task_fragment.editTextDescription
import kotlinx.android.synthetic.main.create_task_fragment.editTextTitle
import kotlinx.android.synthetic.main.create_task_fragment.progressBar2
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.sql.Timestamp
import java.time.LocalTime
import java.util.*

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class CreateTaskFragment : Fragment() {

    companion object {
        fun newInstance() = CreateTaskFragment()
    }

    private val viewModel: CreateTaskViewModel by viewModels()
    private lateinit var mBottomSheetBehavior: BottomSheetBehavior<*>
    private val attach: MutableList<Uri> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_task_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeObservers()
        buttonSave.setOnClickListener {
            viewModel.createTask(getNewTask())
        }

        mBottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet_choice)
        mBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        btn_attach.setOnClickListener { attach() }

        var beginningTime: LocalTime = LocalTime.now()
        var endTime: LocalTime = LocalTime.now()
        editTextAddStartTime.text = formatTime(beginningTime)
        editTextAddFinishTime.text = formatTime(endTime)

        editTextAddStartTime.setOnClickListener {
            onClickTime { time ->
                run {
                    beginningTime = time
                    editTextAddStartTime.text = formatTime(beginningTime)
                }
            }
        }
        editTextAddFinishTime.setOnClickListener {
            onClickTime { time ->
                run {
                    endTime = time
                    editTextAddFinishTime.text = formatTime(endTime)

                }

            }
        }
    }

    private fun attach() {
        mBottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

        btn_attach_file.setOnClickListener { attachFile() }
        btn_attach_image.setOnClickListener { attachImage() }
    }

    private fun attachFile() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        startActivityForResult(intent, PICK_FILE_REQUEST_CODE)
    }

    private fun attachImage() {
        CropImage.activity()
            .start(APP_ACTIVITY, this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            when (requestCode) {
                CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                    val uri = CropImage.getActivityResult(data).uri
                    attach.add(uri)
                }
                PICK_FILE_REQUEST_CODE -> {
                    val uri = data.data
                    uri?.let {
                        attach.add(it)
                    }
                }
            }
        }
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is DataState.Success<Task> -> {
                    displayProgressBar(false)
                }
                is DataState.Error -> {
                    displayProgressBar(false)
                    displayError(it.exception.message)
                }
                is DataState.Loading -> {
                    displayProgressBar(true)
                }
            }
        })
    }

    private fun getNewTask(): Task {
        var calendar: Calendar = Calendar.getInstance()
        val bundle: Bundle? = arguments
        if (bundle != null) {
            calendar = bundle.get("date") as Calendar
        }

        val dateStart: Timestamp? =
            Timestamp(calendar.timeInMillis + timeStrToMillis(editTextAddStartTime.text.toString())) // editTextStartTime
        val dateFinish: Timestamp? =
            Timestamp(calendar.timeInMillis + timeStrToMillis(editTextAddFinishTime.text.toString())) // editTextFinishTime
        val name: String = editTextTitle.text.toString()
        val description: String = editTextDescription.text.toString()
        return Task(
            dateStart = dateStart,
            dateFinish = dateFinish,
            name = name,
            description = description,
            attachments = attach
        )
    }

    private fun displayError(message: String?) {
        if (message != null) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "Unknown error", Toast.LENGTH_LONG).show()
        }
    }

    private fun displayProgressBar(isDisplay: Boolean) {
        progressBar2.visibility = if (isDisplay) View.VISIBLE else View.GONE
    }

    private fun onClickTime(function: (LocalTime) -> Unit) {
        // Get Current Time
        val now = LocalTime.now()
        val mHour = now.hour
        val mMinute = now.minute

        // Launch Time Picker Dialog
        TimePickerDialog(
            context,
            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                function(LocalTime.of(hourOfDay, minute))
            },
            mHour,
            mMinute,
            true
        ).show()
    }
}
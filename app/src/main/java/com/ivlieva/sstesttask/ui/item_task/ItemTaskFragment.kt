package com.ivlieva.sstesttask.ui.item_task

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ivlieva.sstesttask.R
import com.ivlieva.sstesttask.entyty.Task
import com.ivlieva.sstesttask.ui.AttachAdapter
import com.ivlieva.sstesttask.util.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.item_task_fragment.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.io.File


@AndroidEntryPoint
@ExperimentalCoroutinesApi
class ItemTaskFragment : Fragment() {

    companion object {
        fun newInstance() = ItemTaskFragment()
    }

    private val viewModel: ItemTaskViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.item_task_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeObservers()
        attachTaskRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val bundle: Bundle? = arguments
//        val task: Task
//        if (bundle != null) {
//            task = bundle.get("task") as Task
//            setFieldTask(task)
//            setAdapter(task)
//        }
        if (bundle != null) {
            viewModel.getCurrentTask(bundle.getLong("id"))
        }
    }

    private fun setFieldTask(task: Task) {
        textViewTaskTitle.text = task.name
        val taskDate = task.dateStart.toString() + " - " + task.dateFinish.toString()
        textViewTaskDate.text = taskDate
        textViewTaskDescription.text = task.description
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is DataState.Success<Task> -> {
                    displayProgressBar(false)
                    displayTask(it.data)
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

    private fun displayError(message: String?) {
        if (message != null) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "Unknown error", Toast.LENGTH_LONG).show()
        }
    }

    private fun displayProgressBar(isDisplay: Boolean) {
        progressBarForItemTask.visibility = if (isDisplay) View.VISIBLE else View.GONE
    }

    private fun displayTask(task: Task) {
        setFieldTask(task)
        setAdapter(task)
    }

    private fun setAdapter(task: Task) {
        attachTaskRecyclerView.adapter =
            AttachAdapter(task.attachments, object : AttachAdapter.Listener {
                override fun onItemClick(uri: Uri) {
                    val photoURI = FileProvider.getUriForFile(
                        context!!,
                        context!!.applicationContext.packageName.toString() + ".provider",
                        File(uri.path)
                    )
                    val intent = Intent()
                    intent.action = Intent.ACTION_VIEW
                    intent.setDataAndType(photoURI, "*/*")
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    startActivity(intent)
                }
            })
    }

}
package com.ivlieva.sstesttask.ui.list_tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ivlieva.sstesttask.R
import com.ivlieva.sstesttask.entyty.Task
import com.ivlieva.sstesttask.util.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.list_tasks_fragment.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.*

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class ListTasksFragment : Fragment() {

    companion object {
        fun newInstance() = ListTasksFragment()
    }

    private val viewModel: ListTasksViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_tasks_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeObservers()
        recyclerViewTasks.layoutManager = LinearLayoutManager(context)
        val listTasksStateEvent = ListTasksStateEvent.GetTaskByDateEvents
        viewModel.setStateEvent(listTasksStateEvent)

        var selectCalendar: Calendar = Calendar.getInstance()
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectCalendar = GregorianCalendar(year, month, dayOfMonth)
            listTasksStateEvent.date = selectCalendar.timeInMillis
            viewModel.setStateEvent(listTasksStateEvent)
        }

        fab.setOnClickListener {
            findNavController().navigate(
                R.id.action_listTasksFragment_to_createTaskFragment,
                bundleOf("date" to selectCalendar)
            )
        }
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is DataState.Success<List<Task>> -> {
                    displayProgressBar(false)
                    displayTasks(it.data)
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
        progressBar.visibility = if (isDisplay) View.VISIBLE else View.GONE
    }

    private fun displayTasks(tasks: List<Task>) {
        val adapter = TasksAdapter(tasks, object : TasksAdapter.Listener {
            override fun onItemClick(task: Task) {
                findNavController().navigate(
                    R.id.action_listTasksFragment_to_itemTaskFragment,
                    bundleOf("task" to task, "id" to task.id)
                )
            }
        })
        recyclerViewTasks.adapter = adapter
    }

}
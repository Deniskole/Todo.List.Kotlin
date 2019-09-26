package com.example.todolist


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.adapter.TasksAdapter
import kotlinx.android.synthetic.main.dialog_input.view.*
import kotlinx.android.synthetic.main.fragment_tasks.*

class TasksFragment : Fragment() {
    private var tasks = mutableListOf<String>()
    private val adapter = TasksAdapter(tasks)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        tasks.add("Java")
        tasks.add("Kotlin")
        tasks.add("Android")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tasks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(DividerItemDecoration(context, layoutManager.orientation))

        adapter.listener = object : TasksAdapter.Listener {
            override fun onViewHolderClick(
                holder: RecyclerView.ViewHolder,
                position: Int, id: Int
            ) {
                showCreateTaskDialog(TaskState.EDIT, position)
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.addItem -> showCreateTaskDialog(TaskState.NEW, 0)
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    @SuppressLint("InflateParams")
    private fun showCreateTaskDialog(taskState: TaskState, position: Int) {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_input, null)
        val dialog = AlertDialog.Builder(context)
        when (taskState) {
            TaskState.NEW, TaskState.EDIT -> {
                with(dialog) {
                    setView(view)
                    if (taskState == TaskState.NEW) {
                        setTitle(R.string.new_task)
                        setPositiveButton(R.string.add_new) { _, _ ->
                            addTask(view.descriptionEditText.text.toString())
                        }
                    } else {
                        if (tasks.size >= 1) view.descriptionEditText.setText(tasks[position])
                        setTitle(R.string.edit_task)
                        setNeutralButton(R.string.delete_task) { dialog, which ->
                            showCreateTaskDialog(TaskState.DELETE, position)
                        }
                        setPositiveButton(R.string.save_task) { _, _ ->
                            editTask(position, view.descriptionEditText.text.toString())
                        }
                    }
                    setNegativeButton(R.string.cancel_task) { dialog, _ ->
                        dialog.cancel()
                    }

                }
                val alertDialog = dialog.create()
                alertDialog.show()
                val button = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE)
                button.isEnabled = view.descriptionEditText.text.toString().isNotEmpty()
                view.descriptionEditText.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable) = Unit
                    override fun beforeTextChanged(
                        s: CharSequence, start: Int,
                        count: Int, after: Int
                    ) = Unit

                    override fun onTextChanged(
                        s: CharSequence,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        button.isEnabled = s.isNotEmpty()
                    }
                })
            }

            TaskState.DELETE -> {
                with(dialog) {
                    setTitle(R.string.delete_task)
                    setNegativeButton(R.string.cancel_task) { _, _ ->
                        showCreateTaskDialog(TaskState.EDIT, position)
                    }
                    setPositiveButton(R.string.delete_task) { _, _ ->
                        deleteTask(position)
                    }
                    dialog.show()
                }
            }
        }
    }

    private fun addTask(task: String) {
        tasks.add(task)
        adapter.notifyDataSetChanged()
    }

    private fun deleteTask(position: Int) {
        tasks.removeAt(position)
        adapter.notifyDataSetChanged()
    }

    private fun editTask(position: Int, task: String) {
        tasks[position] = task
        adapter.notifyDataSetChanged()
    }
}

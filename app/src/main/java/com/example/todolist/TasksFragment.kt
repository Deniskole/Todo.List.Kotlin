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
import com.example.todolist.adapter.TasksAdapter
import kotlinx.android.synthetic.main.dialog_input.view.*
import kotlinx.android.synthetic.main.fragment_tasks.*

class TasksFragment : Fragment() {
    private var tasks = mutableListOf<String>()
    private val adapter = TasksAdapter(tasks)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.addItem -> showCreateTaskDialog()
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    @SuppressLint("InflateParams")
    private fun showCreateTaskDialog() {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_input, null)

        val dialog = AlertDialog.Builder(context)
            .setView(view)
            .setTitle(R.string.add_new_task)
            .setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.cancel()
            }
            .setPositiveButton(R.string.add_new) { _, _ ->
                val taskStr = view.descriptionEditText.text.toString()
                this.addNewTask(taskStr)
            }.create()

        dialog.setOnShowListener {
            val button = dialog.getButton(DialogInterface.BUTTON_POSITIVE)
            button.isEnabled = false

            view.descriptionEditText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable) = Unit
                override fun beforeTextChanged(
                    s: CharSequence, start: Int, count: Int, after: Int
                ) = Unit

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    button.isEnabled = s.isNotEmpty()
                }
            })
        }

        dialog.show()
    }

    private fun addNewTask(task: String) {
        tasks.add(task)

        adapter.notifyDataSetChanged()
    }
}

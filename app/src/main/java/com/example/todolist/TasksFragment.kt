package com.example.todolist


import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.adapter.TaskAdapter
import kotlinx.android.synthetic.main.dialog_add_new.view.*
import kotlinx.android.synthetic.main.fragment_tasks.*

class TasksFragment : Fragment() {
    var tasks: ArrayList<String> = ArrayList()
    private var adapter: TaskAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tasks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        tasksListRecyclerView.layoutManager = layoutManager
        tasksListRecyclerView.addItemDecoration(dividerItemDecoration)

        val adapter = context?.let { TaskAdapter(tasks, it) }
        tasksListRecyclerView.adapter = adapter
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.addItem -> {
                showCreateCategoryDialog()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showCreateCategoryDialog() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_new, null)
        val builder = AlertDialog.Builder(context)
            .setView(dialogView)
            .setTitle(R.string.add_new_task)
            .setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.cancel()
            }
            .setPositiveButton(R.string.add_new) { _, _ ->
                val taskStr = dialogView.descriptionTextView.text.toString()
                this.addNewTask(taskStr)
            }

        val dialog: AlertDialog = builder.show()
        val theButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE)
        theButton.isEnabled = false
        dialogView.descriptionTextView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                theButton.isEnabled = s?.isNotEmpty()!!
            }
        })
    }

    private fun addNewTask(task: String) {
        tasks.add(task)
        adapter?.notifyDataSetChanged()
    }
}

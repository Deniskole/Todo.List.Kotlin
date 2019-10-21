package com.example.todolist.screens.tasks


import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.adapter.OnViewHolderClickListener
import com.example.todolist.adapter.OnViewHolderLongClickListener
import com.example.todolist.adapter.TasksAdapter
import com.example.todolist.common.di.scenes.TasksModule
import com.example.todolist.model.Task
import com.example.todolist.screens.tasks.TasksContract.Action.*
import com.example.todolist.util.Constants.Companion.FILTER_KEY
import kotlinx.android.synthetic.main.dialog_input.view.*
import kotlinx.android.synthetic.main.fragment_tasks.*
import toothpick.Scope
import toothpick.Toothpick
import java.io.Serializable
import javax.inject.Inject


class TasksFragment : Fragment(),
    OnViewHolderClickListener, OnViewHolderLongClickListener, TasksContract.View {

    @Inject lateinit var presenter: TasksPresenter
    private val adapter = TasksAdapter(this)
    lateinit var filter: TasksContract.Storage.Filter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        filter = arguments?.getSerializable(FILTER_KEY) as TasksContract.Storage.Filter
        val scope: Scope = Toothpick.openScopes(requireContext().applicationContext, this)
        scope.installModules(TasksModule(this))
        Toothpick.inject(this, scope)

        setHasOptionsMenu(true)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)

        if (!hidden) {
            presenter.start(filter)
        }
    }

    fun newInstance(filter: TasksContract.Storage.Filter): TasksFragment {
        val myFragment = TasksFragment()
        val args = Bundle()
        args.putSerializable(FILTER_KEY, filter as Serializable)
        myFragment.arguments = args
        return myFragment
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(com.example.todolist.R.layout.fragment_tasks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                layoutManager.orientation
            )
        )

        presenter.start(filter)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(com.example.todolist.R.menu.main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            com.example.todolist.R.id.addItem -> actionTaskDialog(NEW)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewHolderClick(holder: RecyclerView.ViewHolder, position: Int, id: Int) {
        when (id) {
            com.example.todolist.R.id.taskFavoriteImageView -> {
                val task = adapter.getTask(position)
                presenter.favorite(task.id, task.title, task.descriptions, task.favorite)
            }
            com.example.todolist.R.id.container -> actionTaskDialog(EDIT, position)
        }
    }

    override fun onViewHolderLongClick(
        holder: RecyclerView.ViewHolder, position: Int, id: Int
    ): Boolean {
        actionTaskDialog(DELETE, position)
        return true
    }

    override fun showData(tasksList: List<Task>) = adapter.setTasks(tasksList)

    private fun actionTaskDialog(action: TasksContract.Action, position: Int? = null) {
        val task: Task? = position?.let { adapter.getTask(it) }
        var onShowListener: DialogInterface.OnShowListener? = null

        val builder = AlertDialog.Builder(context).setTitle(action.titleResId)

        when (action) {
            NEW, EDIT -> {
                val view =
                    View.inflate(context, com.example.todolist.R.layout.dialog_input, null)
                builder.apply {
                    setView(view)

                    if (task == null) {
                        setPositiveButton(com.example.todolist.R.string.add) { _, _ ->
                            presenter.insert(
                                view.titleEditText.text.toString(),
                                view.descriptionEditText.text.toString()
                            )
                        }
                    } else {
                        view.titleEditText.setText(task.title)
                        view.descriptionEditText.setText(task.descriptions)

                        setPositiveButton(com.example.todolist.R.string.save) { _, _ ->
                            presenter.update(
                                task.id,
                                view.titleEditText.text.toString(),
                                view.descriptionEditText.text.toString(),
                                task.favorite
                            )
                        }
                    }
                }
                    .setNegativeButton(com.example.todolist.R.string.cancel) { dialog, _ -> dialog.cancel() }

                onShowListener = DialogInterface.OnShowListener { dialog ->
                    if (dialog !is AlertDialog) return@OnShowListener

                    val button = dialog.getButton(DialogInterface.BUTTON_POSITIVE)
                    button.isEnabled = view.descriptionEditText.text.toString().isNotEmpty()
                    view.descriptionEditText.addTextChangedListener {
                        button.isEnabled = !it.isNullOrEmpty()
                    }
                }
            }
            DELETE -> {
                builder.setNegativeButton(com.example.todolist.R.string.cancel) { _, _ -> }
                    .setPositiveButton(com.example.todolist.R.string.delete) { _, _ ->
                        if (position != null) presenter.delete(adapter.getTask(position))
                    }
            }
        }

        with(builder.create()) {
            onShowListener?.also { setOnShowListener(it) }
            show()
        }
    }
}

package com.ma.development.todo_app.presentation.tasksfeed

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ma.development.todo_app.R
import com.ma.development.todo_app.databinding.CustomBottomSheetDialogBinding
import com.ma.development.todo_app.databinding.FragmentTasksFeedBinding
import com.ma.development.todo_app.domain.model.Task
import com.ma.development.todo_app.utils.*
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TasksFeedFragment : Fragment(), TasksFeedAdapter.OnClickListener {

    private val viewModel: TasksFeedViewModel by viewModels()
    private val binding by lazy { FragmentTasksFeedBinding.inflate(layoutInflater) }
    private val tasksFeedAdapter by lazy { TasksFeedAdapter(this) }
    var seeAllFlag : Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.lifecycleOwner = viewLifecycleOwner
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModdel = viewModel
        observeViewModel()
        setUpViews()
    }

    private fun setUpViews() {
        setUpRecyclerView()
        setUpFab()
        setUpSeeMoreBtn()
    }

    private fun setUpRecyclerView() {
        binding.tasksRecyclerView.apply {

            this.adapter = tasksFeedAdapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy > 0) {
                        binding.addFab.hide()
                        binding.seeMoreBtn.hide()
                    } else {
                        binding.addFab.show()
                        if (seeAllFlag)
                            binding.seeMoreBtn.show()
                    }
                    super.onScrolled(recyclerView, dx, dy)
                }
            })
        }
    }

    private fun setUpSeeMoreBtn() {
        binding.seeMoreBtn.setOnClickListener {
            viewModel.getTasks()
        }
    }

    private fun setUpFab() {
        binding.addFab.setOnClickListener { showBottomSheetDialog() }
    }

    private fun observeViewModel() {
        viewModel.apply {
            tasks.observe(viewLifecycleOwner) {
                tasksFeedAdapter.submitList(it)
                if (firstTime) {
                    skip = it.size
                    getTasks()
                }
            }
            initialLoadingVisibility.observe(viewLifecycleOwner){
                if (!it)
                    binding.progressCircular.hide()
            }

            error.observe(viewLifecycleOwner) {
                showSnackBar(binding.root, getString(R.string.network_error_message))
            }
            seeAllVisibility.observe(viewLifecycleOwner) {
                seeAllFlag = it
                if (it)
                    binding.seeMoreBtn.show()
                else
                    binding.seeMoreBtn.hide()
            }

        }
    }
    override fun onItemClick(taskId: String) {
        val bundle = Bundle().apply { putString(KEY_TASK_ID, taskId) }
        findNavController().navigate(R.id.action_tasksFeedFragment_to_taskEditFragment, bundle)
    }

    override fun onRemoveIconClick(task: Task) {
        showDeleteDialog(requireActivity(), layoutInflater) { viewModel.deleteTask(task) }
    }


    private fun showBottomSheetDialog() {

        val bottomSheetDialogBinding = CustomBottomSheetDialogBinding.inflate(layoutInflater)

        bottomSheetDialog = BottomSheetDialog(requireContext(),R.style.DialogStyle).apply {
            window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            setContentView(bottomSheetDialogBinding.root)
            show()
        }

        bottomSheetDialogBinding.saveTaskBtn.setOnClickListener {
            val description = bottomSheetDialogBinding.taskDescriptionEditTxt.text.toString()
            addTask(description)
        }
    }

    private lateinit var bottomSheetDialog: BottomSheetDialog

    private fun addTask(description: String) {
        if (description.isNotEmpty()) {
            viewModel.addTask(description)
            bottomSheetDialog.dismiss()
        } else
            showSnackBar(binding.root, getString(R.string.error_empty_description))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_filters, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.completed_filter -> {
                viewModel.filter(FilterType.COMPLETED)
                true
            }
            R.id.inComplete_filter -> {
                viewModel.filter(FilterType.INCOMPLETE)
                true
            }
            R.id.all_filter -> {
                viewModel.filter(FilterType.ALL)
                true
            }
            else -> true
        }
    }


}
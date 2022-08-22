package com.ma.development.todo_app.presentation.taskedit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ma.development.todo_app.R
import com.ma.development.todo_app.databinding.FragmentTaskEditBinding
import com.ma.development.todo_app.domain.model.Task
import com.ma.development.todo_app.presentation.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskEditFragment : Fragment() {

    private val viewModel: TaskEditViewModel by viewModels()
    private val binding by lazy { FragmentTaskEditBinding.inflate(layoutInflater) }
    private var taskId = ""
    private lateinit var task: Task

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        taskId = arguments?.getString(KEY_TASK_ID, "") ?: ""
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //setUpUi()
        observeViewModel()
    }

    private fun setUpUi() {
        setUpRadioButton()
        setUpSaveButton()
        setUpDeleteButton()
    }

    private fun setUpRadioButton() {
        if (task.completed)
            binding.radioBtnCompleted.isChecked = true
        else
            binding.radioBtnIncomplete.isChecked = true
    }

    private fun setUpSaveButton() {
        binding.saveUpdateBtn.setOnClickListener {
            binding.progressCircular.show()
            val isCompleted = binding.radioBtnCompleted.isChecked
            viewModel.updateTaskStatus(isCompleted = isCompleted, taskId)
        }
    }

    private fun setUpDeleteButton() {
        binding.deleteBtn.setOnClickListener { onDeleteButtonClick() }
    }

    private fun observeViewModel() {
        viewModel.apply {
            getTask(taskId).observe(viewLifecycleOwner) {
                it?.let {
                    task = it
                    binding.task = task
                    setUpUi()
                }
            }

            savedSuccessfully.observe(viewLifecycleOwner) {
                binding.progressCircular.hide()
                if (it) showSnackBar(binding.root, "Task status is saved")

            }

            deletedSuccessfully.observe(viewLifecycleOwner) {
                binding.progressCircular.hide()
                findNavController().popBackStack(R.id.tasksFeedFragment, false)
            }

            error.observe(viewLifecycleOwner){
                binding.progressCircular.hide()
                showSnackBar(binding.root, getString(R.string.network_error_message))
            }

        }
    }

    private fun onDeleteButtonClick() {
        showDeleteDialog(requireContext(), layoutInflater) {
            binding.progressCircular.show()
            viewModel.deleteTask(task)
        }
    }

}
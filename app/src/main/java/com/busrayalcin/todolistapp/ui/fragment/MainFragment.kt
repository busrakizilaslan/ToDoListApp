package com.busrayalcin.todolistapp.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.busrayalcin.todolistapp.R
import com.busrayalcin.todolistapp.databinding.FragmentMainBinding
import com.busrayalcin.todolistapp.ui.adapter.TaskAdapter
import com.busrayalcin.todolistapp.ui.viewmodel.MainFragmentViewModel
import com.busrayalcin.todolistapp.utils.doNavigate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(),SearchView.OnQueryTextListener {
    private lateinit var binding : FragmentMainBinding
    private lateinit var viewModel : MainFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        val tempViewModel: MainFragmentViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_main,container,false)
        binding.mainFragment = this
        binding.mainToolBarTitle = "ToDos"
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        viewModel.taskList.observe(viewLifecycleOwner){
            val adapter = TaskAdapter(requireContext(),it,viewModel)
            binding.taskAdapter = adapter
        }
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu,menu)
        val item = menu.findItem(R.id.action_search)
        val searchView = item.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_exit){
            android.os.Process.killProcess(android.os.Process.myPid())
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        viewModel.search(query)
        return true
    }

    override fun onQueryTextChange(newText: String): Boolean {
        viewModel.search(newText)
        return true
    }

    fun fabClick(view:View){
        Navigation.doNavigate(view,R.id.taskFragment)
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadTasks()
    }


}
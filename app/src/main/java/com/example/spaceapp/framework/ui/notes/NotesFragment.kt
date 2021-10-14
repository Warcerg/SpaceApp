package com.example.spaceapp.framework.ui.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.spaceapp.R
import com.example.spaceapp.databinding.NotesFragmentBinding
import com.example.spaceapp.framework.ui.adapters.NotesRecyclerAdapter
import com.example.spaceapp.framework.util.OnListItemClickListener
import com.example.spaceapp.model.AppState
import com.example.spaceapp.model.entities.Note
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotesFragment : Fragment() {
    private val viewModel: NotesViewModel by viewModel()

    private var _binding: NotesFragmentBinding? = null
    private val binding get() = _binding!!

    private var adapter: NotesRecyclerAdapter? = null
    lateinit var itemTouchHelper: ItemTouchHelper


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = NotesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.adapter = adapter
        viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
        viewModel.getNotesData()


    }

    private fun renderData(appState: AppState) {
        with(binding) {
            when (appState) {
                is AppState.Loading -> {
                    Toast.makeText(context, getString(R.string.loading), Toast.LENGTH_SHORT).show()
                }
                is AppState.SuccessNotes -> {
                    var adapter = NotesRecyclerAdapter(
                        object : OnListItemClickListener {
                            override fun onItemClick(note: Note) {
                            }
                        }, object : NotesRecyclerAdapter.OnStartDragListener {
                            override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
                                itemTouchHelper.startDrag(viewHolder)
                            }
                        },
                        appState.notes
                    )
                    recyclerView.adapter = adapter
                    itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
                    itemTouchHelper.attachToRecyclerView(recyclerView)
                    notesFragmentFAB.setOnClickListener { adapter.appendItem() }

                }
                is AppState.Error -> {
                    Toast.makeText(context, getString(R.string.error), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = NotesFragment()
    }
}
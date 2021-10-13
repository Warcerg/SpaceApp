package com.example.spaceapp.framework.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.spaceapp.databinding.NoteRecyclerItemBinding
import com.example.spaceapp.framework.util.OnListItemClickListener
import com.example.spaceapp.model.entities.Note

class NotesRecyclerAdapter(
    private var onListItemClickListener: OnListItemClickListener,
    private var notes: MutableList<Pair<Note, Boolean>>
): RecyclerView.Adapter<NotesRecyclerAdapter.NoteViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding: NoteRecyclerItemBinding =
            NoteRecyclerItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false)
       return NoteViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        (holder).bind(notes[position])
    }

    override fun getItemCount(): Int {
       return notes.size
    }

    inner class NoteViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind(pair: Pair<Note, Boolean>) {
            NoteRecyclerItemBinding.bind(itemView).apply {
                root.setOnClickListener {
                    onListItemClickListener.onItemClick(pair.first)
                }
                noteHeading.text = pair.first.Heading
                noteBodyTextView.text = pair.first.Text
                removeNoteImageView.setOnClickListener {removeItem() }
                moveItemUp.setOnClickListener { moveUp() }
                moveItemDown.setOnClickListener { moveDown() }
                noteHeading.setOnClickListener { toggleText() }
                noteBodyTextView.visibility = if (pair.second) View.VISIBLE else View.GONE
            }
        }

        private fun toggleText() {
            notes[layoutPosition] = notes[layoutPosition].let {
                it.first to !it.second
            }
            notifyItemChanged(layoutPosition)
        }

        private fun moveUp() {
            layoutPosition.takeIf { it > 1 }?.also {
                notes.removeAt(it).apply {
                    notes.add(it - 1, this)
                }
                notifyItemMoved(it, it - 1)
            }
        }

        private fun moveDown() {
            layoutPosition.takeIf { it < itemCount - 1 }?.also {
                notes.removeAt(it).apply {
                    notes.add(it + 1, this)
                }
                notifyItemMoved(it, it + 1)
            }
        }

        private fun removeItem() {
            notes.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
        }



    }
}
package com.example.spaceapp.framework.ui.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MotionEventCompat.getActionMasked
import androidx.recyclerview.widget.RecyclerView
import com.example.spaceapp.R
import com.example.spaceapp.databinding.NoteRecyclerItemBinding
import com.example.spaceapp.framework.ui.notes.ItemTouchHelperViewHolder
import com.example.spaceapp.framework.util.OnListItemClickListener
import com.example.spaceapp.model.entities.Note

class NotesRecyclerAdapter(
    private var onListItemClickListener: OnListItemClickListener,
    private var dragListener: OnStartDragListener,
    private var notes: MutableList<Pair<Note, Boolean>>
) : RecyclerView.Adapter<NotesRecyclerAdapter.NoteViewHolder>(), ItemTouchHelperAdapter {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding: NoteRecyclerItemBinding =
            NoteRecyclerItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return NoteViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        (holder).bind(notes[position])
    }

    override fun getItemCount(): Int {
        return notes.size
    }


    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        notes.removeAt(fromPosition).apply {
            notes.add(if (toPosition > fromPosition) toPosition - 1 else toPosition, this)
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        notes.removeAt(position)
        notifyItemRemoved(position)
    }

    fun appendItem() {
        notes.add(Pair(generateItem(), false))
        notifyItemInserted(itemCount - 1)
    }

    private fun generateItem() = Note("Note Title", "Note text")

    interface OnStartDragListener {
        fun onStartDrag(viewHolder: RecyclerView.ViewHolder)
    }

    inner class NoteViewHolder(view: View) :
        RecyclerView.ViewHolder(view),
        ItemTouchHelperViewHolder {

        private var isEditable = false

        @SuppressLint("ClickableViewAccessibility")
        fun bind(pair: Pair<Note, Boolean>) {
            NoteRecyclerItemBinding.bind(itemView).apply {
                root.setOnClickListener {
                    onListItemClickListener.onItemClick(pair.first)
                }
                noteHeading.text = pair.first.Heading
                noteHeadingEditText.setText(pair.first.Heading)
                noteBodyTextView.setText(pair.first.Text)
                removeNoteImageView.setOnClickListener { removeItem() }
                moveItemUp.setOnClickListener { moveUp() }
                moveItemDown.setOnClickListener { moveDown() }
                noteHeading.setOnClickListener { toggleText() }
                editItem.setOnClickListener { editNote(pair) }
                noteBodyTextView.visibility = if (pair.second) View.VISIBLE else View.GONE
                noteHeadingEditText.visibility = if (pair.second) View.VISIBLE else View.GONE
                editItem.visibility = if (pair.second) View.VISIBLE else View.GONE
                dragHandler.setOnTouchListener { v, event ->
                    if (event.actionMasked == MotionEvent.ACTION_DOWN) {
                        dragListener.onStartDrag(this@NoteViewHolder)
                    }
                    false
                }
            }
        }

        private fun NoteRecyclerItemBinding.editNote(
            pair: Pair<Note, Boolean>
        ) {
            isEditable = !isEditable
            if (isEditable) {
                noteBodyTextView.isFocusable = true
                noteBodyTextView.isFocusableInTouchMode = true
                noteHeadingEditText.isFocusable = true
                noteHeadingEditText.isFocusableInTouchMode = true
                editItem.setImageResource(R.drawable.ic_baseline_save_24)
            } else {
                noteBodyTextView.isFocusable = false
                noteBodyTextView.isFocusableInTouchMode = false
                noteHeadingEditText.isFocusable = false
                noteHeadingEditText.isFocusableInTouchMode = false
                pair.first.Heading = noteHeadingEditText.text.toString()
                noteHeading.text = pair.first.Heading
                pair.first.Text = noteBodyTextView.text.toString()
                editItem.setImageResource(R.drawable.ic_baseline_edit_24)
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

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(Color.WHITE)
        }

    }
}
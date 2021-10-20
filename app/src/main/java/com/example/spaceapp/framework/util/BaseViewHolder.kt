package com.example.spaceapp.framework.util

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.spaceapp.model.entities.Note

abstract class BaseViewHolder(view: View): RecyclerView.ViewHolder(view) {
    abstract fun bind(pair: Pair<Note,Boolean>)
}
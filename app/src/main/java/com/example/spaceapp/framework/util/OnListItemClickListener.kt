package com.example.spaceapp.framework.util

import com.example.spaceapp.model.entities.Note

interface OnListItemClickListener {
    fun onItemClick(note: Note)
}
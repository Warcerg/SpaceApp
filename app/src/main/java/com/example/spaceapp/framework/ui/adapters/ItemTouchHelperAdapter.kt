package com.example.spaceapp.framework.ui.adapters

interface ItemTouchHelperAdapter {
    fun onItemMove(fromPosition:Int,toPosition:Int)
    fun onItemDismiss(position:Int)
}
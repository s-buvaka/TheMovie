package com.wispapp.themovie.ui.moviedetails.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import com.wispapp.themovie.core.model.database.models.TrailerModel
import com.wispapp.themovie.ui.moviedetails.TrailerFragment
import com.wispapp.themovie.ui.moviedetails.interfaces.PlaybackYouTubeListener

class TrailersPagerAdapter(private val listener: PlaybackYouTubeListener, fragment: Fragment) :
    FragmentStateAdapter(fragment) {

    private val itemList = mutableListOf<TrailerModel>()

    var state = STATE.SINGLE_PAGE
        private set

    override fun getItemCount(): Int = itemList.size

    override fun createFragment(position: Int): Fragment =
        TrailerFragment.newInstance(listener, itemList[position].id)

    override fun onBindViewHolder(
        holder: FragmentViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
        state = getState(position)
    }

    fun update(trailers: List<TrailerModel>) {
        itemList.clear()
        itemList.addAll(trailers)
        notifyDataSetChanged()
    }

    private fun getState(position: Int): STATE {
        return when {
            itemList.size == 1 -> STATE.SINGLE_PAGE
            position == 0 -> STATE.START_PAGE
            position == itemList.size - 1 -> STATE.END_PAGE
            else -> STATE.MIDDLE_PAGE
        }
    }

    enum class STATE { SINGLE_PAGE, START_PAGE, END_PAGE, MIDDLE_PAGE }
}
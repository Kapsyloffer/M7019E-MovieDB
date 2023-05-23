
package com.ltu.m7019e.v23.themoviedb.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ltu.m7019e.v23.themoviedb.model.Review
import com.ltu.m7019e.v23.themoviedb.databinding.ReviewListItemBinding

class ReviewAdapter() :  ListAdapter<Review, ReviewAdapter.ViewHolder>(MovieReviewDiffCallback()){
    class ViewHolder(private var binding: ReviewListItemBinding) : RecyclerView.ViewHolder(binding.root) { //wtf is a reviewbinding

        fun bind(_review: Review) {
            binding.review = _review
        }

        companion object {
            fun from(parent: ViewGroup) : ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ReviewListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class MovieReviewDiffCallback : DiffUtil.ItemCallback<Review>() {
    override fun areItemsTheSame(old: Review, new: Review): Boolean {
        return old.id == new.id
    }

    override fun areContentsTheSame(old: Review, new: Review): Boolean {
        return old.content == new.content
    }
}
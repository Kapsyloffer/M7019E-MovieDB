package com.ltu.m7019e.v23.themoviedb.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ltu.m7019e.v23.themoviedb.databinding.MovieListItemBinding
import com.ltu.m7019e.v23.themoviedb.model.Movie

class MovieListAdapter(private val movieClickListener: MovieListClickListener) :  ListAdapter<Movie, MovieListAdapter.ViewHolder>(MovieListDiffCallback()){
    class ViewHolder(private var binding: MovieListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie, movieClickListener: MovieListClickListener) {
            binding.movie = movie
            binding.clickListener = movieClickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup) : ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MovieListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), movieClickListener)
    }
}

class MovieListDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }

}

class MovieListClickListener(val clickListener: (movie: Movie) -> Unit) {
    fun onClick(movie: Movie) = clickListener(movie)
}
package com.ltu.m7019e.v23.themoviedb

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.ltu.m7019e.v23.themoviedb.databinding.FragmentMovieDetailBinding
import com.ltu.m7019e.v23.themoviedb.model.Movie

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class MovieDetailFragment : Fragment() {

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var movie: Movie
    private lateinit var reviewBtn : Button
    private lateinit var imdbBtn : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMovieDetailBinding.inflate(inflater)
        movie = MovieDetailFragmentArgs.fromBundle(requireArguments()).movie
        binding.movie = movie

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        reviewBtn = binding.reviewBtn
        imdbBtn = binding.imdbBtn

        binding.backToMovieList.setOnClickListener {
            findNavController().navigate(MovieDetailFragmentDirections.actionMovieDetailFragmentToMovieListFragment())
        }

        binding.reviewBtn.setOnClickListener{
            findNavController().navigate(MovieDetailFragmentDirections.actionMovieDetailFragmentToReviews(movie))
        }

        binding.imdbBtn.setOnClickListener{
            val imdbUrl = "https://www.imdb.com/title/tt" + movie.id
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(imdbUrl))
            startActivity(intent)
        }
    }
}

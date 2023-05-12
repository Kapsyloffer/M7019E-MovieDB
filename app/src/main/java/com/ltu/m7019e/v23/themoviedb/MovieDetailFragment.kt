package com.ltu.m7019e.v23.themoviedb

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ltu.m7019e.v23.themoviedb.databinding.FragmentMovieDetailBinding
import com.ltu.m7019e.v23.themoviedb.model.Movie
import com.ltu.m7019e.v23.themoviedb.network.TMDBApi
import com.ltu.m7019e.v23.themoviedb.viewmodel.MovieDetailsViewModel
import com.ltu.m7019e.v23.themoviedb.viewmodel.MovieDetailsViewModelFactory
import com.ltu.m7019e.v23.themoviedb.viewmodel.ReviewViewModel
import com.ltu.m7019e.v23.themoviedb.viewmodel.ReviewViewModelFactory

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class MovieDetailFragment : Fragment() {

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var movie: Movie
    private lateinit var viewModelFactory : MovieDetailsViewModelFactory
    private lateinit var viewModel : MovieDetailsViewModel

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

        var application = requireNotNull(this.activity).application
        viewModelFactory = MovieDetailsViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MovieDetailsViewModel::class.java)


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
            val imdbUrl = "https://imdb.com/title/" + viewModel.fetchIMDB(movie)// makeIMDBLink(movie)
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(imdbUrl))
            startActivity(intent)
        }
    }
}

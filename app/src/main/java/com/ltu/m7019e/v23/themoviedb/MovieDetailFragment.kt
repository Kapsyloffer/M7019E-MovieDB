package com.ltu.m7019e.v23.themoviedb

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ltu.m7019e.v23.themoviedb.database.Movies
import com.ltu.m7019e.v23.themoviedb.databinding.FragmentMovieDetailBinding
import com.ltu.m7019e.v23.themoviedb.model.Movie
import com.ltu.m7019e.v23.themoviedb.network.TMDBApi
import com.ltu.m7019e.v23.themoviedb.viewmodel.MovieDetailsViewModel
import com.ltu.m7019e.v23.themoviedb.viewmodel.MovieDetailsViewModelFactory
import com.ltu.m7019e.v23.themoviedb.viewmodel.ReviewViewModel
import com.ltu.m7019e.v23.themoviedb.viewmodel.ReviewViewModelFactory
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class MovieDetailFragment : Fragment() {

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var movie: Movie
    private lateinit var viewModelFactory : MovieDetailsViewModelFactory
    private lateinit var viewModel : MovieDetailsViewModel

    private lateinit var database : Movies

    private lateinit var reviewBtn : Button
    private lateinit var imdbBtn : Button
    private lateinit var saveBtn : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMovieDetailBinding.inflate(inflater)
        movie = MovieDetailFragmentArgs.fromBundle(requireArguments()).movie
        binding.movie = movie

        var application = requireNotNull(this.activity).application

        database = Movies.getInstance(requireNotNull(this.activity).application)

        viewModelFactory = MovieDetailsViewModelFactory(application, database.moviesDao)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MovieDetailsViewModel::class.java)


        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        reviewBtn = binding.reviewBtn
        imdbBtn = binding.imdbBtn
        saveBtn = binding.saveBtn
        lifecycleScope.launch {
            ToggleButton(viewModel.isSaved(movie))
        }

        binding.backToMovieList.setOnClickListener {
            findNavController().navigate(MovieDetailFragmentDirections.actionMovieDetailFragmentToMovieListFragment())
        }

        binding.reviewBtn.setOnClickListener{
            findNavController().navigate(MovieDetailFragmentDirections.actionMovieDetailFragmentToReviews(movie))
        }

        binding.imdbBtn.setOnClickListener{
            lifecycleScope.launch {
                val imdbId = async { viewModel.fetchIMDB(movie) }.await()
                val imdbUrl = "https://imdb.com/title/$imdbId"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(imdbUrl))
                startActivity(intent)
            }
        }

        binding.saveBtn.setOnClickListener{
            lifecycleScope.launch {
                Log.i("a", "CLICKED" + viewModel.isSaved(movie).toString())
                ToggleButton(!viewModel.isSaved(movie))
                if(viewModel.isSaved(movie))
                {
                    viewModel.unsaveMovie(movie.id)
                }
                else
                {
                    viewModel.saveMovie(movie)
                }
            }
        }
    }

    fun ToggleButton(b: Boolean)
    {
        if(b)
        {
            saveBtn.text= "unsave"
        }
        else
        {
            saveBtn.text= "save"
        }
    }
}

package com.ltu.m7019e.v23.themoviedb

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ltu.m7019e.v23.themoviedb.adapter.ReviewAdapter
import com.ltu.m7019e.v23.themoviedb.databinding.FragmentMovieReviewBinding
import com.ltu.m7019e.v23.themoviedb.model.Movie
import com.ltu.m7019e.v23.themoviedb.viewmodel.ReviewViewModel
import com.ltu.m7019e.v23.themoviedb.viewmodel.ReviewViewModelFactory
class MovieReviewFragment : Fragment() {
    private var _binding: FragmentMovieReviewBinding? = null
    private val binding get() = _binding!!

    private lateinit var movie: Movie
    private lateinit var viewModelFactory : ReviewViewModelFactory
    private lateinit var viewModel : ReviewViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMovieReviewBinding.inflate(inflater)
        movie = MovieDetailFragmentArgs.fromBundle(requireArguments()).movie

        binding.movie = movie

        // Set up RecyclerView and adapter
        val reviewsRecyclerView = binding.reviewTxts
        val reviewsAdapter = ReviewAdapter()
        reviewsRecyclerView.adapter = reviewsAdapter


        var application = requireNotNull(this.activity).application
        // Pass the data to the adapter
        viewModelFactory = ReviewViewModelFactory(application, movie)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ReviewViewModel::class.java)

        viewModel.addReviews() //TODO: Remove

        viewModel.movieReviewList.observe(viewLifecycleOwner){ reviewList ->
            reviewList?.let{
                reviewsAdapter.submitList(reviewList)
            }
        }

        binding.viewmodel = viewModel
        return binding.root

    }

    fun getReviews(movie_id: Int)
    {
        //TODO
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        binding.backToMovieDetail.setOnClickListener {
            findNavController().navigate(MovieReviewFragmentDirections.actionReviewsToMovieDetailFragment(movie))
        }
    }
}
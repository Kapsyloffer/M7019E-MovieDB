package com.ltu.m7019e.v23.themoviedb

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ltu.m7019e.v23.themoviedb.databinding.FragmentMovieReviewBinding
import com.ltu.m7019e.v23.themoviedb.model.Movie

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MovieReviewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MovieReviewFragment : Fragment() {
    private var _binding: FragmentMovieReviewBinding? = null
    private val binding get() = _binding!!

    private lateinit var movie: Movie

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

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        binding.backToMovieDetail.setOnClickListener {
            findNavController().navigate(MovieReviewFragmentDirections.actionReviewsToMovieDetailFragment(movie))
        }
    }
}
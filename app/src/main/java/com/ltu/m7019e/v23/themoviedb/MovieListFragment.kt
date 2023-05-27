package com.ltu.m7019e.v23.themoviedb

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.work.impl.constraints.trackers.NetworkStateTracker
import com.ltu.m7019e.v23.themoviedb.adapter.MovieListAdapter
import com.ltu.m7019e.v23.themoviedb.adapter.MovieListClickListener
import com.ltu.m7019e.v23.themoviedb.database.Movies
import com.ltu.m7019e.v23.themoviedb.databinding.FragmentMovieListBinding
import com.ltu.m7019e.v23.themoviedb.network.NetworkStatus
import com.ltu.m7019e.v23.themoviedb.repository.MovieRepo
import com.ltu.m7019e.v23.themoviedb.viewmodel.MovieListViewModel
import com.ltu.m7019e.v23.themoviedb.viewmodel.MovieListViewModelFactory

class MovieListFragment : Fragment() {

    private lateinit var viewModel: MovieListViewModel
    private lateinit var viewModelFactory: MovieListViewModelFactory

    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!

    private var latest : Int = 0

    private var lastSelectedMenuOption = R.id.action_load_popular_movies;

    private lateinit var database : Movies

    private lateinit var repo : MovieRepo

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMovieListBinding.inflate(inflater)

        val application = requireNotNull(this.activity).application
        database = Movies.getInstance(requireNotNull(this.activity).application)
        repo = MovieRepo(database)


        viewModelFactory = MovieListViewModelFactory(application, database)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MovieListViewModel::class.java)

        val movieListAdapter = MovieListAdapter(
            MovieListClickListener { movie ->
                viewModel.onMovieListItemClicked(movie)
            }
        )

        binding.movieListRv.adapter = movieListAdapter
        binding.movieListRv.layoutManager = GridLayoutManager(context, 3);

        repo.movieList.observe(
            viewLifecycleOwner
        ) { movieList ->
            movieList?.let {
                movieListAdapter.submitList(movieList)
            }
        }

        viewModel.navigateToMovieDetail.observe(viewLifecycleOwner) { movie ->
            movie?.let{
                val action = MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment(movie)
                this.findNavController().navigate(action)
                viewModel.onMovieDetailNavigated()
            }
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // The usage of an interface lets you inject your own implementation
        val menuHost: MenuHost = requireActivity()

        // Add menu items without using the Fragment Menu APIs
        // Note how we can tie the MenuProvider to the viewLifecycleOwner
        // and an optional Lifecycle.State (here, RESUMED) to indicate when
        // the menu should be visible
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.menu_main, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                when (menuItem.itemId) {
                    R.id.action_load_popular_movies -> {
                        lastSelectedMenuOption = R.id.action_load_popular_movies
                        latest = 0
                        viewModel.getMovies(0)
                    }
                    R.id.action_load_top_rated_movies -> {
                        lastSelectedMenuOption = R.id.action_load_top_rated_movies
                        latest = 1
                        viewModel.getMovies(1)
                    }
                    R.id.action_load_saved_movies -> {
                        lastSelectedMenuOption = R.id.action_load_saved_movies
                        latest = 2
                        viewModel.getMovies(2)
                    }
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
    override fun onResume() {
        super.onResume()
        Log.i("Latest: ", latest.toString())
        viewModel.getMovies(latest)
    }

}
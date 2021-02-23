package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.api.AsteroidsApiFilter
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    /*
    * A way to delay viewModel creation until a lifecycle method is to use lazy. This requires
    * ViewModel to not be referenced before onViewCreated(), which we do in this fragment
     */
    private val viewModel: MainViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProvider(this, MainViewModelFactorty(activity.application)).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        //Binds the asteroid RecyclerView adapter with click handler. Tells the MainViewModel when
        // the Asteroid item is clicked.
        binding.asteroidRecycler.adapter = AsteroidAdapter(AsteroidAdapter.AsteroidClickListener {
            viewModel.displayAsteroidDetails(it)
        })

        //Observe the NavigateToAsteroidDetails LiveData and navigate when it isn't null
        viewModel.navigateToAsteroidDetails.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                this.findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
                viewModel.displayAsteroidDetailsComplete()
            }
        })

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        viewModel.updateFilter(
                when (item.itemId) {
                    R.id.show_week_menu -> AsteroidsApiFilter.SHOW_ALL
                    R.id.show_today_menu -> AsteroidsApiFilter.SHOW_TODAY
                    else -> AsteroidsApiFilter.SHOW_SAVED
                }
        )
        return true
    }
}

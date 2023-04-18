package com.image.reachmobitsports.presentation.fragments

import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.image.reachmobitsports.analytics.AnalyticsModelProvider
import com.image.reachmobitsports.data.i.responses.Player
import com.image.reachmobitsports.databinding.FragmentSportsDetailBinding
import com.image.reachmobitsports.presentation.adapter.PlayerAdapter
import com.image.reachmobitsports.presentation.util.ConnectionLiveData
import com.image.reachmobitsports.presentation.viewmodel.AnalyticsEventViewModels
import com.image.reachmobitsports.presentation.viewmodel.SearchQueryViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class PlayerFragment : Fragment() {

    private var _binding: FragmentSportsDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var playerListAdapter: PlayerAdapter
    private lateinit var playerRrecyclerView: RecyclerView
    private var playerDataItemList: MutableList<Player> = arrayListOf()
    private lateinit var connectionLiveData: ConnectionLiveData
    private val viewModel: SearchQueryViewModel by viewModels()

    @Inject
    internal lateinit var analyticsModelProvider: AnalyticsModelProvider
    private var analyticsModel: AnalyticsEventViewModels? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        connectionLiveData = ConnectionLiveData(requireContext())
        analyticsModel = analyticsModelProvider[AnalyticsEventViewModels::class.java]
        analyticsModel?.setSecreenViewModel(viewModel)

        viewModel.getSerachPlayer("")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSportsDetailBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Checking Internet connection
        connectionLiveData.observe(viewLifecycleOwner) { isNetworkAvil ->
            isNetworkAvil?.let {
                updateUI(it)
                searchPlayer()
            }
        }
        initObserver()
    }

    //observing the live data
    private fun initObserver() {
        viewModel.searchPlayersLive.observe(viewLifecycleOwner) {
            progressVisibility(it.isLoading)
            it.player?.let { player ->
                initAdapter(player)
            } ?: handleError(it.error)
        }
        initPlayerRecyclerView()
    }


    /**
     * setting recyclerview adapter data
     */
    private fun initAdapter(list: MutableList<Player>) {
        playerDataItemList.clear()
        playerDataItemList.addAll(list)
        playerListAdapter.setEventsLeagueData(list)
        binding.payerNameList.adapter = playerListAdapter
    }

    /**
     * Showing the progress bar on call of network request
     */
    private fun progressVisibility(isVisible: Boolean) {
        if (isVisible)
            binding.progressCircular.visibility = View.VISIBLE
        else
            binding.progressCircular.visibility = View.GONE
    }

    /**
     * Updating the UI on Internet availability
     */
    private fun updateUI(isVisible: Boolean) {
        if (isVisible) {
            binding.payerNameList.visibility = View.VISIBLE
            binding.progressCircular.visibility = View.VISIBLE
        } else {
            binding.payerNameList.visibility = View.GONE
            binding.progressCircular.visibility = View.GONE
            progressVisibility(false)
        }
    }


    /**
     * searching the players
     */
    fun searchPlayer() {
        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Perform search operation
                return false
            }

            override fun onQueryTextChange(playerName: String?): Boolean {
                if (!playerName.isNullOrEmpty()) {
                    viewModel.getSerachPlayer(playerName)
                }
                return true
            }
        }
        )
    }

    /**
     * initialize the recyclerview here
     */
    private fun initPlayerRecyclerView() {
        playerRrecyclerView = binding.payerNameList
        val linearLayoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
        playerRrecyclerView.layoutManager = linearLayoutManager
        playerListAdapter = PlayerAdapter(playerDataItemList)
    }

    /**
     * showing the error message
     */
    private fun handleError(message: String?) {
        message?.let {
            val snackbar = Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG)
            val snackbarTextView =
                snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
            snackbarTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
            snackbar.show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
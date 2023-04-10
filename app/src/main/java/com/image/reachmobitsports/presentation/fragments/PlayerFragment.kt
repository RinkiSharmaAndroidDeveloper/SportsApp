package com.image.reachmobitsports.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.analytics.FirebaseAnalytics
import com.image.reachmobitsports.data.remote.responses.Player
import com.image.reachmobitsports.databinding.FragmentSportsDetailBinding
import com.image.reachmobitsports.presentation.viewmodel.ListViewModel
import com.image.reachmobitsports.presentation.adapter.PlayerAdapter
import com.image.reachmobitsports.presentation.util.ConnectionLiveData
import com.image.reachmobitsports.presentation.util.Const
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PlayerFragment : Fragment() {

    private var _binding: FragmentSportsDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var playerListAdapter: PlayerAdapter
    private lateinit var playerRrecyclerView: RecyclerView
    private var playerDataItemList: MutableList<Player> = arrayListOf()
    private lateinit var connectionLiveData: ConnectionLiveData
    private lateinit var analytics: FirebaseAnalytics
    private val viewModel: ListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectionLiveData = ConnectionLiveData(requireContext())
        analytics = FirebaseAnalytics.getInstance(requireContext())
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
                searchPlayer()
                viewModel.getSerachPlayer("")
                initFireBaseAnalytics()
            }
        }
        initPlayerRecyclerView()
        initObserver()
    }
    //observing the live data
    private fun initObserver() {
        viewModel.searchPlayersLive.observe(viewLifecycleOwner) {
            progressVisibility(it.isLoading)
            it.player?.let { it1 -> initAdapter(it1) }
            handleError(it.error)
            it.player?.let { it1 -> playerListAdapter.setEventsLeagueData(it1) }
        }
    }

    //setting recyclerview adapter data
    private fun initAdapter(list: MutableList<Player>) {
        playerDataItemList.clear()
        playerDataItemList.addAll(list)
        playerListAdapter.setEventsLeagueData(playerDataItemList)
        binding.payerNameList.adapter = playerListAdapter

    }

    //showing the progress bar
    private fun progressVisibility(isVisible: Boolean) {
        if (isVisible)
            binding.progressCircular.visibility = View.VISIBLE
        else
            binding.progressCircular.visibility = View.GONE
    }

    //logging event for firebase analytics
    private fun initFireBaseAnalytics() {
        analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, Bundle().apply {
            putString(FirebaseAnalytics.Param.SCREEN_CLASS, Const.PLAYER_FRAGMENT)
        })
    }

    //searching the players
    fun searchPlayer() {
        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Perform search operation
                return false
            }

            override fun onQueryTextChange(playerName: String?): Boolean {
                if (!playerName.isNullOrEmpty()) {
                    viewModel.getSerachPlayer(playerName)
                    val bundle = Bundle()
                    bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, playerName)
                    analytics.logEvent(Const.SHOW_ALL_CLICKED, bundle)
                }
                return true
            }
        }
        )
    }

    //initialize the recyclerview here
    private fun initPlayerRecyclerView() {
        playerRrecyclerView = binding.payerNameList
        val linearLayoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
        playerRrecyclerView.layoutManager = linearLayoutManager
        playerListAdapter = PlayerAdapter(playerDataItemList)
    }

    //showing the error message
    private fun handleError(message: String?) {
        message?.let {
            Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
package com.image.reachmobitsports.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.analytics.FirebaseAnalytics
import com.image.reachmobitsports.data.remote.response.Teams
import com.image.reachmobitsports.data.remote.responses.Player
import com.image.reachmobitsports.databinding.FragmentSportsListBinding
import com.image.reachmobitsports.presentation.viewmodel.ListViewModel
import com.image.reachmobitsports.presentation.adapter.PlayerAdapter
import com.image.reachmobitsports.presentation.adapter.SportsListAdapter
import com.image.reachmobitsports.presentation.util.ConnectionLiveData
import com.image.reachmobitsports.presentation.util.Const
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SportsListFragment : Fragment() {

    private var _binding: FragmentSportsListBinding? = null
    private val binding get() = _binding!!
    private val sportsDataItemList: MutableList<Teams> = arrayListOf()
    private val playerDataItemList: MutableList<Player> = arrayListOf()
    private lateinit var connectionLiveData: ConnectionLiveData
    private lateinit var sportsListAdapter: SportsListAdapter
    private lateinit var playerListAdapter: PlayerAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var playerRrecyclerView: RecyclerView
    private val viewModel: ListViewModel by viewModels()
    private lateinit var analytics: FirebaseAnalytics
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        connectionLiveData = ConnectionLiveData(requireContext())
        analytics = FirebaseAnalytics.getInstance(requireContext())

        viewModel.getSportsList()
        viewModel.getPlayersList()
        initFireBaseAnalytics()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSportsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        connectionLiveData.observe(viewLifecycleOwner) { isNetworkAviliable ->
            isNetworkAviliable?.let { updateUI(it) }
        }
        binding.tvShowAll.setOnClickListener {
            // creating data for firebase analytics
            val bundle = Bundle()
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, playerDataItemList.get(0).strTeam)
            bundle.putString(
                FirebaseAnalytics.Param.ITEM_NAME,
                playerDataItemList.get(0).playerName
            )
            bundle.putString(
                FirebaseAnalytics.Param.CONTENT_TYPE,
                playerDataItemList.get(0).playerImage
            )
            analytics.logEvent(Const.SHOW_ALL_CLICKED, bundle)
            // navigating to player list fragment
            findNavController().navigate(SportsListFragmentDirections.actionSportsFragmentToPlayerFragment())

        }
        initObserver()
        initRecyclerView()
        initPlayerRecyclerView()
    }
    //initialize the recyclerview for the team list
    private fun initRecyclerView() {
        recyclerView = binding.recyclerview
        val linearLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = linearLayoutManager
        sportsListAdapter = SportsListAdapter(sportsDataItemList)
    }
    //initialize the recyclerview for the player list
    private fun initPlayerRecyclerView() {
        playerRrecyclerView = binding.recyclerviewPlayer
        val linearLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        playerRrecyclerView.layoutManager = linearLayoutManager
        playerListAdapter = PlayerAdapter(playerDataItemList)
    }

    private fun initFireBaseAnalytics() {
        analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, Bundle().apply {
                putString(FirebaseAnalytics.Param.SCREEN_NAME, Const.SPORTS_LIST_FRAGMENT)
            })
    }
//observing the live data
    private fun initObserver() {
        viewModel.liveData.observe(viewLifecycleOwner) {
            progressVisibility(it.isLoading)
            handleError(it.error)
            initAdapter(it.leagues)
        }

        viewModel.playersStateLive.observe(viewLifecycleOwner) {
            progressVisibility(it.isLoading)
            handleError(it.error)
            initPlayerAdapter(it.player)
        }

    }

    private fun initAdapter(list: List<Teams>?) {
        sportsDataItemList.clear()
        list?.let { sportsDataItemList.addAll(it) }
        binding.recyclerview.adapter = sportsListAdapter
    }

    private fun initPlayerAdapter(list: MutableList<Player>?) {
        playerDataItemList.clear()
        list?.let { playerDataItemList.addAll(it) }
        list?.let { playerListAdapter.setEventsLeagueData(it) }

        binding.recyclerviewPlayer.adapter = playerListAdapter
    }

    private fun progressVisibility(isVisible: Boolean) {
        if (isVisible)
            binding.progressCircular.visibility = View.VISIBLE
        else
            binding.progressCircular.visibility = View.GONE
    }

    private fun updateUI(isOnline: Boolean) {
        if (isOnline) {
            binding.recyclerview.visibility = View.VISIBLE
            binding.recyclerviewPlayer.visibility = View.VISIBLE

        } else {
            binding.recyclerview.visibility = View.GONE
            binding.recyclerviewPlayer.visibility = View.GONE
            progressVisibility(false)
        }
    }

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
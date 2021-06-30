package com.example.githubuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.adapter.FollowingAdapter

import com.example.githubuser.databinding.FragmentFollowingBinding
import com.example.githubuser.model.MainViewModel


class FollowingFragment : Fragment() {

    private lateinit var adapter: FollowingAdapter
    private lateinit var binding: FragmentFollowingBinding
    private lateinit var mainViewModel: MainViewModel


    companion object{
        private val ARG_USERNAME ="username"

        fun newInstance(username: String): Fragment{
            val fragment= FollowingFragment()
            val bundle = Bundle()

            bundle.putString(ARG_USERNAME,username)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFollowingBinding.bind(view)
        adapter = FollowingAdapter()
        adapter.notifyDataSetChanged()

        binding.recycleViewFollowing.layoutManager =LinearLayoutManager(activity)
        binding.recycleViewFollowing.adapter=adapter
        val username =arguments?.getString(FollowingFragment.ARG_USERNAME)

        mainViewModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        username?.let { mainViewModel.setListFollowing(it) }
        mainViewModel.getListFollowing().observe(viewLifecycleOwner,{FollowersFollowingItem->
            if (FollowersFollowingItem!=null){
                adapter.setData(FollowersFollowingItem)
            }
        })
       // username?.let { listFollowing(it) }
    }


}
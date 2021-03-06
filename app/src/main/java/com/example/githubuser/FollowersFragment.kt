package com.example.githubuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.adapter.FollowersAdapter
import com.example.githubuser.databinding.FragmentFollowersBinding
import com.example.githubuser.model.MainViewModel


class FollowersFragment : Fragment() {

    private lateinit var adapter: FollowersAdapter
    private lateinit var binding: FragmentFollowersBinding

    private lateinit var mainViewModel: MainViewModel
    companion object{
        private val ARG_USERNAME ="username"

        fun newInstance(username: String): Fragment{
            val fragment= FollowersFragment()
            val bundle = Bundle()

            bundle.putString(ARG_USERNAME,username)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_followers, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFollowersBinding.bind(view)
        adapter = FollowersAdapter()
        adapter.notifyDataSetChanged()

        binding.recycleViewFollowers.layoutManager = LinearLayoutManager(activity)
        binding.recycleViewFollowers.adapter =adapter

        val username =arguments?.getString(ARG_USERNAME)

        mainViewModel =ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        username?.let { mainViewModel.setListFollowers(it) }

       // username?.let { listFollowers(it) }
        mainViewModel.getListFollowers().observe(viewLifecycleOwner,{UserItem->
            if (UserItem!=null){
                adapter.setData(UserItem)
            }
        })
    }



}
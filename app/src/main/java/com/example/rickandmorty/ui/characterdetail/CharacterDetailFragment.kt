package com.example.rickandmorty.ui.characterdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.rickandmorty.R
import com.example.rickandmorty.data.entities.Character
import com.example.rickandmorty.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.character_detail_fragment.*
import kotlinx.android.synthetic.main.character_detail_fragment.view.*

@AndroidEntryPoint
class CharacterDetailFragment : Fragment() {

    private val viewModel: CharacterDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.character_detail_fragment, container, false)
        arguments?.getInt("id")?.let { viewModel.start(it) }
        setupObservers(view)
        return view
    }

    private fun setupObservers(view: View) {
        viewModel.character.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    bindCharacter(view, it.data!!)
                    progress_bar.visibility = View.GONE
                    character_cl.visibility = View.VISIBLE
                }

                Resource.Status.ERROR ->
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING -> {
                    progress_bar.visibility = View.VISIBLE
                    character_cl.visibility = View.GONE
                }
            }
        })
    }

    private fun bindCharacter(view: View, character: Character) {
        view.name.text = character.name
        view.species.text = character.species
        view.status.text = character.status
        view.gender.text = character.gender
        Glide.with(view)
            .load(character.image)
            .transform(CircleCrop())
            .into(view.image)
    }
}

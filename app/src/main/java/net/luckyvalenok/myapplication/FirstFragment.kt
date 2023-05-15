package net.luckyvalenok.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import net.luckyvalenok.myapplication.databinding.FirstFragmentBinding

class FirstFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    private val cardAdapter = CardAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FirstFragmentBinding = FirstFragmentBinding.inflate(inflater, container, false)

        binding.items.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                CardAdapter.SpacesItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL,
                    resources.getDimensionPixelSize(R.dimen.default_padding)
                )
            )
            adapter = cardAdapter
        }

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.cards.observe(viewLifecycleOwner) {
            cardAdapter.submitList(CardType.getFromListRequest(it))
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }
}
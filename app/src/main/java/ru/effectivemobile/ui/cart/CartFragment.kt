package ru.effectivemobile.ui.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import dagger.android.support.AndroidSupportInjection
import ru.effectivemobile.R
import ru.effectivemobile.databinding.CartViewholderBinding
import ru.effectivemobile.databinding.FragmentCartBinding
import ru.effectivemobile.di.viewModelInjector
import ru.effectivemobile.ui.utils.decoratePrice
import javax.inject.Inject
import javax.inject.Provider


class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private lateinit var viewModel: CartViewModel

    @Inject
    fun injectViewModel(provider: Provider<CartViewModel>) {
        viewModel = viewModelInjector(provider, requireActivity())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavigation()
        setupRecycler()

    }

    private fun setupNavigation() {
        binding.cartBackButton.setOnClickListener {  findNavController().popBackStack() }
    }

    private fun setupRecycler() {
        attachCartItems(
            binding.cartRecycler,
            notifyAmountDecreased = viewModel.notifyItemDecreased,
            notifyAmountIncreased = viewModel.notifyItemIncreased,
            notifyItemDeleted = viewModel.notifyItemDeleted,
            registerEventHandler = { recyclerHandler->
                viewModel.subscriberToCartEvents(viewLifecycleOwner) { event->
                    recyclerHandler(event)
                    binding.price.text = "${decoratePrice(event.data.sumOf { it.cost })} us"
                }
            }
        )
    }

    companion object {

    }
}
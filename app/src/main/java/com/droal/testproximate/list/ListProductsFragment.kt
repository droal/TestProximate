package com.droal.testproximate.list

import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.droal.testproximate.R
import com.droal.testproximate.databinding.FragmentListBinding
import com.droal.testproximate.login.LoginViewModel
import com.google.android.material.navigation.NavigationView


/**
 * A simple [Fragment] subclass.
 * Use the [ListProductsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListProductsFragment : Fragment() {

    private val viewModel: LoginViewModel by activityViewModels()

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!


    val menuIcons: Map<String, Int> = mapOf(
        "Clear" to R.drawable.clear,
        "Adb" to R.drawable.adb,
        "AddLocation" to R.drawable.add_location,
        "Airplay" to R.drawable.airplay,
        "Android" to R.drawable.android,
        "Apple" to R.drawable.apple)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root

        requireActivity().findViewById<FrameLayout>(R.id.footer).visibility = View.VISIBLE
        //Configurar menú
        var navView = requireActivity().findViewById<NavigationView>(R.id.nav_view)
        navView.setItemIconTintList(null);
        val navDraweerMenu: Menu = navView.getMenu()


        val adapter = ListProductsAdapter(ListProductsAdapter.OnClickListener{
            viewModel.displayProductDetails(it)
        })

        binding.rvProductsGrid.adapter = adapter

        viewModel.responseData.observe(viewLifecycleOwner, Observer { response ->
            adapter.data = response.products
            navDraweerMenu.clear()
            response.menu.forEach {

                val draw = menuIcons.get(it.icon) ?: R.drawable.err_img

                navDraweerMenu.add(0, it.id, 0, it.redirectTo.replace("/", "")).setIcon(draw)
            }
        })


        viewModel.navigateToSelectedProduct.observe(viewLifecycleOwner, {
            if(it != null){
               view.findNavController().navigate(ListProductsFragmentDirections.actionListFragmentToDetailProductFragment(it))
                viewModel.displayProductDetailsComplete()
            }
        })
        return view
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
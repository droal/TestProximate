package com.droal.testproximate.login

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.droal.testproximate.R
import com.droal.testproximate.databinding.FragmentLoginBinding


/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by activityViewModels()

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return

        val sesion: Boolean = sharedPref.getBoolean("sesion", false)

        if(sesion){
            val user: String? = sharedPref.getString("user", "")
            val password: String? = sharedPref.getString("password", "")

            if (user != null && password != null) {
                viewModel.login(user, password)
            }
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        requireActivity().findViewById<FrameLayout>(R.id.footer).visibility = View.GONE

        var toolbar = requireActivity().findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        toolbar.menu?.clear()
        toolbar.navigationIcon = null

        binding.button.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE

            val user: String = binding.etUser.text.toString()
            val password: String = binding.etPassword.text.toString()
            viewModel.login(user, password)
        }

        viewModel.responseLoginMsg.observe(viewLifecycleOwner, Observer { response ->
            binding.text.text = response
            binding.progressBar.visibility = View.INVISIBLE
        })

        viewModel.responseLoginOk.observe(viewLifecycleOwner, Observer {
            if(it != null){

                binding.progressBar.visibility = View.INVISIBLE

                val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
                if(sharedPref != null){
                    val sesion: Boolean = sharedPref.getBoolean("sesion", false)
                    if(!sesion){
                        with (sharedPref.edit()) {
                            putBoolean("sesion", true)
                            putString("user", binding.etUser.text.toString())
                            putString("password", binding.etPassword.text.toString())
                            apply()
                        }
                    }
                }

                view.findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToListFragment())
                viewModel.displayProductListComplete()
            }
        })

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
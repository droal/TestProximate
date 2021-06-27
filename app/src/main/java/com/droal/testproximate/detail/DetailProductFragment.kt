package com.droal.testproximate.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import coil.ImageLoader
import coil.api.load
import coil.decode.SvgDecoder
import coil.request.LoadRequest
import com.droal.testproximate.R
import com.droal.testproximate.databinding.FragmentDetailProductBinding
import java.util.*

class DetailProductFragment : Fragment() {

    private var _binding: FragmentDetailProductBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailProductBinding.inflate(inflater, container, false)
        val view = binding.root

        requireActivity().findViewById<FrameLayout>(R.id.footer).visibility = View.VISIBLE

        val product = DetailProductFragmentArgs.fromBundle(requireArguments()).productSelected
        binding.tvTitle.text = product.title
        binding.tvDesc.text = product.longDescription
        binding.ivDet.loadImageFromUrl(product.image)

        return view
    }

    fun ImageView.loadImageFromUrl(myUrl: String?) {
        myUrl?.let {
            if (it.toLowerCase(Locale.ENGLISH).endsWith("svg")) {
                val imageLoader = ImageLoader.Builder(this.context)
                    .componentRegistry {
                        add(SvgDecoder(this@loadImageFromUrl.context))
                    }
                    .build()
                val request = LoadRequest.Builder(this.context)
                    .data(it)
                    .target(this)
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.err_img)
                    .build()
                imageLoader.execute(request)
            } else {
                this.load(myUrl){
                    placeholder(R.drawable.loading_animation)
                    error(R.drawable.err_img)
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.smolentsev.pixpicture.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.smolentsev.pixpicture.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class WallpaperFragment : Fragment() {

    private var imageURL: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkImageArguments()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Arguments",imageURL.toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wallpaper, container, false)
    }
    private fun checkImageArguments(){
        imageURL = requireArguments().getString(IMAGE_PUT)
    }

    companion object {
       private const val IMAGE_PUT = "Image"
        @JvmStatic
        fun newInstance(imageURL: String) =
            WallpaperFragment().apply {
                arguments = Bundle().apply {
                    arguments = Bundle().apply {
                        putString(IMAGE_PUT, imageURL)
                    }
                }
            }
    }
}
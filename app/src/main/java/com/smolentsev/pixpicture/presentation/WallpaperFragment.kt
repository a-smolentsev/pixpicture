package com.smolentsev.pixpicture.presentation

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.smolentsev.pixpicture.R
import java.io.IOException


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class WallpaperFragment : Fragment() {

    private lateinit var imageURL: String
    private lateinit var wallpaper: ImageView
    private lateinit var button: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkImageArguments()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        setImage(imageURL,view)
        button.setOnClickListener {
            setAsWallpaper()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wallpaper, container, false)
    }
    private fun checkImageArguments(){
        imageURL = requireArguments().getString(IMAGE_PUT).toString()
        Log.d("Arguments",imageURL)
    }

    fun setAsWallpaper() {
        Glide.with(requireContext())
            .asBitmap()
            .load(imageURL)
            .into(object : CustomTarget<Bitmap?>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap?>?
                ) {
                    try {
                        Toast.makeText(requireContext(),"Изображение установлено",Toast.LENGTH_SHORT).show()
                        WallpaperManager.getInstance(requireContext()).setBitmap(resource)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }
            })
    }

    private fun setImage(imageUrl: String, view: View){
        Glide.with(view)
            .load(imageUrl)
            .placeholder(R.drawable.load_image)
            .centerCrop()
            .into(wallpaper)
    }

    private fun initView(view: View){
        wallpaper = view.findViewById(R.id.imageWallpaper)
        button = view.findViewById(R.id.setWallpaper)
    }

    companion object {
       private const val IMAGE_PUT = "Image"
        @JvmStatic
        fun newInstance(image: String): WallpaperFragment {
            return WallpaperFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(IMAGE_PUT, image)
                }
            }
        }
    }
}
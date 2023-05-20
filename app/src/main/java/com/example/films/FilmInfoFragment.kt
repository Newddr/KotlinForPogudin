package com.example.films

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class FilmInfoFragment: Fragment(R.layout.info_film_fragment) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var text = requireActivity().findViewById<TextView>(R.id.name)
        text.text = putArgs()[0]
        var image = requireActivity().findViewById<ImageView>(R.id.imageView2)
        var bitmap= arguments?.getParcelable<Bitmap>("bitmap")
//        bitmap= bitmap?.let { resizeBitmap(it,1600,1600) }
        image.setImageBitmap(bitmap)
        var descriprion = requireActivity().findViewById<TextView>(R.id.descriprion)
        descriprion.text= arguments?.getString("description")
        var favorite = requireActivity().findViewById<ImageView>(R.id.imageView4)
        if(putArgs()[1]=="1") favorite.setImageResource(R.drawable.star1)
            else favorite.setImageResource(R.drawable.star)

        favorite.setOnClickListener {
            if (putArgs()[1]=="1") {
                favorite.setImageResource(R.drawable.star)

            } else {
                favorite.setImageResource(R.drawable.star1)

            }
        }


    }
    fun resizeBitmap(bitmap: Bitmap, width: Int, height: Int): Bitmap {
        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true)
        bitmap.recycle()
        return resizedBitmap
    }
    fun putArgs():Array<String>   {
        val bundle=arguments
        var args = arrayOf("","","")
        if(bundle!=null) {
            args[0] = bundle?.getString("name").toString()
            args[1]= bundle?.getString("status").toString()
            return args
        }
        return arrayOf("","","")
    }




}
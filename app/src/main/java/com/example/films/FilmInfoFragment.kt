package com.example.films

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class FilmInfoFragment: Fragment(R.layout.info_film_fragment) {
    private lateinit var databaseHelper: DataBaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databaseHelper = DataBaseHelper(requireContext())


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
        if (putArgs()[1] == "1") {
            favorite.setImageResource(R.drawable.star1)
        } else {
            favorite.setImageResource(R.drawable.star)
        }

        favorite.setOnClickListener{
            val currentStatus = putArgs()[1].toInt()
            val newStatus = if (currentStatus == 1) 0 else 1
            val itemId = putArgs()[2].toInt()

            if (newStatus == 1) {
                favorite.setImageResource(R.drawable.star1)
                databaseHelper.updateFilmStatus(itemId, 1)
            } else {
                favorite.setImageResource(R.drawable.star)
                databaseHelper.updateFilmStatus(itemId, 0)
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
            args[2]=bundle?.getInt("id").toString()
            return args
        }
        return arrayOf("","","")
    }




}
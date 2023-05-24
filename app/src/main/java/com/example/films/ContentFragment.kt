package com.example.films


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class ContentFragment : Fragment(R.layout.fragment_content) {

    var listOfFilms = mutableListOf<FilmsInfo>()
    private lateinit var databaseHelper: DataBaseHelper
    private var filter="";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(activity is SecondActivity) filter =" WHERE status = 1"
        else filter=""
        databaseHelper = DataBaseHelper(requireContext())
        FillDatabaseBySeries()
    }




    @SuppressLint("Range")
    private suspend fun fillist(adapter: RecycleAdapter)  {
        listOfFilms.clear()
        val films = databaseHelper.getAllFilms(filter)
        for (film in films) {
            if (!listOfFilms.any { it.id == film.id }) {
                listOfFilms.add(film)
                adapter.notyy()
            }
            delay(1000)
        }
    }
    private fun FillDatabaseBySeries() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val spaceApi = retrofit.create(FinalSpace::class.java)
        val call = spaceApi.episodes()

        call.enqueue(object : Callback<List<FilmsInfo>> {
            override fun onResponse(call: Call<List<FilmsInfo>>, response: Response<List<FilmsInfo>>) {
                if (response.isSuccessful) {
                    val series = response.body()
                    if (series != null) {
                        GlobalScope.launch {
                            for (seria in series) {
                                // Проверяем, есть ли фильм уже в базе данных
                                if (!databaseHelper.checkIfSeriaExists(seria.id)) {
                                    databaseHelper.insertSeria(seria)
                                }
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<FilmsInfo>>, t: Throwable) {
                val toast = Toast.makeText(
                    context,
                    "Ошибка api запроса ${t.message}",
                    Toast.LENGTH_LONG
                )
                toast.show()
                Log.d("FFFFF", "" + t.message)
            }
        })

        listOfFilms.clear()
    }


    private val BASE_URL = "https://finalspaceapi.com/"

    interface FinalSpace {
        @GET("api/v0/episode/")
        fun episodes(): Call<List<FilmsInfo>>
    }




    @SuppressLint("SuspiciousIndentation")
    override  fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val recycle = view.findViewById<RecyclerView>(R.id.recycler)
       recycle.layoutManager = LinearLayoutManager(context)

        val container= requireActivity().findViewById<View>(R.id.fragment)
        //val text= requireActivity().findViewById<TextView>(R.id.LogText)
//        text.text=fillist().size.toString()


//        recycle.adapter = activity?.let { RecycleAdapter(fillist() as MutableList<VkInfo>, it,container) }
//        recycle.adapter.addItem(VkInfo("fgg",5,"fddg"," "))
        val adapter = activity?.let {
            RecycleAdapter(listOfFilms ,
                it, container)
        }

        recycle.adapter = adapter

            if (adapter != null){
                GlobalScope.launch(Dispatchers.IO) {
                    if (adapter != null) {
                        fillist(adapter)
                    };
                }
            }

//        text.text=recycle.adapter?.itemCount.toString()


    }


}






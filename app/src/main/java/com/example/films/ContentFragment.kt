package com.example.films


import android.annotation.SuppressLint
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.films.R
import kotlinx.coroutines.*

class ContentFragment : Fragment(R.layout.fragment_content) {

    var listOfFilms = mutableListOf<FilmsInfo>()
    private lateinit var databaseHelper: DataBaseHelper
    private var filter="";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(activity is SecondActivity) filter =" WHERE status = 1"
        else filter=""
        databaseHelper = DataBaseHelper(requireContext())
    }




    @SuppressLint("Range")
    private suspend fun fillist(adapter: RecycleAdapter)  {
        val films = databaseHelper.getAllFilms(filter)
        for (film in films) {
            if (adapter != null) {
                listOfFilms.add(film)
                //adapter.addItem(data)
                adapter.notyy()
            }
            delay(1000);


        }

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






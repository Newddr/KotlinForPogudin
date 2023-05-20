package com.example.films


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.films.R
import kotlinx.coroutines.*


public var isAutorize = false
class ContentFragment : Fragment(R.layout.fragment_content) {

    public var listOfFilms = mutableListOf<FilmsInfo>(FilmsInfo("фильм 1", 2023,"Описание к фильму 1","https://preview.redd.it/4laj8cm34nxa1.jpg?width=640&crop=smart&auto=webp&v=enabled&s=9599f708cd7e7ba2268a99dc0f4668bfe88ebe78",1))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }




    @SuppressLint("Range")
    private  fun fillist(adapter: RecycleAdapter)  {
        var i =0;
        for(i in 0..10)  {
            val data = FilmsInfo("film ${i}", 1999, "description", "https://preview.redd.it/4laj8cm34nxa1.jpg?width=640&crop=smart&auto=webp&v=enabled&s=9599f708cd7e7ba2268a99dc0f4668bfe88ebe78",0)
            if (adapter != null) {

                listOfFilms.add(data)
                //adapter.addItem(data)
                adapter.notyy()
            }
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


            if (adapter != null)  fillist(adapter)

//        text.text=recycle.adapter?.itemCount.toString()


    }


}






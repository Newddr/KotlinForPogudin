package com.example.films
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager



class MainActivity : AppCompatActivity() {
    lateinit var viewPager:ViewPager

    val fragment = ContentFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn = findViewById<Button>(R.id.autorizebtn)
        btn.setOnClickListener {
            val IntentLog = Intent(this, SecondActivity::class.java)
           startActivity(IntentLog)
        }

    }



    override fun onBackPressed() {
        // Получаем текущий фрагмент
        val fragment = supportFragmentManager.findFragmentById(R.id.fragment)

        // Если это фрагмент ChildFragment, переключаемся на ParentFragment
        if (fragment is FilmInfoFragment) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment, ContentFragment())
            transaction.commit()
        } else {
            // Если это Root-фрагмент, просто закрываем активность
            super.onBackPressed()
        }
    }


    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {

        return super.onCreateView(name, context, attrs)

    }




}
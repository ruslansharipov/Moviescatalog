package ru.sharipov.moviescatalog.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.sharipov.moviescatalog.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment())
                .commit()
        }
    }
}

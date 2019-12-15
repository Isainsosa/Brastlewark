package com.isain.brastlewark

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.isain.brastlewark.ui.home.HomeController
import com.isainsosa.arco.extensions.moveToFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window?.statusBarColor = ContextCompat.getColor(this, R.color.backgroundColor)
            window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window?.statusBarColor = ContextCompat.getColor(this, R.color.backgroundColor)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showFragment(HomeController())
    }

    private fun showFragment(fragment: Fragment) {
        container.let { supportFragmentManager.moveToFragment(it.id, fragment, false) }
    }
}

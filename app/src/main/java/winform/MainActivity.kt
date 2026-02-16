package winform

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import winform.fragments.LoginFragment
import com.example.lks_latihan_app.R
import winform.fragments.BaseUrlFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, BaseUrlFragment())
                .commit()
        }
    }

}

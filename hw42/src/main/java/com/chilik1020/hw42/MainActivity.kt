package com.chilik1020.hw42

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.cvColorMixer
import kotlinx.android.synthetic.main.activity_main.rootView
import kotlinx.android.synthetic.main.activity_main.switchToastOrSnackBar

class MainActivity : AppCompatActivity() {

    private var isSwitchOn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        isSwitchOn = switchToastOrSnackBar.isChecked
        switchToastOrSnackBar.setOnCheckedChangeListener { _, isChecked -> isSwitchOn = isChecked }

        cvColorMixer.setListener(object : ColorMixerTouchListener {
            override fun onTouchColorMixer(x: Float, y: Float, color: Int?) {
                if (isSwitchOn) {
                    val sbView =
                        Snackbar.make(rootView, "Нажаты координаты[$x; $y]", Snackbar.LENGTH_SHORT)
                    color?.let { sbView.setBackgroundTint(it) }
                    sbView.show()
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Нажаты координаты[$x; $y]",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }
}

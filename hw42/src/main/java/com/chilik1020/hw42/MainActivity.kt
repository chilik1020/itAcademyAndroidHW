package com.chilik1020.hw42

import android.os.Bundle
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ColorMixerTouchListener,
    CompoundButton.OnCheckedChangeListener {

    private var isSwitchOn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        isSwitchOn = switchToastOrSnackBar.isChecked
        isSwitchOn = true
        switchToastOrSnackBar.setOnCheckedChangeListener(this)
        cvColorMixer.setListener(this)
    }

    override fun onTouchColorMixer(x: Float, y: Float, color: Int?) {
        if (isSwitchOn) {
            val sbView = Snackbar.make(rootView,"Нажаты координаты[$x; $y]", Snackbar.LENGTH_SHORT)
            color?.let { sbView.setBackgroundTint(it) }
            sbView.show()
        } else {
            Toast.makeText(this, "Нажаты координаты[$x; $y]", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        isSwitchOn = isChecked
        Toast.makeText(
            this, "CLICK: " + if (isChecked) "on" else "off",
            Toast.LENGTH_SHORT
        ).show()
    }
}
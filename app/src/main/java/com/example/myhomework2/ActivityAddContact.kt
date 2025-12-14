package com.example.myhomework2

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myhomework2.databinding.ActivityAddContactBinding
import android.widget.ArrayAdapter
import android.content.Intent
import android.widget.RadioButton


class AddContactActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddContactBinding

    private var selectedHabitColor: Int = android.graphics.Color.WHITE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupPrioritySpinner()
        setupColorPicker()
        setupListener()
    }

    private fun setupColorPicker() {
        val colors = IntArray(361) { i -> android.graphics.Color.HSVToColor(floatArrayOf(i.toFloat(), 1f, 1f)) }
        val gradient = android.graphics.drawable.GradientDrawable(
            android.graphics.drawable.GradientDrawable.Orientation.LEFT_RIGHT,
            colors
        )
        binding.squareContainer.background = gradient

        val screenWidth = resources.displayMetrics.widthPixels
        val squareSize = (screenWidth / 5.25).toInt()
        val margin = (squareSize * 0.25).toInt()

        for (i in 0..15) {
            val square = android.view.View(this)
            val hue = (360f / 15f) * i
            val color = android.graphics.Color.HSVToColor(floatArrayOf(hue, 1f, 1f))

            square.setBackgroundColor(color)

            val params = android.widget.LinearLayout.LayoutParams(squareSize, squareSize)
            params.setMargins(margin, margin, margin, margin)
            square.layoutParams = params

            square.setOnClickListener {
                updateColorInfo(color)
            }
            binding.squareContainer.addView(square)
        }
        updateColorInfo(android.graphics.Color.HSVToColor(floatArrayOf(0f, 1f, 1f)))
    }

    private fun updateColorInfo(color:Int) {
        selectedHabitColor = color
        binding.currentColorPreview.setBackgroundColor(color)

        val r = android.graphics.Color.red(color)
        val g = android.graphics.Color.green(color)
        val b = android.graphics.Color.blue(color)

        val hsv = FloatArray(3)
        android.graphics.Color.colorToHSV(color, hsv)

        val h = hsv[0].toInt()
        val s = (hsv[1]*100).toInt()
        val v = (hsv[2]*100).toInt()

        binding.tvRGB.text = getString(R.string.rgb, r, g, b)
        binding.tvHSV.text = getString(R.string.hsv, h, s, v)
    }

    private fun setupPrioritySpinner() {
        val spinnerAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.priority,
            android.R.layout.simple_spinner_item
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spPriority.adapter = spinnerAdapter
    }

    private fun setupListener() {
        binding.btnSave.setOnClickListener {
            saveHabit()
        }
    }

    private fun saveHabit() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)

        with(binding) {
            val habitName = edHabitName.text.toString().trim()
            val description = edDescription.text.toString().trim()
            val priority = spPriority.selectedItem.toString()
            val howMake = edHowMany.text.toString()
            val period = edPeriod.text.toString()

            val checkedId = types.checkedRadioButtonId

            if (checkedId == -1) {
                showToast()
                return
            }

            val type = findViewById<RadioButton>(checkedId).text.toString()
            if (habitName.isEmpty() || description.isEmpty() || howMake.isEmpty() || period.isEmpty()) {
                showToast()
                return
            }

            val habit = Habit(habitName, description, priority, type, howMake, period, selectedHabitColor)
            sendResult(habit)
        }
    }

    private fun showToast() {
        Toast.makeText(this, "Выберите тип", Toast.LENGTH_SHORT).show()
    }

    private fun sendResult(habit: Habit) {
        val data = Intent().apply {
            putExtra(EXTRA_HABIT, habit)
        }
        setResult(RESULT_OK, data)
        finish()
    }

    companion object {
        const val EXTRA_HABIT = "extra_habit"
    }
}


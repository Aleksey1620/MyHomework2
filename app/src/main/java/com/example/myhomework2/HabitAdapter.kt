package com.example.myhomework2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myhomework2.databinding.ItemContactBinding

class HabitAdapter : RecyclerView.Adapter<HabitAdapter.VH>() {

    private val items = mutableListOf<Habit>()

    fun addHabit(habit: Habit) {
        items.add(habit)
        notifyItemInserted(items.lastIndex)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemContactBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VH(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

    class VH(private val binding: ItemContactBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(habit: Habit) {
            with(binding){
                tvHabitName.text = habit.habitName
                tvDescription.text = habit.description
                tvPriority.text = habit.priority
                tvType.text = habit.type
                tvHowMany.text = habit.howMany
                tvPeriod.text = habit.period
                colorIndicator.setBackgroundColor(habit.color)
            }
        }
    }
}


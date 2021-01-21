package com.btuzel.currencyconverter

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.btuzel.currencyconverter.databinding.ActivityMainBinding
import com.btuzel.currencyconverter.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val animation2 = AlphaAnimation(0.01f, 1.0f)
        animation2.duration = 1750
        animation2.startOffset = 500
        animation2.fillAfter = true
        binding.tvTitle.startAnimation(animation2)

        binding.tilFrom.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in_left))
        binding.btnConvert.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in_right))
        binding.spFromCurrency.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in_right))
        binding.spToCurrency.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in_right))

        binding.btnConvert.setOnClickListener {
            viewModel.convert(
                binding.etFrom.text.toString(),
                binding.spFromCurrency.selectedItem.toString(),
                binding.spToCurrency.selectedItem.toString(),
            )
        }

        lifecycleScope.launchWhenStarted {
            viewModel.conversion.collect { event ->
                when(event) {
                    is MainViewModel.CurrencyEvent.Success -> {
                        binding.progressBar.isVisible = false
                        binding.tvResult.setTextColor(Color.BLACK)
                        binding.tvResult.text = event.resultText
                    }
                    is MainViewModel.CurrencyEvent.Failure -> {
                        binding.progressBar.isVisible = false
                        binding.tvResult.setTextColor(Color.RED)
                        binding.tvResult.text = event.errorText
                    }
                    is MainViewModel.CurrencyEvent.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    else -> Unit
                }
            }
        }
    }
}
package com.example.cheapshark

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.cheapshark.ui.GameDealsScreen
import com.example.cheapshark.ui.theme.CheapSharkAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CheapSharkAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    GameDealsScreen()
                }
            }
        }
    }
}

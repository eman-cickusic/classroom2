package com.classroom2.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.classroom2.app.ui.navigation.AppNavGraph
import com.classroom2.app.ui.theme.Classroom2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Classroom2Theme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    // Push every screen below the status bar and above the nav bar so
                    // top-bar back arrows, hero gradients, and bottom CTAs stay clickable.
                    androidx.compose.foundation.layout.Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(WindowInsets.systemBars.asPaddingValues())
                    ) {
                        AppNavGraph()
                    }
                }
            }
        }
    }
}

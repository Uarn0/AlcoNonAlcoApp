package com.example.cocktails

import android.animation.ObjectAnimator.ofFloat
import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.cocktails.ui.navigation.BottomNavigationBar
import com.example.cocktails.ui.theme.CocktailsTheme
import com.example.cocktails.viewmodel.CocktailsViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: CocktailsViewModel by viewModels { CocktailsViewModel.Factory }
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition {
            !viewModel.uiState.value.uiIsReady
        }

        splashScreen.setOnExitAnimationListener { splashScreenView ->
            val slideUp = ofFloat(
                splashScreenView.view,
                View.TRANSLATION_Y,
                0f,
                -splashScreenView.view.height.toFloat()
            )
            slideUp.interpolator = AnticipateInterpolator()
            slideUp.duration = 600L

            slideUp.doOnEnd { splashScreenView.remove() }
            slideUp.start()
        }

        enableEdgeToEdge()
        setContent {
            CocktailsTheme {
                AllUI(
                    viewModel = viewModel
                )
            }
        }
    }
}

@Composable
fun AllUI(modifier: Modifier = Modifier, viewModel: CocktailsViewModel) {
    BottomNavigationBar(viewModel = viewModel)
}
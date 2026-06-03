package com.example.cocktails

import android.animation.ObjectAnimator.ofFloat
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.cocktails.ui.navigation.BottomNavigationBar
import com.example.cocktails.ui.theme.CocktailsTheme
import com.example.cocktails.viewmodel.CocktailsViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: CocktailsViewModel by viewModels { CocktailsViewModel.Factory }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
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
fun AllUI(viewModel: CocktailsViewModel) {
    BottomNavigationBar(viewModel = viewModel)
}

@Composable
fun ExpandableActionMenu(
    onRandomClick: () -> Unit,
    onSearchClick: () -> Unit
) {

    var expanded by remember { mutableStateOf(false) }

    val items = listOf(
        MiniFabItem(
            icon = Icons.Default.AutoAwesome,
            title = "Random cocktail",
            onClick = onRandomClick
        ), MiniFabItem(
            icon = Icons.Default.Search,
            title = "Search cocktail",
            onClick = onSearchClick,
        )
    )
    Column(horizontalAlignment = Alignment.End) {
        AnimatedVisibility(
            visible = expanded,
            enter = fadeIn() + scaleIn(),
            exit = fadeOut() + scaleOut()
        ) {
            LazyColumn(Modifier.padding(bottom = 0.dp)) {
                items(items) { item ->
                    FloatingActionButtonMenuItem(
                        item.icon, item.title,
                        onClick = { item.onClick() }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
        val transition = updateTransition(targetState = expanded, label = "transition")
        val rotation by transition.animateFloat(label = "rotation") {
            if (it) 180f else 0f
        }

        FloatingActionButton(
            onClick = { expanded = !expanded }
        ) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowUp,
                contentDescription = "Expand",
                modifier = Modifier.rotate(rotation)
            )
        }
    }

}

@Composable
fun FloatingActionButtonMenuItem(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surface)
            .clickable(onClick = onClick)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(label, style = MaterialTheme.typography.labelMedium)
        Icon(icon, contentDescription = label, Modifier.size(20.dp))
    }
}

data class MiniFabItem(
    val icon: ImageVector,
    val title: String,
    val onClick: () -> Unit
)
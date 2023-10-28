package com.raktufin.composehomerecipes

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.raktufin.composehomerecipes.domain.models.RecipeDomain
import com.raktufin.composehomerecipes.extensions.observe
import com.raktufin.composehomerecipes.features.MainScreen
import com.raktufin.composehomerecipes.features.viewmodel.MainUiState
import com.raktufin.composehomerecipes.features.viewmodel.MainViewModel
import com.raktufin.composehomerecipes.ui.theme.ComposeHomeRecipesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeHomeRecipesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val state by viewModel.state.collectAsState()
                    MainScreen(state, viewModel::insertRecipe, this::showToast)
                }
            }
        }
    }

    private fun showToast() {
        Toast.makeText(
            applicationContext,
            "Card clicked",
            Toast.LENGTH_SHORT
        ).show()
    }
}
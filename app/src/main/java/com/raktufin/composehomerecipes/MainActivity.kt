package com.raktufin.composehomerecipes

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.raktufin.composehomerecipes.features.fullrecipe.FullRecipeScreen
import com.raktufin.composehomerecipes.features.fullrecipe.viewmodel.FullRecipeState
import com.raktufin.composehomerecipes.features.fullrecipe.viewmodel.FullRecipeViewModel
import com.raktufin.composehomerecipes.features.main.MainScreen
import com.raktufin.composehomerecipes.features.main.viewmodel.MainViewModel
import com.raktufin.composehomerecipes.ui.theme.ComposeHomeRecipesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel by viewModels<MainViewModel>()
//    private val fullRecipeViewModel by viewModels<FullRecipeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeHomeRecipesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "main-screen") {
                        composable("main-screen") {
                            val state by mainViewModel.state.collectAsState()
                            MainScreen(
                                state,
                                mainViewModel::insertRecipe,
                                navController,
                                mainViewModel::deleteRecipe,
                                this@MainActivity::alertToast
                            )
                        }
                        composable(
                            "full-recipe-screen/{recipeId}",
                            arguments = listOf(navArgument("recipeId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val recipeId = backStackEntry.arguments?.getInt("recipeId", 0) ?: 0

                            val fullRecipeViewModel by viewModels<FullRecipeViewModel>()
                            fullRecipeViewModel.getFullRecipe(recipeId)
                            val state by fullRecipeViewModel.state.collectAsState()

                            FullRecipeScreen(
                                state = state,
                                insertIngredient = fullRecipeViewModel::insertIngredient,
                                insertPrepareMode = fullRecipeViewModel::insertPrepareMode,
                                updateIngredient = fullRecipeViewModel::updateIngredient,
                                updatePrepareMode = fullRecipeViewModel::updatePrepareMode,
                                deleteIngredient = fullRecipeViewModel::deleteIngredient,
                                deletePrepareMode = fullRecipeViewModel::deletePrepareMode,
                                this@MainActivity::alertToast
                            )
                        }
                    }
                }
            }
        }
    }

    private fun alertToast() {
        Toast.makeText(
            applicationContext,
            "You must fill the field with something",
            Toast.LENGTH_SHORT
        ).show()
    }
}
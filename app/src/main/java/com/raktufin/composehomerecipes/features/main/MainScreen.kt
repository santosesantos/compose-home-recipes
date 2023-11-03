@file:OptIn(ExperimentalFoundationApi::class)

package com.raktufin.composehomerecipes.features.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.raktufin.composehomerecipes.R
import com.raktufin.composehomerecipes.domain.models.RecipeDomain
import com.raktufin.composehomerecipes.features.dialog.InputDialog
import com.raktufin.composehomerecipes.features.main.viewmodel.MainUiState
import com.raktufin.composehomerecipes.ui.theme.Shapes

@Composable
fun MainScreen(
    state: MainUiState,
    insertRecipe: (String) -> Unit,
    navController: NavController,
    deleteRecipeMethod: (Int) -> Unit,
    alertToast: () -> Unit
) {
    val (showDialog, setShowDialog) = remember { mutableStateOf(false) }

    val (showDeleteDialog, setShowDeleteDialog) = remember { mutableStateOf(false) }
    val (targetRecipeId, setTargetRecipeId) = remember { mutableIntStateOf(0) }
    val (targetRecipeName, setTargetRecipeName) = remember { mutableStateOf("") }

    if (showDialog) {
        InputDialog(
            onDismissRequest = { setShowDialog(false) },
            onConfirmation = {
                insertRecipe(it)
                setShowDialog(false)
            },
            title = "New recipe",
            placeholder = "Recipe",
            alertToast = alertToast
        )
    }

    if (showDeleteDialog) {
        AlertDialog(
            icon = {
                Icon(Icons.Filled.Delete, contentDescription = "Delete icon")
            },
            title = {
                Text(text = "Delete recipe?")
            },
            text = {
                Text(text = "Are you sure you want to delete $targetRecipeName?")
            },
            onDismissRequest = {
                setShowDeleteDialog(false)
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        deleteRecipeMethod(targetRecipeId)
                        setShowDeleteDialog(false)
                    }
                ) {
                    Text(
                        text = "Delete",
                        color = Color(0xFFE32227)
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        setShowDeleteDialog(false)
                    }
                ) {
                    Text(text = "Cancel")
                }
            }
        )
    }

    when (state) {
        MainUiState.Loading -> ColumnTemplate {
            Progress()
        }

        is MainUiState.Error -> ColumnTemplate {
            ErrorText(message = state.message)
        }

        MainUiState.Empty -> ColumnTemplate {
            EmptyText()
        }

        is MainUiState.Success -> LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.recipes) { recipe ->
                RecipeInfo(
                    recipe,
                    cardOnClick = { navController.navigate("full-recipe-screen/${recipe.id}") },
                    setShowDeleteDialog, setTargetRecipeId, setTargetRecipeName
                )
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 32.dp, end = 16.dp)
    ) {
        ExtendedFloatingActionButton(
            onClick = { setShowDialog(true) },
            icon = { Icon(Icons.Filled.Add, "Insert new Recipe button") },
            text = { Text(text = "New Recipe") },
            modifier = Modifier
                .align(Alignment.BottomEnd)
        )
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen(
        state = MainUiState.Empty,
        insertRecipe = {},
        navController = rememberNavController(),
        {},
        {}
    )
}

@Composable
fun ColumnTemplate(composable: @Composable ColumnScope.() -> Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        content = composable
    )
}

@Composable
fun Progress() {
    CircularProgressIndicator(
        modifier = Modifier
            .width(64.dp)
    )
}

@Composable
fun ErrorText(message: String) {
    Text(
        text = message,
        style = MaterialTheme.typography.headlineMedium.copy(color = Color(0xFFE32227)),
        modifier = Modifier
    )
}

@Composable
fun EmptyText() {
    Text(
        text = stringResource(R.string.empty_text_recipes),
        style = MaterialTheme.typography.headlineSmall,
    )
}

@Composable
fun RecipeInfo(
    recipe: RecipeDomain,
    cardOnClick: () -> Unit,
    setShowDeleteDialog: (Boolean) -> Unit,
    setTargetRecipeId: (Int) -> Unit,
    setTargetRecipeName: (String) -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        shape = Shapes.large,
        modifier = Modifier
            .combinedClickable(
                enabled = true,
                onClick = cardOnClick,
                onLongClick = {
                    setTargetRecipeId(recipe.id)
                    setTargetRecipeName(recipe.name)
                    setShowDeleteDialog(true)
                }
            )
            .fillMaxWidth()
            .height(intrinsicSize = IntrinsicSize.Min)
    ) {
        Box(
            modifier = Modifier
                .padding(32.dp)
        ) {
            Text(
                text = recipe.name,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}

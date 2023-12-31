package com.raktufin.composehomerecipes.features.fullrecipe

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.raktufin.composehomerecipes.domain.models.FullRecipeDomain
import com.raktufin.composehomerecipes.domain.models.IngredientDomain
import com.raktufin.composehomerecipes.domain.models.PrepareModeDomain
import com.raktufin.composehomerecipes.domain.models.RecipeDomain
import com.raktufin.composehomerecipes.extensions.innerShadow
import com.raktufin.composehomerecipes.features.dialog.InputDialog
import com.raktufin.composehomerecipes.features.dialog.UpdateDialog
import com.raktufin.composehomerecipes.features.fab.ExpandableFAB
import com.raktufin.composehomerecipes.features.fullrecipe.viewmodel.FullRecipeState
import com.raktufin.composehomerecipes.ui.theme.Shapes

@Preview
@Composable
fun ScreenPreview() {
    FullRecipeScreen(state = FullRecipeState.Success(
        FullRecipeDomain(
            recipe = RecipeDomain(name = "Tonight"),
            ingredients = listOf(IngredientDomain(name = "Tonight", recipeId = 0)),
            prepareModes = listOf(PrepareModeDomain(name = "Tonight", recipeId = 0))
        )
    ), { _ -> }, { _ -> }, {_, _ ->}, {_, _ ->}, {_ -> }, {_ ->}, {})
}

@Composable
fun FullRecipeScreen(
    state: FullRecipeState,
    insertIngredient: (ingredientName: String) -> Unit,
    insertPrepareMode: (prepareModeName: String) -> Unit,
    updateIngredient: (ingredientId: Int, ingredientName: String) -> Unit,
    updatePrepareMode: (prepareModeId: Int, prepareModeName: String) -> Unit,
    deleteIngredient: (ingredientId: Int) -> Unit,
    deletePrepareMode: (prepareModeId: Int) -> Unit,
    alertToast: () -> Unit
) {
    val (showDialog, setShowDialog) = remember { mutableStateOf(false) }

    val (dialogTitle, setDialogTitle) = remember { mutableStateOf("") }
    val (dialogPlaceholder, setDialogPlaceholder) = remember { mutableStateOf("") }
    val (insertMethod, setInsertMethod) = remember { mutableStateOf(insertIngredient) }

    val (isInputDialog, setIsInputDialog) = remember { mutableStateOf(true) }
    val (updateMethod, setUpdateMethod) = remember { mutableStateOf(updateIngredient) }
    val (deleteMethod, setDeleteMethod) = remember { mutableStateOf(deleteIngredient) }
    val (itemName, setItemName) = remember { mutableStateOf("") }
    val (itemId, setItemId) = remember { mutableIntStateOf(0) }

    if (showDialog) {
        if (isInputDialog) {
            InputDialog(
                onDismissRequest = { setShowDialog(false) },
                onConfirmation = {
                    insertMethod(it)
                    setShowDialog(false)
                },
                title = dialogTitle,
                placeholder = dialogPlaceholder,
                alertToast = alertToast
            )
        } else {
            UpdateDialog(
                onDismissRequest = { setShowDialog(false) },
                onUpdate = { name ->
                    updateMethod(itemId, name)
                    setShowDialog(false)
                },
                onDelete = {
                    deleteMethod(itemId)
                    setShowDialog(false)
                },
                itemName = itemName,
                title = dialogTitle,
                placeholder = dialogPlaceholder,
                alertToast = alertToast
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.primary)
                .padding(all = 8.dp)
        ) {
            when (state) {
                is FullRecipeState.Success -> Text(
                    text = state.fullRecipe.recipe.name,
                    style = MaterialTheme.typography.displayMedium.copy(color = MaterialTheme.colorScheme.onPrimary)
                )

                else -> Progress()
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(16.dp)
        ) {
            SectionTemplate {
                IngredientSection(state) { itemId, itemName ->
                    setDialogTitle("Update ingredient")
                    setDialogPlaceholder("Ingredient")
                    setItemId(itemId)
                    setItemName(itemName)
                    setUpdateMethod(updateIngredient)
                    setDeleteMethod(deleteIngredient)
                    setIsInputDialog(false)
                    setShowDialog(true)
                }
            }
            SectionTemplate {
                PrepareModeSection(state) { itemId, itemName ->
                    setDialogTitle("Update prepare mode")
                    setDialogPlaceholder("Prepare mode")
                    setItemId(itemId)
                    setItemName(itemName)
                    setUpdateMethod(updatePrepareMode)
                    setDeleteMethod(deletePrepareMode)
                    setIsInputDialog(false)
                    setShowDialog(true)
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        ExpandableFAB(
            onAddIngredient = {
                setDialogTitle("New ingredient")
                setDialogPlaceholder("Ingredient")
                setInsertMethod(insertIngredient)
                setShowDialog(true)
            },
            onAddPrepareMode = {
                setDialogTitle("New prepare mode")
                setDialogPlaceholder("Prepare mode")
                setInsertMethod(insertPrepareMode)
                setShowDialog(true)
            },
            modifier = Modifier
                .padding(bottom = 32.dp, end = 16.dp)
                .align(Alignment.BottomEnd)
        )
    }
}

@Composable
fun SectionTemplate(composable: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f),
        content = composable
    )
}

@Composable
fun IngredientSection(state: FullRecipeState, showDialog: (Int, String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        Text(
            text = "Ingredients",
            style = MaterialTheme.typography.displaySmall
        )
    }

    when (state) {
        FullRecipeState.Loading -> ColumnTemplate {
            Progress()
        }

        is FullRecipeState.Error -> ColumnTemplate {
            ErrorText(message = state.message)
        }

        is FullRecipeState.Success -> LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = MaterialTheme.colorScheme.primary, shape = Shapes.medium)
                .innerShadow(
                    blur = 20.dp,
                    color = Color(0xff444444),
                    cornersRadius = 16.dp,
                    offsetX = 0.5.dp,
                    offsetY = 0.5.dp
                )
        ) {
            items(state.fullRecipe.ingredients) { ingredient ->
                ItemList(itemId = ingredient.id, itemName = ingredient.name, showDialog)
            }
        }
    }
}

@Composable
fun PrepareModeSection(state: FullRecipeState, showDialog: (Int, String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        Text(
            text = "Prepare modes",
            style = MaterialTheme.typography.displaySmall
        )
    }

    when (state) {
        FullRecipeState.Loading -> ColumnTemplate {
            Progress()
        }

        is FullRecipeState.Error -> ColumnTemplate {
            ErrorText(message = state.message)
        }

        is FullRecipeState.Success -> LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = MaterialTheme.colorScheme.primary, shape = Shapes.medium)
                .innerShadow(
                    blur = 20.dp,
                    color = Color(0xff444444),
                    cornersRadius = 16.dp,
                    offsetX = 0.5.dp,
                    offsetY = 0.5.dp
                )
        ) {
            items(state.fullRecipe.prepareModes) { prepareMode ->
                ItemList(itemId = prepareMode.id, itemName = prepareMode.name, showDialog)
            }
        }
    }
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
fun ItemList(itemId: Int, itemName: String, showDialog: (Int, String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                showDialog(itemId, itemName)
            }
            .padding(8.dp)
    ) {
        Text(
            text = itemName,
            style = MaterialTheme.typography.headlineSmall.copy(color = MaterialTheme.colorScheme.onPrimary)
        )
    }
}
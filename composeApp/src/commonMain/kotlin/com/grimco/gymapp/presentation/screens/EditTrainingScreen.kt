package com.grimco.gymapp.presentation.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.grimco.gymapp.data.dtos.Exercise
import com.grimco.gymapp.data.model.TrainingExercisesEntity
import com.grimco.gymapp.presentation.components.ExerciseCard
import com.grimco.gymapp.presentation.components.ExerciseCardContainer
import com.grimco.gymapp.presentation.components.TextHeader
import com.grimco.gymapp.presentation.viewmodel.EditTrainingViewModel
import com.mohamedrejeb.calf.core.LocalPlatformContext
import com.mohamedrejeb.calf.io.readByteArray
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTrainingScreen(
    idTraining: Long,
    modifier: Modifier = Modifier
) {

    val viewModel: EditTrainingViewModel = koinViewModel()
    val discipline by viewModel.disciplineEditor.input.collectAsStateWithLifecycle()
    val trainingList by viewModel.training.collectAsStateWithLifecycle()
    val trainingState = rememberTextFieldState("")

    var showDialogExercise by remember { mutableStateOf(false)}

    val scope = rememberCoroutineScope()

    val bottomSheetState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.PartiallyExpanded
        )
    )

    val context = LocalPlatformContext.current

    val pickerLauncher = rememberFilePickerLauncher(
        type = FilePickerFileType.Image,
        selectionMode = FilePickerSelectionMode.Single,
        onResult = { files ->
            scope.launch {
                files.firstOrNull()?.let { file ->
                    viewModel.saveImage(idTraining,file.readByteArray(context))
                }
            }
        }
    )

    var canSwipe by remember { mutableStateOf(false) }

    BottomSheetScaffold(
        scaffoldState = bottomSheetState,
        sheetContent = {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(16.dp)
                    ,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextHeader("Exercise list")

                    TextButton(onClick = {
                        showDialogExercise = true
                    }) {
                        Text("New Exercise", color = MaterialTheme.colorScheme.tertiary)
                    }
                }

                if (canSwipe) {
                    val exercisesList by viewModel.exercises.collectAsStateWithLifecycle()
                    ExerciseCardContainer(
                        exercisesList,
                        onAdd = {
                            scope.launch {
                                viewModel.addExerciseTraining(idTraining, it)
                            }
                        }
                    )
                }

            }

        },
        sheetPeekHeight = 0.dp,
        sheetSwipeEnabled = canSwipe
    ) {
        Column(
            modifier = modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(150.dp)
                    .border(width = 2.dp, color = Color.Gray)
                    .clickable {
                        pickerLauncher.launch()
                    }
            ){
                Icon(imageVector = Icons.Default.Image, contentDescription = null)
                trainingList?.let {
                    AsyncImage(
                        model = it.training.image,
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }
            }

            OutlinedTextField(
                state = trainingState,
                label = {
                    Text("Training Name")
                },
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 20.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextHeader(text = "Exercise List")
                IconButton(
                    onClick = {
                        canSwipe = true
                        scope.launch {
                            bottomSheetState.bottomSheetState.expand()
                        }
                    }
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                }
            }

            LazyColumn {
                trainingList?.exercises?.let {
                    items(items = it) { exercise ->
                        ExerciseCard(
                            Exercise(
                                id = exercise.id,
                                name = exercise.name
                            ),
                            onDelete = {
                                scope.launch {
                                    viewModel.deleteTrainingExercise(
                                        TrainingExercisesEntity(
                                            idTraining,
                                            exercise.id
                                        )
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }

        if (showDialogExercise) {
            val exerciseState = rememberTextFieldState("")
            BasicAlertDialog(
                onDismissRequest = {
                    showDialogExercise = false
                    exerciseState.text.isNotEmpty().run {
                        viewModel.insertExercise(exerciseState.text.toString())
                    }
                }
            ){
                OutlinedTextField(
                    state = exerciseState,
                    label = {
                        Text("New Exercise Name")
                    }
                )
            }
        }

    }


    LaunchedEffect(canSwipe) {
        if (canSwipe) {
            viewModel.loadExercises()
        }
    }


    LaunchedEffect(discipline) {
        discipline?.let {
            if (trainingState.text.toString() != it) {
                trainingState.edit {
                    replace(0, length, it)
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        snapshotFlow { trainingState.text.toString() }
            .collect { viewModel.disciplineEditor.update(it) }
    }


    LaunchedEffect(idTraining) {
        viewModel.setIdTraining(idTraining)
    }
}
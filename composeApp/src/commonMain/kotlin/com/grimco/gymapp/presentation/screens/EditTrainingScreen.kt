package com.grimco.gymapp.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.BitmapImage
import coil3.Uri
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import com.darkrockstudios.libraries.mpfilepicker.FilePicker
import com.grimco.gymapp.data.dtos.Exercise
import com.grimco.gymapp.data.model.TrainingExercisesEntity
import com.grimco.gymapp.presentation.components.ExerciseCard
import com.grimco.gymapp.presentation.components.ExerciseCardContainer
import com.grimco.gymapp.presentation.components.TextHeader
import com.grimco.gymapp.presentation.viewmodel.EditTrainingViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTrainingScreen(
    idTraining: Long,
    modifier: Modifier = Modifier
) {
    val viewModel: EditTrainingViewModel = koinViewModel(parameters = { parametersOf(idTraining) })
    val discipline by viewModel.disciplineEditor.input.collectAsStateWithLifecycle()
    val trainingList by viewModel.training.collectAsStateWithLifecycle()
    val trainingState = rememberTextFieldState("")

    var showFilePicker by remember { mutableStateOf(false) }
    val image by viewModel.image.collectAsStateWithLifecycle()

    val scope = rememberCoroutineScope()

    val bottomSheetState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.PartiallyExpanded
        )
    )

    var canSwipe by remember { mutableStateOf(false) }

    BottomSheetScaffold(
        scaffoldState = bottomSheetState,
        sheetContent = {
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
        },
        sheetPeekHeight = 0.dp,
        sheetSwipeEnabled = canSwipe
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(200.dp)
                        .border(width = 2.dp, color = Color.Gray)
                        .clickable {
                            showFilePicker = true
                        }
                ){
                    Icon(imageVector = Icons.Default.Image, contentDescription = null)
                    image?.let {
                        AsyncImage(
                            model = image,
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

            FloatingActionButton(
                onClick = {},
                modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
            }

            val fileType = listOf("jpg","png","webp")
            FilePicker(show = showFilePicker, fileExtensions = fileType) { file ->
                showFilePicker = false

                file?.let {
                    scope.launch{
                        viewModel.setImage(it.platformFile)
                    }
                }
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
}
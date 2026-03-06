package com.example.androidlab01.homework_5.movieBase.ui.movies

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.androidlab01.R
import com.example.androidlab01.homework_5.movieBase.data.entity.MovieEntity
import com.example.androidlab01.homework_5.movieBase.domain.model.SortOption

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesScreen(
    viewModel: MoviesViewModel,
    onAddMovieClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val movies by viewModel.movies.collectAsState()
    val sheetState = rememberModalBottomSheetState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.movie_base_title)) },
                actions = {
                    TextButton(onClick = viewModel::showSortBottomSheet) {
                        Text(stringResource(R.string.movie_btn_sort))
                    }
                    IconButton(onClick = onProfileClick) {
                        Icon(Icons.Default.Person, contentDescription = stringResource(R.string.movie_cd_profile))
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddMovieClick) {
                Icon(Icons.Default.Add, contentDescription = stringResource(R.string.movie_cd_add_movie))
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (movies.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.movie_empty_list),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item { Spacer(modifier = Modifier.height(8.dp)) }
                    items(movies, key = { it.id }) { movie ->
                        MovieItem(
                            movie = movie,
                            onDelete = { viewModel.deleteMovie(movie) }
                        )
                    }
                    item { Spacer(modifier = Modifier.height(80.dp)) }
                }
            }
        }

        if (state.showSortBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = viewModel::hideSortBottomSheet,
                sheetState = sheetState
            ) {
                SortBottomSheetContent(
                    currentSortOption = state.sortOption,
                    onSortOptionSelected = viewModel::setSortOption
                )
            }
        }
    }
}

@Composable
private fun MovieItem(
    movie: MovieEntity,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row {
                    Text(
                        text = stringResource(R.string.movie_rating_format, movie.rating),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )

                    movie.year?.let {
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = it.toString(),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                movie.genre?.let {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                movie.comment?.let {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            IconButton(onClick = onDelete) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = stringResource(R.string.movie_cd_delete),
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
private fun SortBottomSheetContent(
    currentSortOption: SortOption,
    onSortOptionSelected: (SortOption) -> Unit
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.movie_btn_sort),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        SortOption.entries.forEach { option ->
            TextButton(
                onClick = { onSortOptionSelected(option) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = if (option == currentSortOption) stringResource(R.string.movie_sort_check) else stringResource(R.string.movie_sort_uncheck),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = option.getDisplayName(context),
                        color = if (option == currentSortOption)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}
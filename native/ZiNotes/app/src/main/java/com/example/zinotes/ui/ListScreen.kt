package com.example.zinotes.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.zinotes.ui.theme.ZiNotesTheme
import com.example.zinotes.R
import com.example.zinotes.data.DataSource
import com.example.zinotes.model.Hanzi
import com.example.zinotes.model.ListUiState
import com.example.zinotes.ui.viewmodel.ListViewModel



@Composable
fun ListScreen(
    onItemClick: (String) -> Unit,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ListViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    when (val state = uiState) {
        is ListUiState.Loading -> LoadingScreen(modifier = modifier)
        is ListUiState.Success -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
                modifier = modifier.fillMaxSize()
            ) {
                AddButton(onAddClick)
                HanziList(
                    hanziList = state.hanziList,
                    onItemClick = onItemClick,
                )
            }
        }
        is ListUiState.Error -> ErrorScreen(state.message, modifier = modifier)
    }

}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ){
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(message: String, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Text(text = message)
    }
}

@Composable
fun AddButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(R.drawable.chinese_coin),
        contentDescription = null,
        modifier = modifier
            .size(100.dp)
            .clickable(
                onClick = onClick
            )

    )
}

@Composable
fun HanziList(
    hanziList: List<Hanzi>,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        items(hanziList.size) {
            HanziCard(
                hanzi = hanziList[it],
                onSeeMoreClick = onItemClick,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun HanziCard(
    hanzi: Hanzi,
    onSeeMoreClick: (String) -> Unit,
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier,
        shape = RectangleShape,
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ){
        Box(
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(R.drawable.horizontal_scroll),
                contentDescription = null,
                modifier = Modifier,
                contentScale = ContentScale.FillBounds
            )
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(R.dimen.card_content))
            ) {
                Text(
                    text = hanzi.pinyin + hanzi.tones.map { c -> c.toString() }.reduce { acc, s -> "$acc,$s" },
                )
                Text(
                    text = hanzi.definitions?.get(0) ?: "",
                )
                IconButton(
                    onClick = { onSeeMoreClick(hanzi.id) },
                    modifier = Modifier.padding(1.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.slanted),
                        contentDescription = null,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun HanziCardPreview() {
    ZiNotesTheme {
        HanziCard(DataSource.hanziList[0], {})
    }
}

@Preview
@Composable
fun HanziListPreview() {
    ZiNotesTheme {
        HanziList(DataSource.hanziList, {})
    }
}

@Preview
@Composable
fun AddButtonPreview() {
    ZiNotesTheme {
        AddButton({})
    }
}

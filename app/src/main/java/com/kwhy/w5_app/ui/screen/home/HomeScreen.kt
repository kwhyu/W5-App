
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kwhy.w5_app.di.Injection
import com.kwhy.w5_app.model.Waifu
import com.kwhy.w5_app.ui.common.UiState
import com.kwhy.w5_app.ui.components.SearchBar
import com.kwhy.w5_app.ui.screen.ViewModelFactory
import com.kwhy.w5_app.ui.screen.home.HomeViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Int) -> Unit,
    ){
    val query by viewModel.query
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.waifuSearch(query)
            }
            is UiState.Success -> {
                HomeContent(
                    query = query,
                    onQueryChange = viewModel::waifuSearch,
                    listWaifu = uiState.data,
                    onFavoriteButtonClicked = { id, newState ->
                        viewModel.updateFavorite(id, newState)
                    },
                    navigateToDetail = navigateToDetail
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun HomeContent(
    query: String,
    onQueryChange: (String) -> Unit,
    listWaifu: List<Waifu>,
    onFavoriteButtonClicked: (id: Int, newState: Boolean) -> Unit,
    modifier: Modifier = Modifier,
    navigateToDetail: (Int) -> Unit,
    ){
    Column {
        SearchBar(
            query = query,
            onQueryChange = onQueryChange,
            modifier = Modifier.background(MaterialTheme.colorScheme.primary)
        )
        ListWaifu(
            listWaifu = listWaifu,
            onFavoriteButtonClicked = onFavoriteButtonClicked,
            navigateToDetail = navigateToDetail
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListWaifu(
    listWaifu: List<Waifu>,
    onFavoriteButtonClicked: (id: Int, newState: Boolean) -> Unit,
    modifier: Modifier = Modifier,
    navigateToDetail: (Int) -> Unit,
){
    LazyColumn(
        contentPadding = PaddingValues(bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(listWaifu, key = { it.id }) { waifu ->
            WaifuItem(
                id = waifu.id,
                name = waifu.name,
                image = waifu.image,
                game = waifu.game,
                isFavorite = waifu.isFavorite,
                onFavoriteButtonClicked = onFavoriteButtonClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .animateItemPlacement(tween(durationMillis = 100))
                    .clickable{
                        navigateToDetail(waifu.id)
                    }
            )
        }
    }
}




import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kwhy.w5_app.di.Injection
import com.kwhy.w5_app.model.Waifu
import com.kwhy.w5_app.ui.common.UiState
import com.kwhy.w5_app.ui.screen.ViewModelFactory
import com.kwhy.w5_app.ui.screen.favorite.FavoriteViewModel

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Int) -> Unit,
    ){
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getWaifuFavorite()
            }
            is UiState.Success -> {
                FavoriteContent(
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
fun FavoriteContent(
    listWaifu: List<Waifu>,
    onFavoriteButtonClicked: (id: Int, newState: Boolean) -> Unit,
    modifier: Modifier = Modifier,
    navigateToDetail: (Int) -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        ListWaifu(
            listWaifu = listWaifu,
            onFavoriteButtonClicked = onFavoriteButtonClicked,
            navigateToDetail = navigateToDetail
        )
    }
}
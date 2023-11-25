
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.Top
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.kwhy.w5_app.di.Injection
import com.kwhy.w5_app.model.Waifu
import com.kwhy.w5_app.ui.common.UiState
import com.kwhy.w5_app.ui.screen.ViewModelFactory
import com.kwhy.w5_app.ui.screen.detail.DetailViewModel

@Composable
fun DetailScreen(
    waifuId: Int,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    )
    ){
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getWaifuById(waifuId)
            }

            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    waifu = data,
                    navigateBack = navigateBack,
                    onFavoriteButtonClicked = { id, state ->
                        viewModel.updateFavorite(id, state)
                    }
                )
            }

            is UiState.Error -> {}
        }
    }

}

@Composable
fun DetailContent(
    waifu: Waifu,
    navigateBack: () -> Unit,
    onFavoriteButtonClicked: (id: Int, state: Boolean) -> Unit,
    modifier: Modifier = Modifier
){
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(bottom = 16.dp)
        ) {
            AsyncImage(
                model = waifu.image,
                contentDescription = "waifu_image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(
                        shape = RoundedCornerShape(
                            bottomStart = 200.dp,
                        )
                    )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = waifu.name,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.padding(end = 10.dp))
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Affiliation:",
                        modifier = Modifier.align(Top)

                    )
                    Spacer(modifier = Modifier.padding(end = 5.dp))
                    Text(
                        text = waifu.affiliation,
                        modifier = Modifier
                            .padding(start = 4.dp, end = 8.dp)
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Status:",
                        modifier = Modifier.align(Top)

                    )
                    Spacer(modifier = Modifier.padding(end = 5.dp))
                    Text(
                        text = waifu.status,
                        modifier = Modifier
                            .padding(start = 4.dp, end = 8.dp)
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Game:",
                        modifier = Modifier.align(Top)

                    )
                    Spacer(modifier = Modifier.padding(end = 5.dp))
                    Text(
                        text = waifu.game,
                        modifier = Modifier
                            .padding(start = 4.dp, end = 8.dp)
                    )
                }
                Divider(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp))
                Text(
                    text = waifu.description,
                    fontSize = 16.sp,
                    lineHeight = 28.sp,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        SmallFloatingActionButton(
            onClick = {
                onFavoriteButtonClicked(waifu.id, waifu.isFavorite)
            },
            modifier = Modifier
                .align(BottomEnd)
                .padding(bottom = 8.dp)
        ) {
            Icon(
                imageVector = if (!waifu.isFavorite) Icons.Default.FavoriteBorder else Icons.Default.Favorite,
                contentDescription = if (!waifu.isFavorite) "Add to Favorite" else "Remove from Favorite",
                tint = if (!waifu.isFavorite) Color.Black else Color.Red
            )
        }
    }
}

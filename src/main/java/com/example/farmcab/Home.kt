import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.farmcab.R

@Composable
fun HomeScreen() {
    val products = remember {
        mutableStateListOf(
            Product("Tractor", R.drawable.img, "₹500/day", 4.8),
            Product("Rotavator", R.drawable.img_2, "₹300/day", 4.5),
            Product("Cultivator", R.drawable.img_3, "₹400/day", 4.6),
            Product("Combine harvester", R.drawable.img_1, "₹1000/day", 4.7)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F6F6))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {

        }

        SearchBar()
        CategorySection()
        ProductList(products)
    }
}

@Composable
fun SearchBar() {
    var searchQuery by remember { mutableStateOf("") }

    TextField(
        value = searchQuery,
        onValueChange = { searchQuery = it },
        placeholder = { Text("Search for equipment...") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White),
        singleLine = true
    )
}

@Composable
fun CategorySection() {
    val categories = listOf("Tractors", "Cultivators", "Rotavators", "Plows")

    LazyRow(modifier = Modifier.padding(16.dp)) {
        items(categories) { category ->
            Text(
                text = category,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF4CAF50))
                    .padding(8.dp)
                    .clickable { }
            )
        }
    }
}

@Composable
fun ProductList(products: List<Product>) {
    LazyColumn(
        modifier = Modifier.padding(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(products) { product ->
            ProductCard(product)
        }
    }
}

@Composable
fun ProductCard(product: Product) {
    val imagePainter = rememberAsyncImagePainter(model = ImageRequest.Builder(LocalContext.current)
        .data(product.imageRes)
        .crossfade(true)
        .build()
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = imagePainter,
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = product.name, fontSize = 20.sp)
            Text(text = product.price, fontSize = 16.sp, color = Color.Gray)
            Text(text = "⭐ ${product.rating}", fontSize = 16.sp, color = Color(0xFFFFA000))
        }
    }
}

// ✅ Data Class for Product
data class Product(val name: String, val imageRes: Int, val price: String, val rating: Double)
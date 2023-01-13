package ps.maytheu.tipapp

import android.graphics.drawable.shapes.Shape
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ps.maytheu.tipapp.component.InputField
import ps.maytheu.tipapp.ui.theme.TipAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipAppTheme {
                // A surface container using the 'background' color from the theme
                MyApp {
                    //    TopMenu()
                    MainContent()
                }
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    Surface(color = MaterialTheme.colors.background) {
        content()
    }
}

@Composable
fun TopMenu(totalPerson: Double = 134.0) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
//            .clip(shape = CircleShape.copy(all = CornerSize(12.dp))),
            .clip(shape = RoundedCornerShape(corner = CornerSize(12.dp))),
        color = Color(0xFFED7F71),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val total = "%.2f".format(totalPerson)
            Text(text = "Total Per Person", style = MaterialTheme.typography.h5)

            Text(
                text = "$$total",
                style = MaterialTheme.typography.h3,
                fontWeight = FontWeight.Bold
            )
        }

    }
}

@Composable
fun MainContent() {
FormField{amount->
    Log.d("TAG", "MainContent: $amount")
}

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FormField(modifier: Modifier = Modifier, valChanged: (String) -> Unit) {
    val totalBill = remember { mutableStateOf("") }
    val validState = remember(totalBill.value) {
        totalBill.value.trim().isNotEmpty()
    }
    val keyController = LocalSoftwareKeyboardController.current
    Surface(
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(10.dp)),
//            .height(450.dp)
//            .clip(shape = CircleShape.copy(all = CornerSize(12.dp))),
        elevation = 5.dp, border = BorderStroke(width = 1.dp, color = Color.LightGray)
    ) {
        Column() {
            InputField(
                valueOfFieldState = totalBill,
                label = "Enter Bill",
                inputEnabled = true,
                isMultipleLine = false,
                onAction = KeyboardActions {
                    if (!validState) return@KeyboardActions
                    //get the value
                    valChanged(totalBill.value.trim())
                    //dismiss keyboard
                    keyController?.hide()
                }
            )

        }

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TipAppTheme {
        MyApp {
            MainContent()
        }
    }
}
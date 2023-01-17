package ps.maytheu.tipapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import ps.maytheu.tipapp.util.calculatePerPerson
import ps.maytheu.tipapp.util.calculateTip
import ps.maytheu.tipapp.widgets.RoundIconButton

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipAppTheme {
                // A surface container using the 'background' color from the theme
                MyApp {
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
fun TopMenu(totalPerson: Double = 0.0) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(10.dp)
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
                text = "$$total", style = MaterialTheme.typography.h3, fontWeight = FontWeight.Bold
            )
        }

    }
}

@Composable
fun MainContent() {
    val tipAmountState = remember {
        mutableStateOf(0.0)
    }
    val totalPerPerson = remember {
        mutableStateOf(0.0)
    }
    val numPeople = remember {
        mutableStateOf(1)
    }
    Column(modifier = Modifier.padding(2.dp)) {

        FormField(
            splitByPeopleState = numPeople,
            tipAmountState = tipAmountState,
            totalPersonState = totalPerPerson
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FormField(
    modifier: Modifier = Modifier,
    range: IntRange = 1..100,
    splitByPeopleState: MutableState<Int>,
    tipAmountState: MutableState<Double>,
    totalPersonState: MutableState<Double>,
    valChanged: (String) -> Unit={},
) {
    val totalBill = remember { mutableStateOf("") }
    val validState = remember(totalBill.value) {
        totalBill.value.trim().isNotEmpty()
    }
    val keyController = LocalSoftwareKeyboardController.current

    val sliderState = remember {
        mutableStateOf(0f)
    }
    val tipPercentage = (sliderState.value * 100).toInt()

    TopMenu(totalPersonState.value)

    Surface(
        modifier = modifier
            .padding(3.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(10.dp)),
//            .height(450.dp)
//            .clip(shape = CircleShape.copy(all = CornerSize(12.dp))),
        elevation = 5.dp,
        border = BorderStroke(width = 1.dp, color = Color.LightGray)
    ) {
        Column(
            modifier = modifier.padding(5.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {

            InputField(valueOfFieldState = totalBill,
                label = "Enter Bill",
                inputEnabled = true,
                isMultipleLine = false,
                onAction = KeyboardActions {
                    if (!validState) return@KeyboardActions
                    //get the value
                    valChanged(totalBill.value.trim())

                    totalPersonState.value = calculatePerPerson(
                        total = totalBill.value.toDouble(),
                        splitBy = splitByPeopleState.value,
                        tipPercentage = tipPercentage
                    )
                    //dismiss keyboard
                    keyController?.hide()
                })

            if (validState) {
                //button menu and info
                Row(modifier = modifier.padding(3.dp), horizontalArrangement = Arrangement.Start) {
                    Text(
                        text = "Split",
                        modifier = Modifier.align(alignment = Alignment.CenterVertically)
                    )

                    Spacer(modifier = Modifier.width(150.dp))

                    //button menu
                    Row(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        RoundIconButton(imageVector = Icons.Default.Remove, onClick = {
                            if (splitByPeopleState.value === 1) 1
                            else {
                                splitByPeopleState.value = splitByPeopleState.value - 1

                                totalPersonState.value = calculatePerPerson(
                                    total = totalBill.value.toDouble(),
                                    splitBy = splitByPeopleState.value,
                                    tipPercentage = tipPercentage
                                )
                            }
                        })

                        Text(
                            text = splitByPeopleState.value.toString(),
                            modifier = Modifier
                                .align(alignment = Alignment.CenterVertically)
                                .padding(horizontal = 9.dp)
                        )

                        RoundIconButton(imageVector = Icons.Default.Add,
                            onClick = {
                                if (splitByPeopleState.value < range.last) {
                                    splitByPeopleState.value = splitByPeopleState.value + 1

                                    totalPersonState.value = calculatePerPerson(
                                        total = totalBill.value.toDouble(),
                                        splitBy = splitByPeopleState.value,
                                        tipPercentage = tipPercentage
                                    )
                                }
                            })
                    }
                }

                //tip
                Row(
                    modifier = modifier.padding(horizontal = 3.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "Tip",
                        modifier = modifier.align(alignment = Alignment.CenterVertically)
                    )

                    Spacer(modifier = modifier.width(250.dp))

                    Text(
                        text = "$ ${tipAmountState.value}",
                        modifier = modifier.align(alignment = Alignment.CenterVertically)
                    )
                }

                //slider
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "$tipPercentage %")

                    Spacer(modifier = modifier.height(30.dp))

                    Slider(
                        value = sliderState.value,
                        onValueChange = { newVal ->
                            sliderState.value = newVal
                            tipAmountState.value = calculateTip(
                                total = totalBill.value.toDouble(),
                                tipPercentage = tipPercentage
                            )
                            totalPersonState.value = calculatePerPerson(
                                total = totalBill.value.toDouble(),
                                splitBy = splitByPeopleState.value,
                                tipPercentage = tipPercentage
                            )
                        },
                        modifier = modifier.padding(horizontal = 16.dp),
                        steps = 5,
                        onValueChangeFinished = { Log.d("TAG finished", "FormField: end") })
                }
            } else {
                Box {}
            }

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
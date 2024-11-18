package com.example.rgb_selector


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rgb_selector.ui.theme.RGB_SelectorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RGB_SelectorTheme {
                var red by remember { mutableStateOf<Int?>(255) }
                var green by remember { mutableStateOf<Int?>(255) }
                var blue by remember { mutableStateOf<Int?>(255) }
                val colorTexto =
                    if (red!! < 128 && green!! < 128 && blue!! < 128) Color.White else Color.Black
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier.padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        ColorSlider("Red", Color.Red,Color.Black, value = red!!, onValueChange = {red= it})
                        HorizontalDivider()
                        ColorSlider("Green", Color.Green,Color.Black, value = green!!, onValueChange = {green= it})
                        HorizontalDivider()
                        ColorSlider("Blue", Color.Blue,Color.Black, value = blue!!, onValueChange = {blue= it})
                        HorizontalDivider()

                    Column(
                        modifier = Modifier.fillMaxSize()
                            .background(Color(red!!, green!!, blue!!)),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CopyableText(
                            "rgb($red,$green,$blue)",
                            colorTexto
                        )
                        CopyableText(
                            "#${red!!.toString(16)}${green!!.toString(16)}${blue!!.toString(16)}",
                            colorTexto
                        )

                    }

                }
            }
        }

        }
    }
}

@Composable
fun LazyRowColorItem(red:Int, green: Int,blue: Int,text: String,onButtonClick: () -> Unit){

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = {onButtonClick()},
            colors = ButtonDefaults
                .buttonColors(Color(red, green, blue)),
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier.padding(5.dp).height(70.dp)
        ) {
            Text(
                text,
                color = Color.White,
                textAlign =
                TextAlign.Center
            )
        }
    }
}


@Composable
fun ColorSlider(
    label: String,
    gradientStart: Color,
    gradientEnd: Color,
    value: Int,
    onValueChange: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp)
    ) {
        Text(
            text = "$label: $value",
            color = gradientStart,
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp) // Altura personalizada
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(gradientStart, gradientEnd)
                    ),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                )
        ) {
            // Slider sobrepuesto
            Slider(
                value = value.toFloat(),
                valueRange = 0f..255f,
                onValueChange = { onValueChange(it.toInt()) },
                colors = SliderDefaults.colors(
                    thumbColor = Color.White, // Color del thumb
                    activeTrackColor = Color.Transparent, // Transparente para usar fondo
                    inactiveTrackColor = Color.Transparent // Sin pista por defecto
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
            )
        }

    }
}

@Composable
fun CopyableText(text: String, color: Color) {
    val clipboardManager = LocalClipboardManager.current

    Text(
        text = text,
        textAlign = TextAlign.Center,
        fontSize = 25.sp,
        color = color,
        modifier = Modifier
            .clickable {
                clipboardManager.setText(AnnotatedString(text)) // Copia al portapapeles
            }
            .padding(8.dp) // Añade espacio para facilitar el toque
    )
}
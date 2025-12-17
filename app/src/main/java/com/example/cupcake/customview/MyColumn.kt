package com.example.cupcake.customview

import androidx.compose.foundation.background
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import com.example.cupcake.theme.White

@Composable
fun MyColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints)
        }
        layout(constraints.maxWidth, constraints.maxHeight) {
            var x = 0
            var y = 0
            placeables.forEach { placeable ->
                placeable.placeRelative(x = x, y = y)
                x += placeable.width
                y += placeable.height
            }
        }
    }
}

@Preview
@Composable
fun PreviewMyColumn() {
    MyColumn(
        modifier = Modifier
            .background(White)
    ) {
        Text("Ушки")
        Text("Глазик")
        Text("Носик")
        Text("Хвостик")
        Text("Получился")
        Text("Добрый")
        Text("Котик")
    }
}
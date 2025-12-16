package com.example.cupcake.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cupcake.R
import com.example.cupcake.theme.CupcakeTheme
import com.example.cupcake.theme.Dimens

@Composable
fun StartScreen(
    modifier: Modifier = Modifier,
    onOrderCupcake: (Int) -> Unit
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(Dimens.SideMargin),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            Dimens.MarginBetweenElements,
            Alignment.CenterVertically
        )
    ) {
        Image(
            painter = painterResource(R.drawable.cupcake),
            contentDescription = null,
            modifier = Modifier
                .size(Dimens.ImageSize),
            contentScale = ContentScale.Fit
        )
        Text(
            text = "Order Cupcakes",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.SemiBold
            ),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = Dimens.TitleTopMargin,
                    bottom = Dimens.TitleBottomMargin
                )
        )
        OrderButton(
            text = "One cupcake",
            onClick = { onOrderCupcake(1) }
        )

        OrderButton(
            text = "Six cupcakes",
            onClick = { onOrderCupcake(6) }
        )

        OrderButton(
            text = "Twelve cupcakes",
            onClick = { onOrderCupcake(12) }
        )
    }
}

@Composable
fun OrderButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .width(Dimens.OrderCupcakeButtonWidth),
        shape = RoundedCornerShape(2.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
            disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 2.dp,
            pressedElevation = 8.dp,
            disabledElevation = 0.dp
        )
    ) {
        Text(
            text = text.uppercase(),
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.1.sp

            )
        )
    }
}

@Composable
@Preview
private fun Preview() {
    CupcakeTheme {
        StartScreen(
            modifier = Modifier
                .fillMaxSize(),
            onOrderCupcake = {}
        )
    }
}
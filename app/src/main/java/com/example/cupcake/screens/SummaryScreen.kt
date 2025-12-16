package com.example.cupcake.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cupcake.model.HomeState
import com.example.cupcake.theme.CupcakeTheme
import com.example.cupcake.theme.Dimens
import kotlin.String

@Composable
fun SummaryScreen(
    modifier: Modifier = Modifier,
    state: HomeState,
    onSendOrder: () -> Unit,
    onCancelOrder: () -> Unit,
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(Dimens.SideMargin),
        horizontalAlignment = Alignment.Start
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val quantity = state.quantity
            OrderDetailItem(
                label = "Quantity",
                value = if (quantity == 1) "$quantity cupcake"
                else "$quantity cupcakes"
            )
            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
            OrderDetailItem(
                label = "Flavor",
                value = state.flavor
            )
            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
            OrderDetailItem(
                label = "Pickup Date",
                value = state.date
            )
            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
            Text(
                text = "Total $${state.price}".uppercase(),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = onSendOrder,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(2.dp),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 2.dp
            ),
        ) {
            Text(
                text = "Send order to another app".uppercase(),
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
        OutlinedButton(
            onClick = onCancelOrder,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            shape = RoundedCornerShape(2.dp)
        ) {
            Text(
                text = "Cancel".uppercase(),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun OrderDetailItem(
    label: String, value: String, modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = label.uppercase(),
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}


@Composable
@Preview
private fun Preview() {
    CupcakeTheme {
        SummaryScreen(
            modifier = Modifier.fillMaxSize(),
            onCancelOrder = {},
            onSendOrder = {},
            state = HomeState(
                quantity = 0, flavor = "Vanilla", date = "", price = 5.0
            )
        )
    }
}
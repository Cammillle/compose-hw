package com.example.cupcake

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.cupcake.model.HomeState
import com.example.cupcake.screens.FlavorScreen
import com.example.cupcake.screens.PickupScreen
import com.example.cupcake.screens.StartScreen
import com.example.cupcake.screens.SummaryScreen
import com.example.cupcake.theme.CupcakeTheme
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CupcakeScreensUITest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private val flavors = listOf("Vanilla", "Chocolate", "Red Velvet", "Salted Caramel", "Coffee")
    private val dateOptions = listOf("Today", "Tomorrow", "Day after", "Next week")

    @Test
    fun startScreen_buttonsClick_correctQuantity() {
        var quantity = 0

        composeTestRule.setContent {
            CupcakeTheme {
                StartScreen(onOrderCupcake = { quantity = it })
            }
        }

        val buttonTexts = listOf(
            R.string.one_cupcake to 1,
            R.string.six_cupcakes to 6,
            R.string.twelve_cupcakes to 12
        )

        buttonTexts.forEach { (resId, expected) ->
            composeTestRule
                .onNodeWithText(composeTestRule.activity.getString(resId).uppercase())
                .performClick()
            assertEquals(expected, quantity)
        }
    }

    @Test
    fun flavorScreen_displayAndSelect() {
        var selectedFlavor = ""
        var nextClicked = false
        var cancelClicked = false

        composeTestRule.setContent {
            CupcakeTheme {
                FlavorScreen(
                    price = 6.0,
                    onSelectFlavor = { selectedFlavor = it },
                    onCancelOrder = { cancelClicked = true },
                    onNextButtonClicked = { nextClicked = true }
                )
            }
        }

        composeTestRule
            .onNodeWithText(composeTestRule.activity.getString(R.string.subtotal_price, 6.0))
            .assertExists()

        composeTestRule
            .onNodeWithText("Chocolate")
            .assertExists()
            .performClick()
        assertEquals("Chocolate", selectedFlavor)

        composeTestRule
            .onNodeWithText(composeTestRule.activity.getString(R.string.next).uppercase())
            .assertExists()
            .performClick()
        assertTrue(nextClicked)

        composeTestRule
            .onNodeWithText(composeTestRule.activity.getString(R.string.cancel).uppercase())
            .assertExists()
            .performClick()
        assertTrue(cancelClicked)
    }

    @Test
    fun pickupScreen_displayAndSelect() {
        var pickedDate = ""
        var nextClicked = false
        var cancelClicked = false

        composeTestRule.setContent {
            CupcakeTheme {
                PickupScreen(
                    price = 6.0,
                    dateOptions = dateOptions,
                    onDatePick = { pickedDate = it },
                    onCancelOrder = { cancelClicked = true },
                    onNextButtonClicked = { nextClicked = true }
                )
            }
        }

        composeTestRule
            .onNodeWithText(composeTestRule.activity.getString(R.string.subtotal_price, 6.0))
            .assertExists()

        composeTestRule
            .onNodeWithText("Tomorrow")
            .assertExists()
            .performClick()
        assertEquals("Tomorrow", pickedDate)

        composeTestRule
            .onNodeWithText(composeTestRule.activity.getString(R.string.next).uppercase())
            .assertExists()
            .performClick()
        assertTrue(nextClicked)

        composeTestRule
            .onNodeWithText(composeTestRule.activity.getString(R.string.cancel).uppercase())
            .assertExists()
            .performClick()
        assertTrue(cancelClicked)
    }

    @Test
    fun summaryScreen_displayAndActions() {
        val testState = HomeState(
            quantity = 3,
            flavor = "Chocolate",
            date = "Tomorrow",
            price = 9.0
        )
        var sentOrder = ""
        var cancelClicked = false

        composeTestRule.setContent {
            CupcakeTheme {
                SummaryScreen(
                    state = testState,
                    onSendOrder = { sentOrder = it },
                    onCancelOrder = { cancelClicked = true }
                )
            }
        }

        composeTestRule.onNodeWithText("3 cupcakes").assertExists()
        composeTestRule.onNodeWithText("Chocolate").assertExists()
        composeTestRule.onNodeWithText("Tomorrow").assertExists()
        composeTestRule
            .onNodeWithText(composeTestRule.activity.getString(R.string.total_price, 9.0).uppercase())
            .assertExists()

        composeTestRule
            .onNodeWithText(composeTestRule.activity.getString(R.string.send).uppercase())
            .performClick()
        assertTrue(sentOrder.contains("3") && sentOrder.contains("Chocolate"))

        composeTestRule
            .onNodeWithText(composeTestRule.activity.getString(R.string.cancel).uppercase())
            .performClick()
        assertTrue(cancelClicked)
    }
}
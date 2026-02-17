package com.example.cupcake
import com.example.cupcake.model.OrderViewModel
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.doubles.plusOrMinus
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi


private const val PRICE_PER_CUPCAKE = 2.00
private const val PRICE_FOR_SAME_DAY_PICKUP = 3.00

@OptIn(ExperimentalCoroutinesApi::class)
class OrderViewModelBehaviorTest : BehaviorSpec({

    Given("OrderViewModel с количеством 3") {
        val viewModel = OrderViewModel()
        viewModel.setQuantity(3)

        When("вызываем setFlavor с 'Chocolate'") {
            viewModel.setFlavor("Chocolate")

            Then("flavor должен обновиться, цена не должна измениться") {
                val state = viewModel.homeState.value
                state.flavor shouldBe "Chocolate"
                state.quantity shouldBe 3
                val expectedBasePrice = 3 * PRICE_PER_CUPCAKE
                val expectedPrice = if (state.date == viewModel.dateOptions[0]) {
                    expectedBasePrice + PRICE_FOR_SAME_DAY_PICKUP
                } else {
                    expectedBasePrice
                }
                state.price shouldBe (expectedPrice plusOrMinus 0.001)
            }
        }
    }

    Given("OrderViewModel") {
        val viewModel = OrderViewModel()

        When("setQuantity вызывается с 2") {
            viewModel.setQuantity(2)

            Then("quantity должно быть 2, цена пересчитана") {
                val state = viewModel.homeState.value
                state.quantity shouldBe 2
                val expectedBasePrice = 2 * PRICE_PER_CUPCAKE
                val expectedPrice = if (state.date == viewModel.dateOptions[0]) {
                    expectedBasePrice + PRICE_FOR_SAME_DAY_PICKUP
                } else {
                    expectedBasePrice
                }
                state.price shouldBe (expectedPrice plusOrMinus 0.001)
            }
        }
    }

    Given("OrderViewModel с количеством 4") {
        val viewModel = OrderViewModel()
        viewModel.setQuantity(4)

        When("setDate вызывается с завтрашней датой") {
            val futureDate = viewModel.dateOptions[1] // завтра
            viewModel.setDate(futureDate)

            Then("date обновляется, цена без надбавки") {
                val state = viewModel.homeState.value
                state.date shouldBe futureDate
                state.price shouldBe (4 * PRICE_PER_CUPCAKE plusOrMinus 0.001)
            }
        }

        When("setDate вызывается с сегодняшней датой") {
            val today = viewModel.dateOptions[0]
            viewModel.setDate(today)

            Then("цена включает надбавку за сегодня") {
                val state = viewModel.homeState.value
                state.date shouldBe today
                state.price shouldBe (4 * PRICE_PER_CUPCAKE + PRICE_FOR_SAME_DAY_PICKUP plusOrMinus 0.001)
            }
        }
    }

    Given("OrderViewModel с измененными значениями") {
        val viewModel = OrderViewModel()
        viewModel.setQuantity(5)
        viewModel.setFlavor("Red Velvet")
        viewModel.setDate(viewModel.dateOptions[2])

        When("resetOrder вызывается") {
            viewModel.resetOrder()

            Then("все значения сбрасываются до начальных") {
                val state = viewModel.homeState.value
                state.quantity shouldBe 0
                state.flavor shouldBe "Vanilla"
                state.date shouldBe viewModel.dateOptions[0]
                state.price shouldBe 0.0
            }
        }
    }

    Given("OrderViewModel для тестирования цены") {
        val viewModel = OrderViewModel()

        When("количество = 3 и дата сегодня") {
            viewModel.setQuantity(3)

            Then("цена = 3*2 + 3 = 9") {
                viewModel.homeState.value.price shouldBe (9.0 plusOrMinus 0.001)
            }
        }

        When("количество = 3 и дата завтра") {
            viewModel.setQuantity(3)
            viewModel.setDate(viewModel.dateOptions[1])

            Then("цена = 3*2 = 6") {
                viewModel.homeState.value.price shouldBe (6.0 plusOrMinus 0.001)
            }
        }
    }

    Given("Последовательные операции") {
        val viewModel = OrderViewModel()

        When("устанавливаем количество 2, потом меняем дату на завтра, потом меняем вкус") {
            viewModel.setQuantity(2)
            viewModel.setDate(viewModel.dateOptions[1])
            viewModel.setFlavor("Strawberry")

            Then("все значения должны быть корректными") {
                val state = viewModel.homeState.value
                state.quantity shouldBe 2
                state.flavor shouldBe "Strawberry"
                state.date shouldBe viewModel.dateOptions[1]
                state.price shouldBe (2 * PRICE_PER_CUPCAKE plusOrMinus 0.001)
            }
        }
    }
})
package ru.effectivemobile.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.createGraph
import androidx.navigation.fragment.fragment
import ru.effectivemobile.ui.cart.CartFragment
import ru.effectivemobile.ui.explorer.ExplorerFragment
import ru.effectivemobile.ui.smartphonedetails.SmartphoneDetailsFragment

object Routes  {
    const val Explorer = "explorer"
    const val Cart = "bag"
    const val Details = "details"
}



private val MainNavigationGraph: NavGraphBuilder.()->Unit = {

    fragment<ExplorerFragment>(Routes.Explorer) {

    }

    fragment<SmartphoneDetailsFragment>(Routes.Details) {

    }

    fragment<CartFragment>(Routes.Cart) {

    }

}

fun NavController.setMainNavigationGraph() {
    graph = createGraph(startDestination = Routes.Explorer, builder = MainNavigationGraph)
}
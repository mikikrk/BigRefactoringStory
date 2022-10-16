//package big.refactoring.story
//
//interface AddItemToOrder {
//
//    fun execute(
//        itemId: String,
//        onError: (errorMessage: String) -> Unit,
//        onSuccess: (updatedOrder: ObserveOrder.Order) -> Unit,
//    )
//}
//
//interface LegacyHugeRouting {
//
//    fun showErrorPopup(message: String)
//    fun goToItem(itemId: String)
//}
//
//class LegacyHugeViewModel(
//    private val routing: LegacyHugeRouting,
//    private val addItemToOrder: AddItemToOrder,
//    // many many more
//) {
//    private var order: ObserveOrder.Order? = null
//
//    fun onItemClicked(itemId: String) {
//        addItemToOrder.execute(
//            itemId = itemId,
//            onError = { errorMessage -> routing.showErrorPopup(errorMessage) },
//            onSuccess = { updatedOrder ->
//                order = updatedOrder
//                recalculateViewState(updatedOrder)
//                routing.goToItem(itemId)
//            }
//        )
//    }
//
//    private fun recalculateViewState(updatedOrder: ObserveOrder.Order) {
//        // Old way of updating ViewState
//    }
//}

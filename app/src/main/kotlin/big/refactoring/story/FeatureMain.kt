package big.refactoring.story

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.Collections.emptyList

interface FeatureMainRouting {
    fun goBack()
}

abstract class FeatureMainViewModel : ViewModel(),
    FeatureToolbarViewModelDelegate,
    FeatureItemsViewModelDelegate {

    abstract fun onBackPressed()
}

class FeatureMainViewModelImpl(
    private val mainRouting: FeatureMainRouting,
    private val toolbarViewModelDelegate: FeatureToolbarViewModelDelegateImpl,
    private val itemsViewModelDelegate: FeatureItemsViewModelDelegateImpl,
    private val observeOrder: ObserveOrder
) : FeatureMainViewModel(),
    FeatureToolbarViewModelDelegate by toolbarViewModelDelegate,
    FeatureToolbarCommunication,
    FeatureItemsViewModelDelegate by itemsViewModelDelegate,
    FeatureItemsCommunication {

    private val sharedState = MutableLiveData(SharedState())

    data class SharedState(
        val selectedItems: List<String> = emptyList(),
        val selectedGuests: List<String> = emptyList(),
        val order: ObserveOrder.Order? = null,
    )

    init {
        initDelegates()
        loadInputData()
    }

    private fun initDelegates() {
        toolbarViewModelDelegate.init(sharedState = sharedState, communication = this)
        itemsViewModelDelegate.init(sharedState = sharedState, communication = this)
    }

    private fun loadInputData() {
        observeOrder.execute(
            onNextOrder = { newOrder ->
                sharedState.value = sharedState.value?.copy(
                    order = newOrder,
                )
            }
        )
    }

    override fun onBackPressed() {
        mainRouting.goBack()
    }

    override fun onSelectedItemChanged(itemIds: List<String>) {
        sharedState.value = sharedState.value?.copy(
            selectedItems = itemIds,
            selectedGuests = emptyList(),
        )
    }

    override fun onSelectedGuestsChanged(guestIds: List<String>) {
        sharedState.value = sharedState.value?.copy(
            selectedItems = emptyList(),
            selectedGuests = guestIds,
        )
    }
}

interface ObserveOrder {

    fun execute(
        onNextOrder: (Order) -> Unit
    )

    data class Order(
        val id: String,
    )
}


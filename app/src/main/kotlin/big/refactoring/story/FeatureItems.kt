package big.refactoring.story

import androidx.lifecycle.LiveData
import androidx.lifecycle.map

interface FeatureItemsRouting {
    fun goToItemDetails()
}

interface FeatureItemsCommunication {

    fun onSelectedItemChanged(itemIds: List<String>)
    fun onSelectedGuestsChanged(guestIds: List<String>)
}

interface FeatureItemsViewModelDelegate {

    val itemsViewState: LiveData<ViewState>

    fun onItemClicked()

    data class ViewState(
        val items: List<String>,
    )
}

class FeatureItemsViewModelDelegateImpl(
    private val routing: FeatureItemsRouting,
    stateMapper: (FeatureMainViewModelImpl.SharedState) -> FeatureItemsViewModelDelegate.ViewState,
) : FeatureItemsViewModelDelegate {

    private lateinit var communication: FeatureItemsCommunication
    private lateinit var sharedState: LiveData<FeatureMainViewModelImpl.SharedState>
    override val itemsViewState: LiveData<FeatureItemsViewModelDelegate.ViewState> =
        sharedState.map(stateMapper)

    fun init(
        sharedState: LiveData<FeatureMainViewModelImpl.SharedState>,
        communication: FeatureItemsCommunication,
    ) {
        this.sharedState = sharedState
        this.communication = communication
    }

    override fun onItemClicked() {
        routing.goToItemDetails()
    }
}




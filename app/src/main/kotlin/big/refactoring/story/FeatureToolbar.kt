package big.refactoring.story

import androidx.lifecycle.LiveData
import androidx.lifecycle.map


interface FeatureToolbarRouting {
    fun goBack()
}

interface FeatureToolbarCommunication {

    fun onSelectedItemChanged(itemIds: List<String>)
}

interface FeatureToolbarViewModelDelegate {

    val toolbarViewState: LiveData<ViewState>

    fun onUpClicked()

    data class ViewState(
        val title: String,
    )
}

class FeatureToolbarViewModelDelegateImpl(
    private val routing: FeatureToolbarRouting,
    stateMapper: (FeatureMainViewModelImpl.SharedState) -> FeatureToolbarViewModelDelegate.ViewState,
) : FeatureToolbarViewModelDelegate {

    private lateinit var communication: FeatureToolbarCommunication
    private lateinit var sharedState: LiveData<FeatureMainViewModelImpl.SharedState>
    override val toolbarViewState: LiveData<FeatureToolbarViewModelDelegate.ViewState> =
        sharedState.map(stateMapper)

    fun init(
        sharedState: LiveData<FeatureMainViewModelImpl.SharedState>,
        communication: FeatureToolbarCommunication,
    ) {
        this.sharedState = sharedState
        this.communication = communication
    }

    override fun onUpClicked() {
        routing.goBack()
    }
}




package site.yoonsang.findkindergarten.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import site.yoonsang.findkindergarten.model.KindergartenLocationResponse
import site.yoonsang.findkindergarten.repository.LocationRepository
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: LocationRepository) :
    ViewModel() {

    val query = MutableLiveData<String>()

    fun getKindergartenLocation(): MutableLiveData<KindergartenLocationResponse> {
        val kindergartenLocation = MutableLiveData<KindergartenLocationResponse>()
        viewModelScope.launch {
            kindergartenLocation.value = repository.getKindergartenLocation(query.value!!)
        }
        return kindergartenLocation
    }
}
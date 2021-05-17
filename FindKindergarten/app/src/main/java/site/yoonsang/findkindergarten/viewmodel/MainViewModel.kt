package site.yoonsang.findkindergarten.viewmodel

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import site.yoonsang.findkindergarten.model.KindergartenInfo
import site.yoonsang.findkindergarten.repository.KindergartenRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: KindergartenRepository) :
    ViewModel() {

    val sidoCode = MutableLiveData<Int>()
    val sggCode = MutableLiveData<Int>()

    fun getKindergartenInfo(sido: Int, sgg: Int): LiveData<PagingData<KindergartenInfo>> {
        sidoCode.value = sido
        sggCode.value = sgg
        return repository.getKindergartenInfo(sidoCode.value!!, sggCode.value!!)
            .cachedIn(viewModelScope)
    }
}
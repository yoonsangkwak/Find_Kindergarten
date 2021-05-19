package site.yoonsang.findkindergarten.viewmodel

import androidx.lifecycle.*
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import site.yoonsang.findkindergarten.repository.KindergartenRepository
import site.yoonsang.findkindergarten.util.DoubleTrigger
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: KindergartenRepository) :
    ViewModel() {

    private val sidoCode = MutableLiveData<Int>()
    private val sggCode = MutableLiveData<Int>()

    init {
        sidoCode.value = 0
        sggCode.value = 0
    }

    val kindergartenInfo = Transformations.switchMap(DoubleTrigger(sidoCode, sggCode)) { value ->
        repository.getKindergartenInfo(value.first!!, value.second!!).cachedIn(viewModelScope)
    }

    fun getKindergartenInfo(sido: Int, sgg: Int) {
        sidoCode.postValue(sido)
        sggCode.postValue(sgg)
    }
}
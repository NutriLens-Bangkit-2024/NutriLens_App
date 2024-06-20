package com.capstone.nutrilens.ui.changeprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.nutrilens.data.response.EditUserRequest
import com.capstone.nutrilens.data.response.EditUserResponse
import com.capstone.nutrilens.data.response.UserResponse
import com.capstone.nutrilens.data.util.NetworkResult
import com.capstone.nutrilens.ui.profile.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChangeProfileViewModel(private val repository: ChangeProfileRepository) : ViewModel() {

    private val _editUserResult = MutableLiveData<NetworkResult<EditUserResponse>>()
    val editUserResult: LiveData<NetworkResult<EditUserResponse>> get() = _editUserResult

    fun editUser(authorization: String, id: String, editUserRequest: EditUserRequest) {
        viewModelScope.launch {
            try {
                val response = repository.editUser(authorization, id, editUserRequest)
                if (response.isSuccessful && response.body() != null) {
                    _editUserResult.value = NetworkResult.Success(response.body()!!)
                } else {
                    _editUserResult.value = NetworkResult.Error("Failed to update user")
                }
            } catch (e: Exception) {
                _editUserResult.value = NetworkResult.Error(e.message ?: "An error occurred")
            }
        }
    }
}
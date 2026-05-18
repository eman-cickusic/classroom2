package com.classroom2.app.data.repository

import com.classroom2.app.data.remote.InMemoryStore
import com.classroom2.app.domain.model.User
import com.classroom2.app.domain.model.UserRole
import com.classroom2.app.util.DemoData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {
    val role: StateFlow<UserRole?>
    val currentUser: StateFlow<User>
    fun selectRole(role: UserRole)
    fun professor(): User
    fun student(): User
}

class LocalAuthRepository : AuthRepository {
    private val _role = MutableStateFlow<UserRole?>(null)
    override val role: StateFlow<UserRole?> = _role

    private val _currentUser = MutableStateFlow(DemoData.student)
    override val currentUser: StateFlow<User> = _currentUser

    override fun selectRole(role: UserRole) {
        _role.value = role
        _currentUser.value = when (role) {
            UserRole.PROFESSOR -> DemoData.professor
            UserRole.STUDENT -> InMemoryStore.currentStudent.value
        }
    }

    override fun professor(): User = DemoData.professor
    override fun student(): User = InMemoryStore.currentStudent.value
}

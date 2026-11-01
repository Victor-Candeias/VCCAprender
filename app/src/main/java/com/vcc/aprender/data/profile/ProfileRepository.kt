package com.vcc.aprender.data.profile

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProfileRepository(
    private val dao: ProfileDao,
    private val preferences: DataStore<Preferences>
) {
    private val selectedKey = longPreferencesKey("selected_child_profile_id")
    val profiles: Flow<List<ChildProfile>> = dao.observeAll()
    val selectedId: Flow<Long?> = preferences.data.map { it[selectedKey] }

    suspend fun save(id: Long, name: String, age: Int, avatar: String): Long {
        val cleanName = name.trim()
        require(cleanName.length in 1..40) { "O nome deve ter entre 1 e 40 caracteres." }
        require(age in 3..8) { "A idade deve estar entre 3 e 8 anos." }
        if (id != 0L) require(dao.find(id) != null) { "Perfil não encontrado." }
        return dao.save(ChildProfile(id, cleanName, age, avatar))
    }

    suspend fun select(id: Long) {
        require(dao.find(id) != null) { "Perfil não encontrado." }
        preferences.edit { it[selectedKey] = id }
    }

    /** Deletes the child and all related progress/history through Room CASCADE. */
    suspend fun delete(profile: ChildProfile) {
        dao.delete(profile)
        preferences.edit { if (it[selectedKey] == profile.id) it.remove(selectedKey) }
    }
}

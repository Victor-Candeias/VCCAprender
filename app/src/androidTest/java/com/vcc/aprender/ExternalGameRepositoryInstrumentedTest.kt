package com.vcc.aprender

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vcc.aprender.data.plugins.ExternalGameRepository
import com.vcc.aprender.data.profile.ChildProfile
import com.vcc.aprender.data.profile.ProfileDatabase
import com.vcc.aprender.pluginsdk.GameProtocol
import com.vcc.aprender.pluginsdk.GameResult
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExternalGameRepositoryInstrumentedTest {
    @Test fun storesOnceAndKeepsResultsIsolatedByProfile() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = Room.inMemoryDatabaseBuilder(context, ProfileDatabase::class.java).build()
        try {
            val id1 = db.profileDao().save(ChildProfile(name = "A", age = 5))
            val id2 = db.profileDao().save(ChildProfile(name = "B", age = 6))
            val repository = ExternalGameRepository(db)
            val request = GameProtocol.newRequest("com.vcc.aprender.sample.quiz", id1)
            val result = GameResult(request, 1, 1)

            assertTrue(repository.record(result))
            assertFalse("O mesmo token não deve contar duas vezes", repository.record(result))
            assertEquals(1, repository.observeByProfile(id1).first().size)
            assertEquals(0, repository.observeByProfile(id2).first().size)
            assertEquals("com.vcc.aprender.sample.quiz", repository.observeByProfile(id1).first()[0].gameId)
        } finally {
            db.close()
        }
    }
}

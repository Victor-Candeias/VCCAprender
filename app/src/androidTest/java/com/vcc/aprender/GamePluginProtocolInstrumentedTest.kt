package com.vcc.aprender

import android.content.ComponentName
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vcc.aprender.pluginsdk.GameContract
import com.vcc.aprender.pluginsdk.GameProtocol
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GamePluginProtocolInstrumentedTest {
    @Test fun intentRoundTripAndTamperedResultRejection() {
        val request = GameProtocol.newRequest("com.vcc.aprender.sample.quiz", 123L)
        val component = ComponentName(
            "com.vcc.aprender.samplegame",
            "com.vcc.aprender.samplegame.SampleGameActivity"
        )
        val intent = GameProtocol.createLaunchIntent(component, request)
        assertEquals(component, intent.component)
        assertEquals(request, GameProtocol.readRequest(intent))

        val result = GameProtocol.createResultIntent(request, correct = 1, total = 2)
        assertEquals(1, GameProtocol.readResult(result, request)?.correctAnswers)
        assertEquals(2, GameProtocol.readResult(result, request)?.totalQuestions)

        result.putExtra(GameContract.EXTRA_SESSION_ID, "wrong-nonce")
        assertNull(GameProtocol.readResult(result, request))
    }

    @Test fun invalidVersionProfileAndCountCannotBeAccepted() {
        val request = GameProtocol.newRequest("com.vcc.aprender.sample.quiz", 1L)
        val result = GameProtocol.createResultIntent(request, correct = 1, total = 1)
        result.putExtra(GameContract.EXTRA_PROTOCOL_VERSION, 2)
        assertNull(GameProtocol.readResult(result, request))
        result.putExtra(GameContract.EXTRA_PROTOCOL_VERSION, GameContract.PROTOCOL_VERSION)
        result.putExtra(GameContract.EXTRA_PROFILE_ID, 2L)
        assertNull(GameProtocol.readResult(result, request))
        result.putExtra(GameContract.EXTRA_PROFILE_ID, request.profileId)
        result.putExtra(GameContract.EXTRA_CORRECT_ANSWERS, 99)
        assertNull(GameProtocol.readResult(result, request))
    }
}

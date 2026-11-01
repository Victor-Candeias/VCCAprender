package com.vcc.aprender

import androidx.compose.ui.test.assertExists
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vcc.aprender.domain.model.LearningTopic
import com.vcc.aprender.ui.components.TopicIllustration
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TopicIllustrationUiTest {
    @get:Rule val composeRule = createComposeRule()

    @Test fun allTopicIllustrationsExposeAccessibleDescriptions() {
        for (topic in LearningTopic.entries) {
            composeRule.setContent { TopicIllustration(topic) }
            composeRule.onNodeWithContentDescription("Ilustração de ${topic.title}").assertExists()
        }
    }
}

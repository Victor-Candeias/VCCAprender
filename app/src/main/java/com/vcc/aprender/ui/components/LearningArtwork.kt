package com.vcc.aprender.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import com.vcc.aprender.R
import com.vcc.aprender.domain.model.LearningIllustration
import com.vcc.aprender.domain.model.LearningPicture

/** Displays each prompt's actual picture; counted stars remain separate and easy to count. */
@Composable
fun LearningArtwork(value: String, modifier: Modifier = Modifier, decorative: Boolean = false) {
    val picture = LearningPicture.from(value)
    if (picture == null) {
        // Preserve text illustrations on user-created cards instead of replacing their meaning.
        val fallbackModifier = if (decorative) modifier.clearAndSetSemantics { } else modifier
        Text(value, modifier = fallbackModifier, style = MaterialTheme.typography.displayMedium)
        return
    }
    val semantics = Modifier.clearAndSetSemantics {
        if (!decorative) contentDescription = picture.description
    }
    if (picture.count == 1) {
        Image(painterResource(picture.artwork.drawable()), contentDescription = null,
            modifier = modifier.size(160.dp).then(semantics))
    } else {
        Column(modifier = modifier.then(semantics), verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally) {
            (0 until picture.count).chunked(5).forEach { row ->
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    row.forEach {
                        Image(painterResource(R.drawable.learn_star), contentDescription = null,
                            modifier = Modifier.size(40.dp))
                    }
                }
            }
        }
    }
}

@DrawableRes
internal fun LearningIllustration.drawable(): Int = when (this) {
    LearningIllustration.STAR -> R.drawable.learn_star
    LearningIllustration.RED -> R.drawable.learn_red
    LearningIllustration.BLUE -> R.drawable.learn_blue
    LearningIllustration.GREEN -> R.drawable.learn_green
    LearningIllustration.YELLOW -> R.drawable.learn_yellow
    LearningIllustration.ORANGE -> R.drawable.learn_orange
    LearningIllustration.PURPLE -> R.drawable.learn_purple
    LearningIllustration.CIRCLE -> R.drawable.learn_circle
    LearningIllustration.SQUARE -> R.drawable.learn_square
    LearningIllustration.TRIANGLE -> R.drawable.learn_triangle
    LearningIllustration.RECTANGLE -> R.drawable.learn_rectangle
    LearningIllustration.HEART -> R.drawable.learn_heart
    LearningIllustration.CAT -> R.drawable.learn_cat
    LearningIllustration.DOG -> R.drawable.learn_dog
    LearningIllustration.COW -> R.drawable.learn_cow
    LearningIllustration.LION -> R.drawable.learn_lion
    LearningIllustration.ELEPHANT -> R.drawable.learn_elephant
    LearningIllustration.BUTTERFLY -> R.drawable.learn_butterfly
    LearningIllustration.FISH -> R.drawable.learn_fish
    LearningIllustration.RABBIT -> R.drawable.learn_rabbit
    LearningIllustration.SHEEP -> R.drawable.learn_sheep
    LearningIllustration.DUCK -> R.drawable.learn_duck
    LearningIllustration.ROOSTER -> R.drawable.learn_rooster
    LearningIllustration.MOUSE -> R.drawable.learn_mouse
    LearningIllustration.FROG -> R.drawable.learn_frog
    LearningIllustration.BEE -> R.drawable.learn_bee
    LearningIllustration.TURTLE -> R.drawable.learn_turtle
    LearningIllustration.ZEBRA -> R.drawable.learn_zebra
    LearningIllustration.HOUSE -> R.drawable.learn_house
    LearningIllustration.BALL -> R.drawable.learn_ball
    LearningIllustration.BOOK -> R.drawable.learn_book
    LearningIllustration.APPLE -> R.drawable.learn_apple
    LearningIllustration.CAR -> R.drawable.learn_car
    LearningIllustration.SUN -> R.drawable.learn_sun
    LearningIllustration.FLOWER -> R.drawable.learn_flower
    LearningIllustration.KEY -> R.drawable.learn_key
    LearningIllustration.DIE -> R.drawable.learn_die
    LearningIllustration.FINGER -> R.drawable.learn_finger
    LearningIllustration.HAND -> R.drawable.learn_hand
    LearningIllustration.BREAD -> R.drawable.learn_bread
    LearningIllustration.MOON -> R.drawable.learn_moon
    LearningIllustration.ROAD -> R.drawable.learn_road
    LearningIllustration.GLOVE -> R.drawable.learn_glove
    LearningIllustration.BAG -> R.drawable.learn_bag
    LearningIllustration.MILK -> R.drawable.learn_milk
    LearningIllustration.TOOTH -> R.drawable.learn_tooth
    LearningIllustration.TABLE -> R.drawable.learn_table
    LearningIllustration.SUITCASE -> R.drawable.learn_suitcase
    LearningIllustration.CAKE -> R.drawable.learn_cake
    LearningIllustration.HELICOPTER -> R.drawable.learn_helicopter
    LearningIllustration.ISLAND -> R.drawable.learn_island
    LearningIllustration.WINDOW -> R.drawable.learn_window
    LearningIllustration.KIWI -> R.drawable.learn_kiwi
    LearningIllustration.SHIP -> R.drawable.learn_ship
    LearningIllustration.CHEESE -> R.drawable.learn_cheese
    LearningIllustration.GRAPES -> R.drawable.learn_grapes
    LearningIllustration.WAFFLE -> R.drawable.learn_waffle
    LearningIllustration.XYLOPHONE -> R.drawable.learn_xylophone
    LearningIllustration.YOGA -> R.drawable.learn_yoga
    LearningIllustration.LETTERS -> R.drawable.learn_letters
    LearningIllustration.SOUND -> R.drawable.learn_sound
}

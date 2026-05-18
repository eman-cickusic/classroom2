package com.classroom2.app.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

object ClassroomShapes {
    val Button = RoundedCornerShape(18.dp)
    val Chip = RoundedCornerShape(50)
    val Card = RoundedCornerShape(22.dp)
    val LargeCard = RoundedCornerShape(28.dp)
    val Hero = RoundedCornerShape(32.dp)
    val Qr = RoundedCornerShape(32.dp)
}

val ClassroomMaterialShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(14.dp),
    medium = RoundedCornerShape(20.dp),
    large = RoundedCornerShape(28.dp),
    extraLarge = RoundedCornerShape(32.dp)
)

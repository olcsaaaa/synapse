package com.olidev.synapse_mobile.ui.theme


import androidx.compose.material3.Typography
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.olidev.synapse_mobile.R


@OptIn(ExperimentalTextApi::class)
val RobotoFlex = FontFamily(
    Font(
        resId = R.font.roboto_flex,
        variationSettings = FontVariation.Settings(
            FontVariation.weight(400),
            FontVariation.width(200f),

            )
    )
)

@OptIn(ExperimentalTextApi::class)
val RobotoFlexWide = FontFamily(
    Font(
        resId = R.font.roboto_flex,
        variationSettings = FontVariation.Settings(
            FontVariation.weight(950),
            FontVariation.width(130f),

            )
    )
)


@OptIn(ExperimentalTextApi::class)
val Typography = Typography(
    displayMedium = Typography().displayMedium.copy(
        fontFamily = RobotoFlex,
        fontWeight = FontWeight(950),
        letterSpacing = (-1.5).sp,
        lineHeight = 52.sp,
    ),
    headlineLarge = Typography().headlineLarge.copy(
        fontFamily = RobotoFlex,
        fontWeight = FontWeight.W800,
        letterSpacing = (-0.5).sp
    ),
    bodyLarge = Typography().bodyLarge.copy(
        fontFamily = RobotoFlex,
        fontWeight = FontWeight(450),
        lineHeight = 24.sp
    ),
    displaySmall = Typography().displaySmall.copy(
        fontFamily = RobotoFlexWide,
        fontSize = 36.sp,
        letterSpacing = (1.5).sp
    ),
    headlineSmall = Typography().headlineSmall.copy(
        fontFamily = FontFamily(
            Font(
                resId = R.font.roboto_flex,
                variationSettings = FontVariation.Settings(
                    FontVariation.weight(400),
                    FontVariation.width(200f),
                )
            ),
        ),
        fontSize = 24.sp
    ),
    titleMedium = Typography().titleMedium.copy(fontFamily = RobotoFlex),
    labelLarge = Typography().labelLarge.copy(fontFamily = RobotoFlex),
)

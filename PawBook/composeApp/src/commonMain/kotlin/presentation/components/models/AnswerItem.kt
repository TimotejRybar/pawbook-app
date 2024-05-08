package presentation.components.models

import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource

data class AnswerItem @OptIn(ExperimentalResourceApi::class) constructor(var id: String, var stringResource: StringResource)
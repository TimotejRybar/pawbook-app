package data.local

import com.russhwolf.settings.Settings
import domain.model.result.Tokens
import domain.model.result.User
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class Preferences() : KoinComponent {
    private val settings: Settings by inject()

    fun saveUser(user: User, tokens: Tokens? = null
    ) {
        if(tokens != null) {
            settings.putString("access_token", tokens.access.token)
            settings.putString("access_token_expires", tokens.access.expires.toString())
            settings.putString("refresh_token", tokens.access.token)
            settings.putString("refresh_token_expires", tokens.access.expires.toString())
        }
        settings.putString("user", Json.encodeToString(user))
    }

    fun getUser(): User {
        return Json.decodeFromString(settings.getString("user", ""))
    }

    fun getAccessToken(): String {
        return settings.getString("access_token", "")
    }
}
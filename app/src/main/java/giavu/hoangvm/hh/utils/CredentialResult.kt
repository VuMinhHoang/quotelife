package giavu.hoangvm.hh.utils

import android.content.Intent
import androidx.annotation.Nullable
import com.google.android.gms.auth.api.credentials.Credential

class CredentialResult private constructor(@param:Nullable @field:Nullable
                                           @get:Nullable
                                           internal val credential: Credential?) {

    val id: String
        get() = credential?.id ?: ""

    val password: String?
        get() = if (credential == null) {
            ""
        } else credential.password

    companion object {

        internal fun fromActivityResult(data: Intent): CredentialResult {
            return CredentialResult(data.getParcelableExtra(Credential.EXTRA_KEY))
        }

        internal fun fromCredential(credential: Credential): CredentialResult {
            return CredentialResult(credential)
        }

        internal fun empty(): CredentialResult {
            return CredentialResult(null)
        }
    }
}

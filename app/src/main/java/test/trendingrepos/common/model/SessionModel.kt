package test.trendingrepos.common.model

import android.os.Bundle
import test.trendingrepos.OpenForTesting

/**
 * Model which keeps session data using Android bundle serialization
 *
 * Created on 23/02/2018
 * @author sdelaysam
 */

@OpenForTesting
class SessionModel {

    private val sessionKey = "session"

    private lateinit var bundle: Bundle

    fun putLong(key: String, value: Long) {
        bundle.putLong(key, value)
    }

    fun getLong(key: String) = if (bundle.containsKey(key)) bundle.getLong(key) else null

    fun remove(key: String) = bundle.remove(key)

    fun clear() = bundle.clear()

    fun save(outState: Bundle?) {
        outState?.putBundle(sessionKey, bundle)
    }

    fun restore(saveState: Bundle?) {
        bundle = saveState?.getBundle(sessionKey) ?: Bundle()
    }
}
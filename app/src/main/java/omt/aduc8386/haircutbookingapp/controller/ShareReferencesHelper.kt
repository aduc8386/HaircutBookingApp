package omt.aduc8386.haircutbookingapp.controller

import android.content.Context
import android.content.SharedPreferences

class ShareReferencesHelper {

    companion object {
        private const val SHARE_REFERENCES: String = "SHARE_REFERENCES"
        private const val IS_LOGGED_IN: String = "IS_LOGGED_IN"
        private const val USER_ID: String = "USER_ID"

        private var instance: SharedPreferences? = null

        fun init(context: Context) {
            instance = context.getSharedPreferences(SHARE_REFERENCES, Context.MODE_PRIVATE)
        }

        fun setIsLoggedIn(isLoggedIn: Boolean) {
            instance?.edit()?.putBoolean(IS_LOGGED_IN, isLoggedIn)?.apply()
        }

        fun getIsLoggedIn(): Boolean {
            return instance?.getBoolean(IS_LOGGED_IN, false) ?: false
        }

        fun setUserId(userId: String) {
            instance?.edit()?.putString(USER_ID, userId)?.apply()
        }

        fun getUserId() : String{
            return instance?.getString(USER_ID, "") ?: ""
        }


    }


}
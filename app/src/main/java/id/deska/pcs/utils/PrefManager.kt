package id.deska.pcs.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import id.deska.pcs.data.response.DataAuth

private const val PREFS_NAME = "app_name"

class PrefManager(context: Context) {

    private val sp: SharedPreferences by lazy {
        context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    val spe: SharedPreferences.Editor by lazy {
        sp.edit()
    }

    fun clearAllPref() {
        sp.edit().remove("saveAuthData").apply()
        sp.edit().remove("spTotalPrice").apply()
    }

    fun saveAuthData(list: DataAuth) {
        val gson = Gson()
        val json: String = gson.toJson(list)
        spe.putString("saveAuthData", json)
        spe.apply()
    }

    fun getAuthData(): DataAuth? {
        val gson = Gson()
        val json: String? = sp.getString("saveAuthData", "")
        return gson.fromJson(json, DataAuth::class.java)
    }

    var spTotalPrice: Int
        get() = sp.getInt("spTotalPrice", 0)
        set(value) {
            spe.putInt("spTotalPrice", value)
            spe.apply()
        }

//    var spAuthPhone: Boolean
//        get() = sp.getBoolean(PREF_AUTH_PHONE, false)
//        set(value) {
//            spe.putBoolean(PREF_AUTH_PHONE, value)
//            spe.apply()
//        }
}

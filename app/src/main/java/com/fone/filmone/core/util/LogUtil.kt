package com.fone.filmone.core.util

import android.util.Log
import com.fone.filmone.BuildConfig

object LogUtil {
    private const val TAG = "LogUtil"
    private val DEBUG_MODE = (BuildConfig.DEBUG)

    @JvmStatic
    fun v(msg: String) {
        if (DEBUG_MODE) {
            Log.v(TAG, buildLogMsg(msg))
        }
    }

    @JvmStatic
    fun d(msg: String) {
        if (DEBUG_MODE) {
            Log.d(TAG, buildLogMsg(msg))
        }
    }

    @JvmStatic
    fun i(msg: String) {
        if (DEBUG_MODE) {
            Log.i(TAG, buildLogMsg(msg))
        }
    }

    @JvmStatic
    fun w(msg: String) {
        if (DEBUG_MODE) {
            Log.w(TAG, buildLogMsg(msg))
        }
    }

    @JvmStatic
    fun e(msg: String) {
        if (DEBUG_MODE) {
            Log.e(TAG, buildLogMsg(msg))
        }
    }

    private fun buildLogMsg(message: String): String {
        val ste = Thread.currentThread().stackTrace[4]

        return StringBuilder().append("[")
            .append(ste.fileName.replace(".java", ""))
            .append("::")
            .append(ste.methodName)
            .append("] ")
            .append(message)
            .toString()
    }
}
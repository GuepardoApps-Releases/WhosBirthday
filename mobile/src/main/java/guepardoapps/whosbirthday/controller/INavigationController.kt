package guepardoapps.whosbirthday.controller

import android.os.Bundle

interface INavigationController {
    fun navigate(activity: Class<*>, finish: Boolean)
    fun navigateWithData(activity: Class<*>, data: Bundle, finish: Boolean)
    fun navigateToOtherApp(packageName: String, finish: Boolean): Boolean
}
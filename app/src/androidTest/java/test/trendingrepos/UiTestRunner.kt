package test.trendingrepos

import android.app.Application
import android.content.Context
import android.support.test.runner.AndroidJUnitRunner
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import android.support.test.runner.lifecycle.Stage
import android.view.WindowManager

/**
 * Created on 22/02/2018
 * @author sdelaysam
 */

class UiTestRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
        return super.newApplication(cl, UiTestApp::class.java.canonicalName, context)
    }

    override fun onCreate(arguments: android.os.Bundle?) {
        super.onCreate(arguments)
        ActivityLifecycleMonitorRegistry.getInstance().addLifecycleCallback { activity, stage ->
            when (stage) {
                Stage.PRE_ON_CREATE -> activity.window.addFlags(
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                                or WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                                or WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                else -> {}
            }
        }
    }


}
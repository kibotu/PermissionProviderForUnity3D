package net.kibotu.unity.android.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import com.unity3d.player.UnityPlayer;
import net.kibotu.unity.android.LogExtensions;

/**
 * Created by Jan Rabe on 25/11/15.
 */
public class UnityActivityLifeCycleCallbacks implements Application.ActivityLifecycleCallbacks {

    final String gameObject;

    public UnityActivityLifeCycleCallbacks(final String gameObject) {
        this.gameObject = gameObject;
    }

    public void init() {
        UnityPlayer.currentActivity.getApplication().registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void onActivityCreated(final Activity activity, final Bundle savedInstanceState) {
        LogExtensions.i(activity.getClass().getCanonicalName(), "[onActivityCreated]");
        UnityPlayer.UnitySendMessage(gameObject, "onActivityCreated", "");
    }

    @Override
    public void onActivityStarted(final Activity activity) {
        LogExtensions.i(activity.getClass().getCanonicalName(), "[onActivityStarted]");
        UnityPlayer.UnitySendMessage(gameObject, "onActivityStarted", "");
    }

    @Override
    public void onActivityResumed(final Activity activity) {
        LogExtensions.i(activity.getClass().getCanonicalName(), "[onActivityResumed]");
        UnityPlayer.UnitySendMessage(gameObject, "onActivityResumed", "");
    }

    @Override
    public void onActivityPaused(final Activity activity) {
        LogExtensions.i(activity.getClass().getCanonicalName(), "[onActivityPaused]");
        UnityPlayer.UnitySendMessage(gameObject, "onActivityPaused", "");
    }

    @Override
    public void onActivityStopped(final Activity activity) {
        LogExtensions.i(activity.getClass().getCanonicalName(), "[onActivityStopped]");
        UnityPlayer.UnitySendMessage(gameObject, "onActivityStopped", "");
    }

    @Override
    public void onActivitySaveInstanceState(final Activity activity, final Bundle outState) {
        LogExtensions.i(activity.getClass().getCanonicalName(), "[onActivitySaveInstanceState]");
        UnityPlayer.UnitySendMessage(gameObject, "onActivitySaveInstanceState", "");
    }

    @Override
    public void onActivityDestroyed(final Activity activity) {
        LogExtensions.i(activity.getClass().getCanonicalName(), "[onActivityDestroyed]");
        UnityPlayer.UnitySendMessage(gameObject, "onActivityDestroyed", "");
    }
}

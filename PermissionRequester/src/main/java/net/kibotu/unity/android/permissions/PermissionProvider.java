package net.kibotu.unity.android.permissions;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.unity3d.player.UnityPlayer;
import net.kibotu.unity.android.LogExtensions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Jan Rabe on 03/12/15.
 */
public class PermissionProvider extends Fragment {

    static final String TAG = PermissionProvider.class.getSimpleName();

    static final String GAME_OBJECT = "GameObject";
    static final String REQUEST_CODE = "RequestCode";
    static final String PERMISSIONS = "Permissions";

    String gameObject;
    int requestCode;
    ArrayList<String> requestedPermissions = new ArrayList<>();
    boolean askedPermission;

    public PermissionProvider() {
    }

    public static void verifyPermissions(final String gameObject, final int requestCode, final String[] permissions) {
        LogExtensions.v(TAG, "[verifyPermissions] GameObject: " + gameObject + " RequestCode: " + requestCode + " Permissions: " + Arrays.toString(permissions));

        UnityPlayer.currentActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final Bundle bundle = new Bundle();
                bundle.putString(GAME_OBJECT, gameObject);
                bundle.putInt(REQUEST_CODE, requestCode);
                bundle.putStringArray(PERMISSIONS, permissions);
                final PermissionProvider fragment = new PermissionProvider();
                fragment.setArguments(bundle);
                UnityPlayer.currentActivity
                        .getFragmentManager()
                        .beginTransaction()
                        .add(fragment, PermissionProvider.class.getCanonicalName())
                        .commit();
            }
        });
    }

    static void onComplete(final String gameObject, final int requestCode, final boolean hasBeenGranted) {

        LogExtensions.v(TAG, "[onComplete] GameObject: " + gameObject + " RequestCode: " + requestCode + " Granted: " + hasBeenGranted);

        UnityPlayer.UnitySendMessage(gameObject, hasBeenGranted
                ? "OnGranted"
                : "OnDenied", String.valueOf(requestCode));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        LogExtensions.v(TAG, "[onCreateView]");

        final Bundle bundle = getArguments();
        if (bundle != null) {
            gameObject = bundle.getString(GAME_OBJECT);
            final String[] permissions = bundle.getStringArray(PERMISSIONS);
            if (permissions != null)
                Collections.addAll(requestedPermissions, permissions);
            requestCode = bundle.getInt(REQUEST_CODE);

            LogExtensions.v(TAG, "[onCreateView] GameObject: " + gameObject + " RequestCode: " + requestCode + " Permissions: " + requestedPermissions);

            checkThemePermissions();
        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @SuppressLint("NewApi")
    public void checkThemePermissions() {

        LogExtensions.v(TAG, "[checkThemePermissions]");

        // Check and request for permissions for Android M and older versions
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M || askedPermission) {
            LogExtensions.v(TAG, "[checkThemePermissions] " + Build.VERSION.SDK_INT + " < " + Build.VERSION_CODES.M + " or " + askedPermission);
            onComplete(gameObject, requestCode, true);
        }

        final ArrayList<String> requiredPermissions = new ArrayList<>();

        for (final String perm : requestedPermissions) {
            if (getActivity().checkSelfPermission(perm) != PackageManager.PERMISSION_GRANTED) {
                requiredPermissions.add(perm);
            }
        }

        LogExtensions.v(TAG, "[checkThemePermissions] " + requiredPermissions);

        if (requiredPermissions.size() > 0) {
            boolean pr = false;
            for (final String p : requiredPermissions) {
                pr = shouldShowRequestPermissionRationale(p);
                if (pr) {
                    break;
                }
            }

            if (pr) {
                LogExtensions.w(TAG, "[checkThemePermissions] We've been denied once before.");
            }
            LogExtensions.w(TAG, "[checkThemePermissions] requestPermissions " + requestCode);
            this.requestPermissions(requiredPermissions.toArray(new String[requiredPermissions.size()]), requestCode);
        } else {

            LogExtensions.v(TAG, "[checkThemePermissions] no requiredPermissions");
            onComplete(gameObject, requestCode, true);
        }

        askedPermission = true;
    }

    /**
     * Once user allow or deny permissions this method is called
     */
    @Override
    public void onRequestPermissionsResult(final int requestCode, final String[] permissions, final int[] grantResults) {
        LogExtensions.v(TAG, "[onRequestPermissionsResult] " + requestCode + " | " + Arrays.toString(permissions) + " | " + Arrays.toString(grantResults));

        if (requestCode == this.requestCode) {
            boolean result = true;
            for (int i : grantResults) {
                if (i != PackageManager.PERMISSION_GRANTED) {
                    result = false;
                    break;
                }
            }

            onComplete(gameObject, requestCode, result);
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}

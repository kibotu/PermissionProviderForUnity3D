using UnityEngine;
using System.Collections;
using System;

namespace Platform.Android
{
	public class PermissionProvider : MonoBehaviour
	{

		public static event Action<bool> OnReceiveStoragePermission;

		#if UNITY_ANDROID
		
		private AndroidJavaClass _request;
		
		public  AndroidJavaClass Request { 
			get { 
				if (_request == null)
					_request = new AndroidJavaClass ("net.kibotu.unity.android.permissions.PermissionProvider");
				return _request;
			} 
		}
		
		#else
		
		public AndroidJavaClassStub Request = new AndroidJavaClassStub ();
		
		#endif

		public void VerifyStorage (Action<bool> action)
		{
			Debug.Log ("[PermissionProvider] VerifyStorage" );
			OnReceiveStoragePermission += action;
			RequestPermissions (1001, "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE");
		}

		public void RequestPermissions (int requestCode, params string[]permissions)
		{
			Debug.Log ("[PermissionProvider] Requesting permissions: "+ requestCode + " " + permissions );
			Request.CallStatic ("verifyPermissions", gameObject.name, requestCode, permissions);
		}

		public void OnGranted (string requestCode)
		{
			Debug.Log("[PermissionProvider] OnGranted " + requestCode);
			if (requestCode.Equals ("1001"))
			if (OnReceiveStoragePermission != null)
				OnReceiveStoragePermission (true);
		}

		public void OnDenied (string requestCode)
		{
			Debug.Log("[PermissionProvider] OnDenied " + requestCode);
			if (requestCode.Equals ("1001"))
				if (OnReceiveStoragePermission != null)
					OnReceiveStoragePermission (false);
		}
	}
}
using UnityEngine;
using System.Collections;
using Platform.Android;

public class Example : MonoBehaviour
{
	void Awake ()
	{
		GetComponent<PermissionProvider> ().VerifyStorage (hasBeenGranted => Debug.Log ("Storage permissions granted: " + hasBeenGranted));
	}
}

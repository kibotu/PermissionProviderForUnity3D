# Android Permission Provider Plugin for Unity3D

## Introduction

## How to use

1. Add PermissionProvider component to any GameObject in your Scene.
2. Register a callback, for convenience look at PermissionProvider.VerifyStorage method

	void Awake ()
	{
		GetComponent<PermissionProvider> ().VerifyStorage (hasBeenGranted => Debug.Log ("Storage permissions granted: " + hasBeenGranted));
	}

## How to install

### Unity

Ready to use package:  [PermissionProvider.unitypackage](PermissionProvider.unitypackage)

### Android

Library can be found under [PermissionRequester/build/outputs/aar/PermissionRequester-release.aar](PermissionRequester/build/outputs/aar/PermissionRequester-release.aar) 

## How to build 

	gradle clean assembleRelease
		
## Requirements

* Java 1.6+
* Android SDK 23+
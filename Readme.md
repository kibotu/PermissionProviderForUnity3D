# Android Permission Provider Plugin for Unity3D [![Build Status](https://travis-ci.org/kibotu/PermissionProviderForUnity3D.svg)](https://travis-ci.org/kibotu/PermissionProviderForUnity3D)

## Introduction

Android 6.0 (API 23+) updated their [permissions handling](http://developer.android.com/intl/ko/training/permissions/requesting.html). This plugin helps with requesting permissions within Unity3D at runtime. 

## How to use

1. Add PermissionProvider component to any GameObject in your Scene.
2. Register a callback, for convenience look at [PermissionProvider.VerifyStorage](Unity/PermissionProvider.cs#L30-L35) method

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
## Introduction

This is a demo project, how to integrate the [HockeyApp Android SDK](https://github.com/bitstadium/HockeySDK-Android) into your app.

This document contains the following sections:

1. [Overview](#overview)
2. [Getting Started](#getting-started)
3. [Contributing](#contributing)
4. [Contributor License](#contributorlicense)
5. [Contact](#contact)

<a id="overview"></a>
## 1. Overview

This project can be used as a starting point for your Android app or sample how to integrate the HockeyApp Android SDK into your app.
It currently focuses on the following aspects:
* crash reporting
* in-app updates
* user feebdack

<a id="getting-started"></a>
## 2. Getting Started
To run this project there are some steps to perform, because we can not share the credentials to the app:

1. Clone the project.
2. Create a demo app in your HockeyApp account.
3. Open the `app/gradle.properties` file and replace the `hockeyapp_app_id` and `hockeyapp_app_secret` keys with the app id and app secret from your app in HockeyApp.
4. Sync your Gradle settings and build the app.

Please make sure to never commit your changes to the repository.

<a id="contributing"></a>
## 3. Contributing

We're looking forward to your contributions via pull requests.

**Development environment**

* Mac/Linux/Windows machine running the latest version of [Android Studio and the Android SDK](https://developer.android.com/sdk/index.html)

<a id="contributorlicense"></a>
## 4. Contributor License

You must sign a [Contributor License Agreement](https://cla.microsoft.com/) before submitting your pull request. To complete the Contributor License Agreement (CLA), you will need to submit a request via the [form](https://cla.microsoft.com/) and then electronically sign the CLA when you receive the email containing the link to the document. You need to sign the CLA only once to cover submission to any Microsoft OSS project. 

<a id="contact"></a>
## 5. Contact

If you have further questions or are running into trouble that cannot be resolved by any of the steps here, feel free to open a GitHub issue here or contact us at [support@hockeyapp.net](mailto:support@hockeyapp.net)

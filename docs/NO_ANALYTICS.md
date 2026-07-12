# Adobe Analytics Removal Summary

## Overview

All Adobe Analytics (Adobe Mobile SDK) telemetry code was removed from the ZowiAppReborn project. The app no longer sends screen tracking events, user timing metrics, or game interaction events to RTVE analytics servers. The build compiles successfully after the changes.

## What Was Removed

### Configuration Files
- `app/src/main/assets/ADBMobileConfig.json` — Adobe Analytics config pointing to `rtve.d1.sc.omtrdc.net`
- `app/src/main/java/com/adobe/mobile/Config.java` — Stub class with no-op `setContext()`, `collectLifecycleData()`, and `pauseCollectingLifecycleData()` methods

### Lifecycle Tracking (Base Classes)
- **BaseActivity.java** — Removed `Config.collectLifecycleData()` / `Config.pauseCollectingLifecycleData()` calls from `onResume`/`onPause`, removed `AnalyticsController` field and `getAnalyticsController()` method
- **BaseFragment.java** — Removed `AnalyticsController` field and `getAnalyticsController()` method

### Screen Tracking (Activities)
Removed `analyticsController.send(new Screen(...))` calls and related imports from:
- `PadViewActivity.java`
- `HomeViewActivity.java`
- `SettingsViewActivity.java`
- `AchievementsViewActivity.java`
- `ProjectViewActivity.java`
- `TimelineActivity.java`
- `MouthsMinigameActivity.java`
- `ZowiSaysMinigameActivity.java`
- `ZowiRunnerMinigameActivity.java`
- `MouthsEditorActivity.java`
- `WelcomeViewActivity.java`
- `WizardViewActivity.java` (4 screen events removed)

### Event Tracking (Activities)
Removed `analyticsController.send(new Event(...))` calls from:
- `ProjectQuizViewActivity.java` — Quiz right/wrong answer events

### Presenter Analytics Calls
- **PadPresenterImpl.java** — Removed ~20 gamepad move/animation/mouth `Event` sends, removed `AnalyticsController` field and constructor parameter
- **TimelinePresenterImpl.java** — Removed timeline sequence count, command type, and command selection `Event` sends, removed `AnalyticsController` field and constructor parameter
- **WizardPresenterImpl.java** — Removed wizard duration `UserTiming` send and `wizardStartTimeMillis` tracking field, removed `AnalyticsController` field and constructor parameter

### Controller Analytics Calls
- **AchievementsControllerImpl.java** — Removed achievement unlock `Event` send, removed `AnalyticsController` field and constructor parameter

### Dependency Injection
- **DependencyInjector.java** — Removed `provideAnalyticsController()` abstract method; removed analytics controller argument from `provideWizardPresenter()`, `providePadPresenter()`, `provideTimelinePresenter()`, and `provideAchievementsController()` calls
- **AndroidDependencyInjector.java** — Removed `Config.setContext()` call from `init()`, removed `provideAnalyticsController()` implementation with cache provider

### Constants Cleanup
- **ProjectControllerImpl.java** — Replaced `AnalyticsUtils.EVENT_ACHIEVEMENT` with literal `"achievement"` (used as JSON key for project data, not analytics)

## Files Still Present (Dead Code)

The following files are no longer referenced by any app code and can be safely deleted:

- `app/src/main/java/com/bq/zowi/analytics/AnalyticsUtils.java` — Event/screen constant definitions
- `app/src/main/java/com/bq/zowi/analytics/ZowiScreen.java` — Screen wrapper class for Adobe Analytics
- `app/src/main/java/com/bq/analytics/core/AnalyticsController.java` — Multi-tracker controller (library module)
- `app/src/main/java/com/bq/analytics/core/AnalyticsTracker.java` — Tracker interface
- `app/src/main/java/com/bq/analytics/hit/Event.java` — Event data model
- `app/src/main/java/com/bq/analytics/hit/Screen.java` — Screen data model
- `app/src/main/java/com/bq/analytics/hit/UserTiming.java` — User timing data model
- `app/src/main/java/com/bq/zowi/analytics/adobe/AdobeAnalyticsTracker.java` — Adobe-specific tracker implementation

## Verification

The project builds successfully with `./gradlew assembleDebug` after all changes. The only compilation notes are pre-existing deprecation and unchecked warnings unrelated to this change.

---

# BQ Analytics Library and comScore SDK Removal

## Overview

Following the Adobe Analytics removal, the remaining analytics-related dead code was identified and deleted. This includes the BQ custom analytics library (`com.bq.analytics.*`), the Zowi-specific analytics wrappers (`com.bq.zowi.analytics.*`), and the decompiled comScore SDK (`com.comscore.*`). No application code referenced any of these classes.

## What Was Removed

### BQ Analytics Library (`com.bq.analytics.*`)

10 files — a reusable analytics tracking library decompiled from `classes.dex`. Only referenced internally between its own classes; never imported by app code.

**Core:**
- `com/bq/analytics/core/AnalyticsTracker.java` — Tracker interface
- `com/bq/analytics/core/AnalyticsController.java` — Multi-tracker controller
- `com/bq/analytics/core/AnalyticsTrackerImpl.java` — Abstract base tracker

**Hit models:**
- `com/bq/analytics/hit/Event.java`
- `com/bq/analytics/hit/Screen.java`
- `com/bq/analytics/hit/Social.java`
- `com/bq/analytics/hit/Crash.java`
- `com/bq/analytics/hit/Ecommerce.java`
- `com/bq/analytics/hit/UserTiming.java`

**Utilities:**
- `com/bq/analytics/tracker/Util.java`

### Zowi Analytics (`com.bq.zowi.analytics.*`)

2 files — app-specific analytics wrappers.

- `com/bq/zowi/analytics/AnalyticsUtils.java` — Event/screen constant definitions
- `com/bq/zowi/analytics/ZowiScreen.java` — Screen wrapper with device dimensions

### comScore SDK (`com.comscore.*`)

~100 decompiled files (version `3.1502.26`) across 8 sub-packages. Originally used for RTVE audience measurement via `scorecardresearch.com`. The entire SDK had been excluded from compilation via `build.gradle` `exclude` directives and replaced with no-op stubs.

**Packages removed:**
- `com/comscore/analytics/` — Core analytics engine (`Core`, `comScore`, `Census`, `ApplicationState`, `SessionState`, + 22 obfuscated classes)
- `com/comscore/streaming/` — StreamSense video tracking (`StreamSense`, `StreamSenseClip`, `StreamSensePlaylist`, + 16 classes)
- `com/comscore/streaming/plugin/` — Player plugin system (6 classes)
- `com/comscore/applications/` — Application measurement models (5 classes)
- `com/comscore/measurement/` — Measurement data model (5 classes)
- `com/comscore/metrics/` — HTTP request layer (3 classes)
- `com/comscore/utils/` — Utilities: storage, caching, connectivity, logging, etc. (~25 classes)
- `com/comscore/android/` — Android device ID helpers (5 classes)
- `com/comscore/instrumentation/` — Auto-tracking base activities (4 classes)

**Stubs removed:**
- `app/src/main/stubs/com/comscore/analytics/comScore.java` — No-op API facade
- `app/src/main/stubs/com/comscore/streaming/Constants.java` — Stub constant

### Build Configuration Cleanup

**`app/build.gradle`:**
- Removed `exclude 'com/comscore/**'` from `sourceSets.main.java`
- Removed redundant `exclude 'com/comscore/instrumentation/InstrumentedMapActivity.java'`

### Resource Cleanup

**`app/src/main/res/values/integers.xml`:**
- Removed orphaned `<integer name="rtve_comscore_autoupdate_period">300</integer>` (5-minute auto-update period no longer used)

## Files Still Present (Dead Code)

The following Bugsnag files were found to reference two comScore classes (`com.comscore.streaming.Constants` and `com.comscore.android.id.IdHelperAndroid`) but are themselves excluded from compilation via `build.gradle` (`exclude 'com/bugsnag/**'`). They require no further action.

- `app/src/main/java/com/bugsnag/android/DeviceData.java`
- `app/src/main/java/com/bugsnag/android/DeviceState.java`

---

# Decompiled Google Ads and Gson Sources Removal

## Overview

Following the analytics cleanup, 81 decompiled source files under `com/google/` were identified as dead code. These were JADX-decompiled stubs from a previously built APK, excluded from compilation via `build.gradle` `exclude` directives. None contained analytics or tracking functionality. The project already uses the real libraries via Gradle dependencies.

## What Was Removed

### Google Ads Legacy API (`com.google.ads.*`)

22 files — decompiled legacy AdMob ads API and mediation framework. No Gradle dependency on Google Ads exists; no app code references any of these classes.

**Packages removed:**
- `com/google/ads/` — `AdRequest.java`, `AdSize.java`
- `com/google/ads/mediation/` — `AbstractAdViewAdapter`, `MediationAdapter`, `MediationAdRequest`, `MediationBannerAdapter`, `MediationBannerListener`, `MediationInterstitialAdapter`, `MediationInterstitialListener`, `MediationServerParameters`, `NetworkExtras`, `AdUrlAdapter`, `EmptyNetworkExtras`
- `com/google/ads/mediation/admob/` — `AdMobAdapter.java`
- `com/google/ads/mediation/customevent/` — `CustomEvent`, `CustomEventAdapter`, `CustomEventBanner`, `CustomEventBannerListener`, `CustomEventInterstitial`, `CustomEventInterstitialListener`, `CustomEventListener`, `CustomEventServerParameters`

### Google Gson Decompiled Sources (`com.google.gson.*`)

59 files — decompiled copy of the Gson library. The app uses Gson via the Gradle dependency `com.google.code.gson:gson:2.10.1`; these source files were excluded from compilation and served no purpose.

**Packages removed:**
- `com/google/gson/` — Core classes: `Gson`, `GsonBuilder`, `JsonObject`, `JsonArray`, `JsonElement`, `JsonParser`, `TypeAdapter`, `TypeAdapterFactory`, `FieldNamingPolicy`, `ExclusionStrategy`, etc. (30 files)
- `com/google/gson/annotations/` — `@SerializedName`, `@Expose`, `@Since`, `@Until`, `@JsonAdapter`
- `com/google/gson/reflect/` — `TypeToken`
- `com/google/gson/stream/` — `JsonReader`, `JsonWriter`, `JsonToken`, `JsonScope`, `MalformedJsonException`
- `com/google/gson/internal/` — Internal implementation: `ConstructorConstructor`, `Excluder`, `LinkedTreeMap`, `Primitives`, `Streams`, `UnsafeAllocator`, etc. (11 files)
- `com/google/gson/internal/bind/` — Type adapters: `ArrayTypeAdapter`, `CollectionTypeAdapterFactory`, `DateTypeAdapter`, `MapTypeAdapterFactory`, `ReflectiveTypeAdapterFactory`, `TypeAdapters`, etc. (12 files)

### Build Configuration Cleanup

**`app/build.gradle`:**
- Removed `exclude 'com/google/ads/**'` from `sourceSets.main.java`
- Removed `exclude 'com/google/gson/**'` from `sourceSets.main.java`
- Removed `srcDirs` reference to `src/main/stubs` (directory was empty after prior cleanup)
- Removed empty `app/src/main/stubs/` directory

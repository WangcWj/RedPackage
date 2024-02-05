# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /home/nian/Android/Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}
-keep class android.support.**{*;}

#AndroidX
-keep public class * extends androidx.fragment.app.Fragment
-keep class androidx.core.**{ *; }
-keep class androidx.fragment.**{ *; }
-keep class androidx.loader.**{ *; }
-keep class androidx.localbroadcastmanager.**{ *; }
-keep class androidx.androidx.legacy.**{ *; }
-keep class androidx.media.**{ *; }
-keep class androidx.print.**{ *; }
-keep class androidx.documentfile.**{ *; }
-keep class androidx.collection.**{ *; }
-keep class androidx.customview.**{ *; }
-keep class androidx.interpolator.**{ *; }
-keep class androidx.asynclayoutinflater.**{ *; }
-keep class androidx.viewpager.**{ *; }
-keep class androidx.swiperefreshlayout.**{ *; }
-keep class androidx.cursoradapter.**{ *; }
-keep class androidx.legacy.**{ *; }
-keep class androidx.slidingpanelayout.**{ *; }
-keep class androidx.coordinatorlayout.**{ *; }
-keep class androidx.appcompat.**{ *; }
-keep class androidx.mediarouter.**{ *; }
-keep class androidx.palette.**{ *; }
-keep class androidx.gridlayout.**{ *; }
-keep class androidx.preference.**{ *; }
-keep class androidx.recyclerview.**{ *; }
-keep class androidx.cardview.**{ *; }
-keep interface androidx.core.app.** { *; }
-keep interface androidx.fragment.app.** { *; }
-keep interface androidx.legacy.app.** { *; }
-keep interface androidx.loader.app.** { *; }

-keep class com.google.android.material.**{ *; }
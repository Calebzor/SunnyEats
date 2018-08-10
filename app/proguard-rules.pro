# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Preserve the line number information for better debugging of stack traces.
-keepattributes SourceFile,LineNumberTable
# Hide the original source file name.
-renamesourcefileattribute SourceFile

## Retrofit (from their docs)
# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions
# Retrofit uses Okio under the hood, rules from Okio docs:
-dontwarn okio.**
-dontwarn javax.annotation.**

# https://github.com/google/dagger/issues/645#issuecomment-296851566
-dontwarn com.google.errorprone.annotations.*

# https://github.com/google/dagger/issues/645#issuecomment-296851566
-dontwarn com.google.errorprone.annotations.*
#!/bin/bash

###################################################
# CHANGE THESE FOR YOUR SYSTEM
emulator_path=$ANDROID_SDK_ROOT"/emulator/emulator"
emulator_name="Rooted"
###################################################
# CHANGE THESE IF YOU WANT TO CHANGE/HAVE CHANGED THE APP NAME
dir_app_name="Carp"
app_name="com.example.myapplication"


ADB="adb" # how you execute adb
ADB_SH="$ADB shell" # this script assumes using `adb root`. for `adb su` see `Caveats`

path_sysapp="/system/priv-app"
apk_host="./automotive/build/outputs/apk/debug/automotive-debug.apk"
apk_name=$dir_app_name".apk"
apk_target_dir="$path_sysapp/$dir_app_name"
apk_target_sys="$apk_target_dir/$apk_name"

# Delete previous APK
rm -f $apk_host

# Compile the APK: you can adapt this for production build, flavors, etc.
./gradlew assembleDebug || exit -1 # exit on failure

# check if emulator is already running
adb_output="$(adb get-state)"


if [[ ! "$adb_output" =~ "device" ]]
then
  echo "Starting the emulator...."
  $emulator_path -avd $emulator_name -writable-system &
fi


while [[ ! "$adb_output" =~ "device" ]]
do
  echo "."
  adb_output="$(adb get-state)"
  sleep 1s
done

# Stop the app
 $ADB_SH "am force-stop $app_name"

# Install APK: using adb root
$ADB remount
$ADB push $apk_host $apk_target_sys

# Give permissions
$ADB_SH "chmod 755 $apk_target_dir"
$ADB_SH "chmod 644 $apk_target_sys"

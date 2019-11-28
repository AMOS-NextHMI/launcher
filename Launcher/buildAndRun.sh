#!/bin/bash

if [ -f .env ]
then
  export $(xargs <.env)
fi

if [ -z ${AMOS_EMULATOR_PATH+x} ]; then AMOS_EMULATOR_PATH=$ANDROID_SDK_ROOT"/emulator/emulator"; fi
if [ -z ${AMOS_EMULATOR_NAME+x} ]; then AMOS_EMULATOR_NAME="Rooted"; fi
if [ -z ${AMOS_LAUNCHER_DIR_NAME+x} ]; then AMOS_LAUNCHER_DIR_NAME="Carp"; fi
if [ -z ${AMOS_LAUNCHER_APP_NAME+x} ]; then AMOS_LAUNCHER_APP_NAME="com.example.myapplication"; fi

ADB="adb" # how you execute adb
ADB_SH="$ADB shell" # this script assumes using `adb root`. for `adb su` see `Caveats`

path_sysapp="/system/priv-app"
apk_host="./automotive/build/outputs/apk/debug/automotive-debug.apk"
apk_name=$AMOS_LAUNCHER_DIR_NAME".apk"
apk_target_dir="$path_sysapp/$AMOS_LAUNCHER_DIR_NAME"
apk_target_sys="$apk_target_dir/$apk_name"

# Delete previous APK
rm -f $apk_host

# Compile the APK: you can adapt this for production build, flavors, etc.
./gradlew assembleDebug || exit 1 # exit on failure

# check if emulator is already running
adb_output="$(adb get-state)"


if [[ ! "$adb_output" =~ "device" ]]
then
  echo "Starting the emulator...."
  $AMOS_EMULATOR_PATH -avd $AMOS_EMULATOR_NAME -writable-system &
fi


while [[ ! "$adb_output" =~ "device" ]]
do
  echo "."
  adb_output="$(adb get-state)"
  sleep 1s
done

# Set the appropriate shell parameters
$ADB_SH settings put global hidden_api_policy_pre_p_apps 1
$ADB_SH settings put global hidden_api_policy_p_apps 1

# Stop the app
$ADB_SH "am force-stop $AMOS_LAUNCHER_APP_NAME"

# Install APK: using adb root
$ADB remount
$ADB push $apk_host $apk_target_sys

# Give permissions
$ADB_SH "chmod 755 $apk_target_dir"
$ADB_SH "chmod 644 $apk_target_sys"

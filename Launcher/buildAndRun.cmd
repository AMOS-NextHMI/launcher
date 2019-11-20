::WIN BATCH SCRIPT

:: CHANGE THESE
set emulator_path=%ANDROID_SDK_ROOT%/emulator/emulator
set emulator_name=Rooted
set app_name=com.example.myapplication
set dir_app_name=carp
set MAIN_ACTIVITY=MainActivity

set ADB="adb"
set path_sysapp=/system/priv-app
set apk_host=.\automotive\build\outputs\apk\debug\automotive-debug.apk
set apk_name=%dir_app_name%.apk
set apk_target_dir=%path_sysapp%/%dir_app_name%
set apk_target_sys=%apk_target_dir%/%apk_name%

:: Delete previous APK
del %apk_host%

:: Compile the APK: you can adapt this for production build, flavors, etc.
call gradlew assembleDebug

:: Check if emulator is already running
set ADB_SH=%ADB% shell su -c get-state
if "%ADB_SH%" NEQ "device" echo Starting the emulator && %emulator_path% -avd %emulator_name% -writable-system
for "%ADB_SH% NEQ "device" do %ADB_SH%=%ADB% shell su -c get-state && timeout 1

:: Stop the app
%ADB% shell am force-stop %app_name%

:: Install APK: using adb su
%ADB_SH% mount -o rw,remount /system
%ADB_SH% chmod 777 /system/lib/
%ADB_SH% mkdir -p /sdcard/tmp
%ADB_SH% mkdir -p %apk_target_dir%
%ADB% push %apk_host% /sdcard/tmp/%apk_name%
%ADB_SH% mv /sdcard/tmp/%apk_name% %apk_target_sys%
%ADB_SH% rmdir /sdcard/tmp

:: Give permissions
%ADB_SH% chmod 755 %apk_target_dir%
%ADB_SH% chmod 644 %apk_target_sys%
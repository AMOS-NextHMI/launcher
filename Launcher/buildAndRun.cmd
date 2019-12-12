::WIN BATCH SCRIPT

:: CHANGE THESE
@echo off
set emulator_path=%ANDROID_SDK_ROOT%/emulator/emulator
set emulator_name="Rooted_Automotive_1024p_landscape_API_28"
set app_name=com.example.myapplication
set dir_app_name=Car_Launcher
set MAIN_ACTIVITY=MainActivity

set ADB=adb
set path_sysapp=/system/priv-app
set apk_host=.\automotive\build\outputs\apk\debug\automotive-debug.apk
set apk_name=%app_name%.apk
set apk_target_dir=%path_sysapp%/%dir_app_name%
set apk_target_sys=%apk_target_dir%/%apk_name%

:: Delete previous APK
del %apk_host%

:: Compile the APK: you can adapt this for production build, flavors, etc.
call gradlew assembleDebug

:: Check if emulator is already running
for /f %%i in ('%ADB% get-state') do set ADB_OUTPUT=%%i
echo Wiping and starting the emulator...
if %ADB_OUTPUT% NEQ "device" start %emulator_path% -avd %emulator_name% -writable-system -wipe-data

:waiting_loop
echo Waiting for a ping...
ping localhost -n 6 >NUL
for /f %%i in ('%ADB% get-state') do set ADB_OUTPUT=%%i

if "%ADB_OUTPUT%" NEQ "device" goto waiting_loop

set ADB_SH=%ADB% shell

:: Install APK: using adb su
%ADB% remount
:: %ADB% push %apk_host% %apk_target_sys%
echo Waiting for three pings until the emulator is really ready...
ping localhost -n 6 >NUL
ping localhost -n 6 >NUL
ping localhost -n 6 >NUL
%ADB% install %apk_host%
%ADB% install ..\..\AATripComputerApp\%apk_host%

:: Give permissions
:: %ADB_SH% chmod 755 %apk_target_dir%
:: %ADB_SH% chmod 644 %apk_target_sys%

:: Set the appropriate shell parameters
%ADB_SH% settings put global hidden_api_policy_pre_p_apps 1
%ADB_SH% settings put global hidden_api_policy_p_apps 1
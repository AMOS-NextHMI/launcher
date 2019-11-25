::WIN BATCH SCRIPT

:: CHANGE THESE
set emulator_path=%ANDROID_SDK_ROOT%/emulator/emulator
set emulator_name="Rooted_Automotive_1024p_landscape_API_28"
set app_name="My Application"
set dir_app_name="com.example.myapplication"
set MAIN_ACTIVITY=MainActivity

set ADB=adb
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
for /f %%i in ('%ADB% get-state') do set ADB_OUTPUT=%%i
if "%ADB_OUTPUT%" NEQ "device" echo Starting the emulator... & %emulator_path% -avd %emulator_name% -writable-system

:waiting_loop
echo Waiting for a ping...
ping localhost -n 6 >NUL
for /f %%i in ('%ADB% get-state') do set ADB_OUTPUT=%%i

if "%ADB_OUTPUT%" NEQ "device" goto waiting_loop

set ADB_SH=%ADB% shell

:: Stop the app
%ADB_SH% am force-stop %dir_app_name%

:: Install APK: using adb su
%ADB_SH% mount -o rw,remount
%ADB% push %apk_host% %apk_target_sys%

:: Give permissions
%ADB_SH% chmod 755 %apk_target_dir%
%ADB_SH% chmod 644 %apk_target_sys%
# AMOS Project Group 4
# Forked AAOS Launcher as a POC for a HMI Infotainment

## Setup

### Extra libraries car/hidden

The repo also supplies libraries which provide access to the hidden android api and the android car api.
To import them in Android Studio you have to do the following:
1. Copy the files in the extra-lib folder into <sdk_folder>\platforms\<api_level=28 for this project>\optional
2. Extend the optional.json file inside this directory and add the following fields:
```json
{
  "name": "android.car",
  "jar": "android.car.jar",
  "manifest": false
},
{
  "name": "android.hidden",
  "jar": "android.hidden.jar",
  "manifest": false
}
```
3. Add these libraries to your (automotive) build.gradle file:
```gradle
android {
  ...
  useLibrary 'android.car'
  useLibrary 'android.hidden'
}
```

### Add the rooted AAOS image to your Android Studio

1. Open Android Studio
2. Open SDK Manager (In the tools tab)
3. There will be a tab "SDK Update Sites" click it
4. There on the right there is a + Button click it
5. As Name choose what you want, as URL input https://studiquizz.de/android/car/repo-sys-img.xml
6. No login or password
7. Click apply in the lower right corner
8. The go to the tab "SDK Platforms"
9. Check "Show Package Details"
10. Under Android 9.0 (Pie) check the "Rooted image of the AAOS Intel x86 Atom_64 System Image" and click apply
11. Then the image will be downloaded, just click through the process
12. Now you should be able to create a new AVD in den AVD Manager just select, in the Select a system image dialog, the new image, in the x86 Images tab.

### Add the appropiate buildAndRun script to your run configuration

1. Open Android Studio
2. Under Run click "Edit Configurations"
3. Add a new Android App configuration
4. Deploy nothing and launch nothing
5. Under "Before launch: Gradle-aware Make, External tool click the plus sign
6. Click "Run external tool"
7. Add a new tool with the plus sign
8. Under "Program:" click the folder icon to add the apropiate script (.sh for UNIX systems and .bat for windows)
9. Okay all overlays and you're done

## Running the app on the emulator while developing

**You have to run the app via this method otherwise you will no obtain system level permissions and get an error**

0.1 Check if the emulator_path in the script works for you and otherwise change it to the right path, then change the emulator_name to the name of your rooted emulator. After that create the directory on your emulator in which you want the launcher-apk to sit (inside the /system/priv-app folder), by starting the emulator with the following command:
```bash
$ANDROID_SDK_ROOT"/emulator/emulator" -avd $emulator_name -writable-system
```
then, after the emulator has started, get into the adb shell and create the folder:
```bash
adb remount
adb shell
cd /system/priv-app
mkdir Carp
```
0.2 Make the script executable on UNIX Sytems
```bash
chmod +x buildAndRun.sh
```
Please note, that the folder name has to be the same as the dir_app_name variable in the script. So if you part from the one inside the script, you have to modify this var.
1. You **have** to start the emulator via the buildAndRun script **otherwise the system is not writable** and you will get an error
2. After the script started the emulator you can apply changes that you made in the code via the "Apply Changes and Restart Activity" ![icon image](img/readme/apply-changes-and-run-activity.png) (Ctrl+F10) **Do not press the usual "Run" Button, it will result in a premission error**

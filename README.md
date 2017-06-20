
# react-native-file-get

## Getting started

`$ npm install react-native-file-get --save`

### Mostly automatic installation

`$ react-native link react-native-file-get`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-file-get` and add `RNFileGet.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNFileGet.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNFileGetPackage;` to the imports at the top of the file
  - Add `new RNFileGetPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-file-get'
  	project(':react-native-file-get').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-file-get/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-file-get')
  	```

#### Windows
[Read it! :D](https://github.com/ReactWindows/react-native)

1. In Visual Studio add the `RNFileGet.sln` in `node_modules/react-native-file-get/windows/RNFileGet.sln` folder to their solution, reference from their app.
2. Open up your `MainPage.cs` app
  - Add `using Com.Reactlibrary.RNFileGet;` to the usings at the top of the file
  - Add `new RNFileGetPackage()` to the `List<IReactPackage>` returned by the `Packages` method


## Usage
```javascript
import RNFileGet from 'react-native-file-get';

// TODO: What to do with the module?
RNFileGet;
```
  
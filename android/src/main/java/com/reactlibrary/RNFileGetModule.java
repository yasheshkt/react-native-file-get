
package com.reactlibrary;

import android.app.Activity;
import android.content.Intent;
import android.provider.Settings;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import java.util.List;
import java.util.ArrayList;
import android.content.Context;
import android.util.Log;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.Bitmap;
import java.io.ByteArrayOutputStream;
import android.util.Base64;
import android.graphics.Canvas;
import android.content.SharedPreferences;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;

public class RNFileGetModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;
  SharedPreferences sharedpreferences;
  public static final String MyPREFERENCES = "FileGet";

  interface Metadata {
      String ID = "ID";
      String NAME = "Name";
      String IMAGE = "Image";
      String ISLOCKED = "IsLocked";
  }

  public RNFileGetModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNFileGet";
  }

  boolean isUserApp(ApplicationInfo ai) {
      int mask = ApplicationInfo.FLAG_SYSTEM | ApplicationInfo.FLAG_UPDATED_SYSTEM_APP;
      return (ai.flags & mask) == 0;
  }  

  String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality)
  {
      ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
      image.compress(compressFormat, quality, byteArrayOS);
      return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
  }  
  Bitmap drawableToBitmap (Drawable drawable) {
      Bitmap bitmap = null;

      if (drawable instanceof BitmapDrawable) {
          BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
          if(bitmapDrawable.getBitmap() != null) {
              return bitmapDrawable.getBitmap();
          }
      }

      if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
          bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
      } else {
          bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
      }

      Canvas canvas = new Canvas(bitmap);
      drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
      drawable.draw(canvas);
      return bitmap;
  }

  @ReactMethod
  public void getListOfInstalledApps(Callback successCallback) {
    sharedpreferences = this.reactContext.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    PackageManager pm = this.reactContext.getPackageManager();
    List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
    WritableArray finalResult = Arguments.createArray();
    for(ApplicationInfo packageInfo:packages){
      if(isUserApp(packageInfo)) {
        WritableMap result = Arguments.createMap();
        String applicationName = (String) (packageInfo != null ? pm.getApplicationLabel(packageInfo) : "(unknown)");
        Drawable icon = pm.getApplicationIcon(packageInfo);

        Bitmap bitmapOfIcon = drawableToBitmap(icon);

        String base64OfIcon = encodeToBase64(bitmapOfIcon, Bitmap.CompressFormat.PNG, 100);

        boolean IsLocked = sharedpreferences.getBoolean(packageInfo.packageName, false);

        result.putString(Metadata.ID, packageInfo.packageName); 
        result.putString(Metadata.NAME, applicationName);
        result.putString(Metadata.IMAGE, base64OfIcon);
        result.putBoolean(Metadata.ISLOCKED, IsLocked);
        finalResult.pushMap(result);
      }        
    }
    successCallback.invoke(finalResult);
  }
  @ReactMethod
  public void lockApp(String appID, Callback successCallback) {
    Log.d("APP ID: ", appID);
    sharedpreferences = this.reactContext.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedpreferences.edit();
    editor.putBoolean(appID, true);
    boolean result = editor.commit();
    successCallback.invoke(result);
    // boolean pref = sharedpreferences.getBoolean(appID, false);
    // Log.d("pref: ", pref.toString());
  }

}

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

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;

public class RNFileGetModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

  interface Metadata {
      String ID = "ID";
      String NAME = "Name";
      String IMAGE = "Image";
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

  public String getImageFromDrawable(Drawable drawable){
      String img = null;
      if(drawable instanceof  BitmapDrawable) {
          Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
          ByteArrayOutputStream stream = new ByteArrayOutputStream();
          bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
          byte[] arr = stream.toByteArray();
          img = Base64.encodeToString(arr, Base64.URL_SAFE);
          return img;
      }
      return null;
  }  

  @ReactMethod
  public void openWifiSettings(Callback successCallback) {
    PackageManager pm = this.reactContext.getPackageManager();
    List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
    WritableArray finalResult = Arguments.createArray();
    for(ApplicationInfo packageInfo:packages){
      if(isUserApp(packageInfo)) {
        WritableMap result = Arguments.createMap();
        String applicationName = (String) (packageInfo != null ? pm.getApplicationLabel(packageInfo) : "(unknown)");
        // Drawable icon = getPackageManager().getApplicationIcon(packageInfo.processName);
        String image = getImageFromDrawable(packageInfo.loadIcon(pm));
        result.putString(Metadata.ID, packageInfo.packageName); 
        result.putString(Metadata.NAME, applicationName);
        result.putString(Metadata.IMAGE, image);
        finalResult.pushMap(result);
      }        
    }
    successCallback.invoke(finalResult);
  }

}
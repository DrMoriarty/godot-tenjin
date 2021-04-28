package ru.mobilap.tenjin;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import com.facebook.applinks.AppLinkData;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.tenjin.android.Callback;
import com.tenjin.android.TenjinSDK;
import java.io.IOException;
import java.lang.Exception;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.godotengine.godot.Godot;
import org.godotengine.godot.GodotLib;
import org.godotengine.godot.plugin.GodotPlugin;
import org.godotengine.godot.plugin.SignalInfo;

public class Tenjin extends GodotPlugin
{
	private Activity activity = null;
    private String apiKey = null;

    public Tenjin(Godot godot) 
    {
        super(godot);
        activity = godot.getActivity();
    }

    @Override
    public String getPluginName() {
        return "Tenjin";
    }

    @Override
    public List<String> getPluginMethods() {
        return Arrays.asList(
                             "init", "logEvent", "logEventWithValue", "logPurchase", "logPurchaseWithSignature", "advertising_id"
                             );
    }

    /*
    @Override
    public Set<SignalInfo> getPluginSignals() {
        return Collections.singleton(loggedInSignal);
    }
    */

    @Override
    public View onMainCreate(Activity activity) {
        return null;
    }

    public void init(final String _apiKey, final String _deepLinkUri) {
        apiKey = _apiKey;
        TenjinSDK instance = TenjinSDK.getInstance(activity, apiKey);
        if(_deepLinkUri != null && _deepLinkUri != "") {
            instance.connect(_deepLinkUri);
        } else {
            instance.connect();
        }
        Log.i("godot", "Tenjin plugin inited!");
        Log.i("godot", "Device GAID: "+advertising_id());
        instance.getDeeplink(new Callback() {
            @Override
            public void onSuccess(boolean clickedTenjinLink, boolean isFirstSession, Map<String, String> data) {
                if (clickedTenjinLink) {
                    if (isFirstSession) {
                        if (data.containsKey(TenjinSDK.DEEPLINK_URL)) {
                           // use the deferred_deeplink_url to direct the user to a specific part of your app
                        }
                    }
                }
            }
        });
    }

    public void logEvent(final String event) {
        if(apiKey != null)
            tenjinInstance().eventWithName(event);
    }

    public void logEventWithValue(final String event, final int value) {
        if(apiKey != null)
            tenjinInstance().eventWithNameAndValue(event, ""+value);
    }

    public void logPurchase(final String productId, final String currencyCode, final int quantity, final float unitPrice) {
        if(apiKey != null)
            tenjinInstance().transaction(productId, currencyCode, quantity, unitPrice);
    }

    public void logPurchaseWithSignature(final String productId, final String currencyCode, final int quantity, final float unitPrice, final String purchaseData, final String dataSignature) {
        if(apiKey != null)
            tenjinInstance().transaction(productId, currencyCode, quantity, unitPrice, purchaseData, dataSignature);
    }
    
    public String advertising_id()
    {
        Info adInfo = null;
        try {
            adInfo = AdvertisingIdClient.getAdvertisingIdInfo(activity);
            String userId = adInfo.getId();
            return userId;
        } catch (IOException e) {
            Log.e("godot", e.toString());
        //} catch (GooglePlayServicesAvailabilityException e) {
            //Log.e("godot", e.toString());
        } catch (GooglePlayServicesNotAvailableException e) {
            Log.e("godot", e.toString());
        } catch (Exception e) {
            Log.e("godot", e.toString());
        }
        return "";
    }

    private TenjinSDK tenjinInstance() {
        return TenjinSDK.getInstance(activity, apiKey);
    }

    @Override public void onMainResume() {
    } 

}

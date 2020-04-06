package org.godotengine.godot;

import android.app.Activity;
import android.util.Log;
import com.facebook.applinks.AppLinkData;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.tenjin.android.Callback;
import com.tenjin.android.TenjinSDK;
import java.io.IOException;
import java.lang.Exception;
import java.util.Map;

public class GodotTenjin extends Godot.SingletonBase
{
	private Activity activity = null;
    private String apiKey = null;

	/**
	 * Initilization Singleton
	 * @param Activity The main activity
	 */
 	static public Godot.SingletonBase initialize(Activity activity)
 	{
 		return new GodotTenjin(activity);
 	}

	/**
	 * Constructor
	 * @param Activity Main activity
	 */
	public GodotTenjin(Activity p_activity) {
		registerClass("Tenjin", new String[] {
                "init"
            });
		activity = p_activity;
	}

    public void init(final String _apiKey) {
        apiKey = _apiKey;
        TenjinSDK instance = TenjinSDK.getInstance(activity, apiKey);
        instance.connect();
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

    public void logPurchase(final String productId, final String currencyCode, final int quantity, final double unitPrice) {
        if(apiKey != null)
            tenjinInstance().transaction(productId, currencyCode, quantity, unitPrice);
    }

    public void logPurchaseWithSignature(final String productId, final String currencyCode, final int quantity, final double unitPrice, final String purchaseData, final String dataSignature) {
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

    @Override protected void onMainResume() {
    } 

}

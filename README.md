# Tenjin plugin for Godot engine. Free attribution, Ad Revenue LTV, Cost and ad revenue aggregation.

## Installation using NativeLib Addon

1. Add [NativeLib Addon](https://github.com/DrMoriarty/nativelib) into your project (search it in Godot's AssetLib).

2. Find `TENJIN` in plugins list and press "Install" button.

3. Set your API key in plugin variables list.

4. Enable **Custom Build** for using in Android.

## Installation using NativeLib-CLI

1. Install [NativeLib-CLI](https://github.com/DrMoriarty/nativelib-cli) in your system.

2. Make `nativelib -i tenjin` in your project directory.

3. Set `Tenjin/ApiKey` in your project settings.

4. Enable **Custom Build** for using in Android.

## Usage

Wrapper on gd-script will be in your autoloading list. Use global name `tenjin` anywhere in your code to use API.

## API

### logEvent(event: String)

Log any custom event in your application.

### logEventWithValue(event: String, value: int)

Log custom event with value which can be summarized in reports.

### logPurchase(sku: String, currency: String, amount: int, price: float)

Log in-app purchase.

### logPurchaseWithSignature(sku: String, currency: String, amount: int, price: float, data: String, signature: String)

Log in-app purchase with receipt verification.

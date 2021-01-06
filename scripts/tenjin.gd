extends Node

var _tj = null
onready var _facebook = $'/root/facebook' if node_exists('/root/facebook') else null

# Called when the node enters the scene tree for the first time.
func _ready() -> void:
    pause_mode = Node.PAUSE_MODE_PROCESS
    if(Engine.has_singleton("Tenjin")):
        _tj = Engine.get_singleton("Tenjin")
    var deepLink = ''
    if _facebook != null:
        deepLink = _facebook.deep_link_uri()
    var apiKey = ''
    if ProjectSettings.has_setting('Tenjin/ApiKey'):
        apiKey = ProjectSettings.get_setting('Tenjin/ApiKey')
    init(apiKey, deepLink)

# Called every frame. 'delta' is the elapsed time since the previous frame.
#func _process(delta: float) -> void:
#    pass

func init(apiKey, deepLinkUri = ''):
    if _tj != null:
        _tj.init(apiKey, deepLinkUri)

func logEvent(event: String) -> void:
    if _tj != null:
        _tj.logEvent(event)

func logEventWithValue(event: String, value: int) -> void:
    if _tj != null:
        _tj.logEventWithValue(event, value)

func logPurchase(sku: String, currency: String, amount: int, price: float) -> void:
    if _tj != null:
        _tj.logPurchase(sku, currency, amount, price)

func logPurchaseWithSignature(sku: String, currency: String, amount: int, price: float, data: String, signature: String) -> void:
    if _tj != null:
        _tj.logPurchaseWithSignature(sku, currency, amount, price, data, signature)

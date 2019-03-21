package giavu.hoangvm.hh.tracker

sealed class Event(val eventName: String) {
    object FirstOpen : Event("HOI_first_open")
    object LoginSuccess: Event("login_success")
    object LoginFailure: Event("login_failure")
    object IMPSRegister: Event("imps_register")
    object TapMenu: Event("tap_menu")
    object TapQuoteList: Event("tap_quote_list")
    object TapLogout: Event("tap_logout")
}

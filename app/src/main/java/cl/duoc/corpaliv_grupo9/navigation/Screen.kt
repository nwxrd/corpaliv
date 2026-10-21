package cl.duoc.corpaliv_grupo9.navigation

sealed class Screen(val route : String) {

    data object Home : Screen(route = "home_screen")
    data object Impact : Screen(route = "impact_screen")
    data object Products : Screen(route = "products_screen")
    data object HelpOptions : Screen(route = "help_options_screen")
    data object Welcome : Screen(route = "welcome_screen")
    data object Programs : Screen(route = "programs_screen")
    data object ContactForm : Screen(route = "contact_form_screen")
    data object Confirmation : Screen(route = "confirmation_screen")
    data object AdminCrm : Screen(route = "admin_crm_screen")

    /*data class Detail(val itemId : String) : Screen("detail_page/{itemId}"){
        fun buildRoute() : String {
            return route.replace("{itemId}",itemId)
        }
    }*/
}
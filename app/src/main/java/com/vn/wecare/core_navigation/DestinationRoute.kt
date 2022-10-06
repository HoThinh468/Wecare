package com.vn.wecare.core_navigation

const val WECARE = "WECARE"

class AuthenticationRoutes {
    companion object {
        const val graph = "AUTH_GRAPH"
        const val signInDes = "sign_in_screen"
        const val signUpDes = "sign_up_screen"
        const val forgotPassDes = "forgot_pass_screen"
    }
}

class HomeRoutes {
    companion object {
        const val graph = "HOME_GRAPH"
        const val homeDes = "Home_screen"
    }
}

class TrainingRoutes {
    companion object {
        const val graph = "TRAINING_GRAPH"
        const val trainingDes = "Training_screen"
    }
}

class AccountRoutes {
    companion object {
        const val graph = "ACCOUNT_GRAPH"
        const val accountDes = "Account_screen"
    }
}
package com.isain.network

sealed class Environment(val mainUrl: String) {

    class Debug : Environment("https://raw.githubusercontent.com")
    class Staging : Environment("https://raw.githubusercontent.com")
    class Production : Environment("https://raw.githubusercontent.com")
    class Custom(mainUrl: String) : Environment(mainUrl)

    fun name() = when (this) {
        is Debug -> "Test"
        is Staging -> "Stage"
        is Production -> "Prod"
        is Custom -> "$mainUrl"
    }

    fun url(api: Api): String {
        return when (api) {
            Api.Main -> mainUrl
        }
    }

    enum class Api {
        Main
    }
}

data class CustomApi(val debugUrl: String, val stageUrl: String, val productionUrl: String) {

    fun provideUrl(network: Network) = when (network.environment) {
        is Environment.Debug -> debugUrl
        is Environment.Staging -> stageUrl
        is Environment.Production -> productionUrl
        is Environment.Custom -> debugUrl
    }

}
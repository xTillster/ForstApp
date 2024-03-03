package com.example.forstapp.POJO

/** POJO of "Afrikanische Schweinepest" document **/

data class ASP(
    var profile: ASPProfile, var region: String, var localZipCode: String,
    var localCity: String, var latitude: String, var longitude: String,
    var wildlifeOrigin: String, var locationRegion: String, var sex: String,
    var trap: String, var age: String, var material: String,
    var decomposition: String, var causeOfDeath: String
) {
    companion object {
        val ASPs = ArrayList<ASP>()
        lateinit var currentASP: ASP
        fun setEmptyASP() {
            var tmpASPProfile: ASPProfile = if (ASPProfile.activeProfile == null) {
                ASPProfile(
                    "", "", "", "",
                    "", "", ""
                )
            } else {
                ASPProfile.activeProfile!!.copy()
            }
            currentASP = ASP(
                tmpASPProfile, "Kreiszugehörigkeit", "", "", "", "",
                "", "Fundort ist ...", "Geschlecht", "Fallenfang",
                "Alter", "Untersuchungsmaterial", "Verwesungsgrad des Tierkörpers",
                "Abgangsursache"
            )
        }

        fun saveCurrentASP(){
            ASPs.add(currentASP)
        }
    }
}

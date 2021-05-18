fun keyForContinuousNode(jsonElement: JsonElement, forTree: Boolean ?= true): String {
    return if (jsonElement.key != null) {
        if (forTree == true) {
            "\"" + jsonElement.key + "\""
        }else{
            "\"" + jsonElement.key + "\": "
        }
    } else {
        if (forTree == true)
            jsonElement.toString()
        else
            ""
    }
}
fun keyForEndNode(jsonElement: JsonElement): String {
    return if (jsonElement.key != null) {
        "\"" + jsonElement.key + "\": "
    } else {
        ""
    }
}
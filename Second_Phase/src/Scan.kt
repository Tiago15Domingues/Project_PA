fun giveTabs(depth: Int): String {
    var tab = ""
    for (i in 0 until depth) {
        tab += "\t"
    }
    return tab
}
fun giveTextualToEndLineJsonElement(elementJSON: JsonElement,textualJSON:String,depth: Int,textual:String): String {
    var jsonTextual = textualJSON
    val parent = elementJSON.parent
    jsonTextual += if (elementJSON.key != null) {
        giveTabs(depth) + "\"" + elementJSON.key + "\": " + textual
    }else {
        giveTabs(depth) + textual
    }
    jsonTextual += if(parent is JsonObject && parent.jsonObjectContent[parent.jsonObjectContent.size-1] != elementJSON || parent is JsonArray && parent.jsonArrayContent[parent.jsonArrayContent.size-1] != elementJSON){
        ",\n"
    }else {
        "\n"
    }
    return jsonTextual
}
fun giveEndTextualToContinuosLineJsonElement(elementJSON: JsonElement,textualJSON:String,depth: Int): String {
    val keyways = if (elementJSON is JsonObject){"}"}else{"]"}
    val parent = elementJSON.parent
    var jsonTextual = textualJSON
    jsonTextual += if (parent is JsonObject && parent.jsonObjectContent[parent.jsonObjectContent.size-1] != elementJSON || parent is JsonArray && parent.jsonArrayContent[parent.jsonArrayContent.size-1] != elementJSON) {
        giveTabs(depth-1) + "$keyways,\n"
    } else {
        if(parent == null){
            giveTabs(depth-1) + "$keyways"
        }else {
            giveTabs(depth - 1) + "$keyways\n"
        }
    }
    return jsonTextual
}
fun giveStartTextualToContinuosLineJsonElement(elementJSON: JsonElement,textualJSON:String,depth: Int): String {
    val keyways = if (elementJSON is JsonObject){"{"}else{"["}
    var jsonTextual = textualJSON
    jsonTextual += if (elementJSON.key != null) {
        giveTabs(depth) + "\"" + elementJSON.key + "\": " + "$keyways\n"
    }else{
        giveTabs(depth) + "$keyways\n"
    }
    return jsonTextual
}
fun passJsonObjectToTextual(objectJson: JsonElement): String {

    val toTextual = object : Visitor {
        var jsonTextual = ""
        var depth = 0
        override fun visit(o: JsonObject): Boolean {
            jsonTextual = giveStartTextualToContinuosLineJsonElement(o,jsonTextual,depth)
            depth++
            return true
        }
        override fun endvisitObject(o: JsonObject) {
            jsonTextual = giveEndTextualToContinuosLineJsonElement(o,jsonTextual,depth)
            depth--
        }
        override fun visit(a: JsonArray): Boolean {
            jsonTextual = giveStartTextualToContinuosLineJsonElement(a,jsonTextual,depth)
            depth++
            return true
        }
        override fun endvisitArray(o: JsonArray) {
            jsonTextual = giveEndTextualToContinuosLineJsonElement(o,jsonTextual,depth)
            depth--
        }
        override fun visit(s: JsonString) {
            jsonTextual = giveTextualToEndLineJsonElement(s, jsonTextual,depth,"\"" + s.value +"\"")
        }
        override fun visit(b: JsonBoolean) {
            jsonTextual = giveTextualToEndLineJsonElement(b, jsonTextual,depth,b.value.toString())
        }
        override fun visit(n: JsonNull) {
            jsonTextual = giveTextualToEndLineJsonElement(n, jsonTextual,depth,n.value.toString())
        }
        override fun visit(i: JsonNumber) {
            jsonTextual = giveTextualToEndLineJsonElement(i, jsonTextual,depth,i.value.toString())
        }
    }
    objectJson.accept(toTextual)

    return toTextual.jsonTextual
}
fun findStrings(objectJson: JsonElement): MutableList<String> {
    val findStrings = object : Visitor {
        var results = mutableListOf<String>()
        override fun visit(s: JsonString) {
            if (!results.contains(s.value))
                results.add(s.value)
        }
    }
    objectJson.accept(findStrings)
    println("All Strings -> " + findStrings.results)
    return findStrings.results
}
fun findObjectWithSpecificString(objectJson: JsonElement): MutableList<JsonElement> {
    val findObjectWithSpecificString = object : Visitor {
        var resultsToPrint = mutableListOf<Pair<String,JsonElement>>()
        var results = mutableListOf<JsonElement>()
        var obj = objectJson
        var objectKey : String? = ""
        var stringKey : String? = ""
        override fun visit(o: JsonObject): Boolean {
            obj = o
            return true
        }
        override fun visit(s: JsonString) {
            println(obj)
            if(s.value == "Peka") {
                if (!results.contains(obj)) {
                    println(results)
                    results.add(obj)
                    println(results)
                    if (s.key != null) stringKey = s.key
                    if (obj.key != null) objectKey = obj.key
                    resultsToPrint.add(Pair("$objectKey | $stringKey", obj))
                }
            }
        }
    }
    println(findObjectWithSpecificString.results)
    objectJson.accept(findObjectWithSpecificString)
    println(findObjectWithSpecificString.results)
    val resultObjectList = findObjectWithSpecificString.resultsToPrint[0].second as JsonObject
    println("All object where is a String 'Peka' and their keys (Object | String) -> $resultObjectList")
    return findObjectWithSpecificString.results
}

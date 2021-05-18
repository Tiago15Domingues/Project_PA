fun giveTabs(depthOfJsonElement: Int): String {
    var tab = ""
    for (i in 0 until depthOfJsonElement) {
        tab += "\t"
    }
    return tab
}
fun giveTextualToEndLineJsonElement(elementJSON: JsonElement, rootTextualJsonElement: String, depthOfJsonElement: Int, JsonElementInQuestion:String): String {
    var jsonTextual = rootTextualJsonElement
    val parent = elementJSON.parent
    jsonTextual +=  giveTabs(depthOfJsonElement) + keyForEndNode(elementJSON) + JsonElementInQuestion
    jsonTextual += if(depthOfJsonElement != 0 && (parent is JsonObject && parent.jsonObjectContent[parent.jsonObjectContent.size-1] != elementJSON || parent is JsonArray && parent.jsonArrayContent[parent.jsonArrayContent.size-1] != elementJSON)){
        ",\n"
    }else {
        if(depthOfJsonElement != 0)
            "\n"
        else
            ""
    }
    return jsonTextual
}
fun giveEndTextualToContinuosLineJsonElement(elementJSON: JsonElement, rootTextualJsonElement: String, depthOfJsonElement: Int): String {
    val keyways = if (elementJSON is JsonObject){"}"}else{"]"}
    val parent = elementJSON.parent
    var jsonTextual = rootTextualJsonElement
    jsonTextual += if ((depthOfJsonElement != 1 && parent is JsonObject && parent.jsonObjectContent[parent.jsonObjectContent.size-1] != elementJSON) || (parent is JsonArray && parent.jsonArrayContent[parent.jsonArrayContent.size-1] != elementJSON && depthOfJsonElement != 1)) {
        giveTabs(depthOfJsonElement-1) + "$keyways,\n"
    } else {
        if(parent == null || depthOfJsonElement == 1){
            giveTabs(depthOfJsonElement-1) + keyways
        }else {
            giveTabs(depthOfJsonElement - 1) + "$keyways\n"
        }
    }
    return jsonTextual
}
fun giveStartTextualToContinuosLineJsonElement(elementJSON: JsonElement, rootTextualJsonElement:String, depthOfJsonElement: Int): String {
    val keyways = if (elementJSON is JsonObject){"{"}else{"["}
    var jsonTextual = rootTextualJsonElement
    jsonTextual += giveTabs(depthOfJsonElement) + keyForContinuousNode(elementJSON,false) + "$keyways\n"
    return jsonTextual
}
fun passJsonElementToTextual(objectJson: JsonElement): String {

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
        override fun endvisitArray(a: JsonArray) {
            jsonTextual = giveEndTextualToContinuosLineJsonElement(a,jsonTextual,depth)
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

fun findAllStrings(objectJson: JsonElement): MutableList<String> {
    val findStrings = object : Visitor {
        var results = mutableListOf<String>()
        override fun visit(s: JsonString) {
            if (!results.contains(s.value))
                results.add(s.value)
        }
    }
    objectJson.accept(findStrings)
    println("All Strings -> " + findStrings.results) //Just to make sure everything is right (not necessary)
    return findStrings.results
}
fun findJsonObjectWithSpecificString(objectJson: JsonElement,string: String): MutableList<JsonElement> {
    val findObjectWithSpecificString = object : Visitor {
        var resultsToPrint = mutableListOf<Pair<String,JsonElement>>()
        var results = mutableListOf<JsonElement>() //Just to make sure everything is right (not necessary)
        var obj = objectJson
        var objectKey : String? = "" //Just to make sure everything is right (not necessary)
        var stringKey : String? = "" //Just to make sure everything is right (not necessary)
        override fun visit(o: JsonObject): Boolean {
            obj = o
            return true
        }
        override fun visit(s: JsonString) {
            if(s.value == string) {
                if (!results.contains(obj)) {
                    results.add(obj)
                    if (s.key != null) stringKey = s.key //Just to make sure everything is right (not necessary)
                    if (obj.key != null) objectKey = obj.key //Just to make sure everything is right (not necessary)
                    resultsToPrint.add(Pair("$objectKey | $stringKey", obj)) //Just to make sure everything is right (not necessary)
                }
            }
        }
    }
    objectJson.accept(findObjectWithSpecificString)
    val resultObjectList = findObjectWithSpecificString.resultsToPrint[0].second as JsonObject //Apenas para uma questão de prints e verificar se bate tudo certo
    println("All object where is a String '$string' and their keys (Object | String) -> $resultObjectList") //Apenas para uma questão de prints e verificar se bate tudo certo
    return findObjectWithSpecificString.results
}
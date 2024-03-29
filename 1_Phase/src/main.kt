fun main(){
    val jsonObject = JsonObject()
    val jsonNumber1 = JsonNumber(123123)
    val jsonString1 = JsonString("Tiago")
    val jsonNull1 = JsonNull(null)
    val jsonBool1 = JsonBoolean(true)

    jsonObject.setProperty("id",jsonNumber1)
    jsonObject.setProperty("name",jsonString1)
    jsonObject.setProperty("male", jsonBool1)
    jsonObject.setProperty("smart", jsonNull1)

    val jsonObject4 = JsonObject()
    val jsonNumber2 = JsonNumber(10)
    val jsonString2 = JsonString("18")
    jsonObject4.setProperty("PA",jsonNumber2)
    jsonObject4.setProperty("IAA",jsonString2)

    val jsonObject6 = JsonObject()
    val jsonNumber3 = JsonNumber(30)
    val jsonArray = JsonArray(arrayOf(jsonNumber3,jsonObject4))
    println(passJsonElementToTextual(jsonArray))
    jsonObject6.setProperty("NOTAS PA", jsonArray)
    jsonObject6.setProperty("NOTA PROG",jsonObject4)

    val jsonObject5 = JsonObject()
    val jsonNumber4 = JsonNumber(19)
    jsonObject5.setProperty("PCD",jsonNumber4)
    jsonObject5.setProperty("NOTAS",jsonObject4)

    val jsonBool2 = JsonBoolean(true)
    val jsonNull2 = JsonNull(null)
    val jsonString3 = JsonString("PASS")
    val jsonArray1 = JsonArray(arrayOf(jsonObject5,jsonNull2))
    val jsonArray2 = JsonArray(arrayOf(jsonString3,jsonObject6,jsonArray1,jsonBool2,jsonObject4))
    jsonObject.setProperty("grades", jsonArray2)

    val jsonObject12 = JsonObject()
    val jsonString4 = JsonString("MirandaDoDouro")
    jsonObject12.setProperty("town",jsonString4)
    jsonObject12.setProperty("born",jsonString4)

    val jsonObject1 = JsonObject()
    val jsonString5 = JsonString("Peka")
    jsonObject1.setProperty("animalID",jsonNumber1)
    jsonObject1.setProperty("name",jsonString5)
    jsonObject1.setProperty("petHome",jsonObject12)

    jsonObject.setProperty("pet",jsonObject1)

    val jsonObject3 = JsonObject()
    val jsonString6 = JsonString("Almada")
    jsonObject3.setProperty("town",jsonString6)
    jsonObject3.setProperty("born",jsonString6)
    jsonObject.setProperty("home",jsonObject3)

    val jsonObject2 = JsonObject()
    jsonObject2.setProperty("MEI_Student",jsonObject)

    findAllStrings(jsonObject)
    findJsonObjectWithSpecificString(jsonObject,"Peka")
    print(passJsonElementToTextual(jsonObject) + "\n")
    findAllStrings(jsonObject2)
    findJsonObjectWithSpecificString(jsonObject2,"Peka")
    println(passJsonElementToTextual(jsonObject2))

    val jo = JsonObject()
    val jn = JsonNumber(123)
    jo.setProperty("KeyFor 'jn'",jn)
    val js = JsonString("Test")
    val jb = JsonBoolean(true)
    val jo2 = JsonObject()
    val jN = JsonNull(null)
    jo2.setProperty("KeyFor 'jN'",jN)
    val ja = JsonArray(arrayOf(js,jb,jN,jo2))
    jo.setProperty("KeyFor 'ja'",ja)

    println(passJsonElementToTextual(jo))



}
fun passJsonElementToTextual(objectJson: JsonElement): String {
    fun giveTabs(depthOfJsonElement: Int): String {
        var tab = ""
        for (i in 0 until depthOfJsonElement) {
            tab += "\t"
        }
        return tab
    }
    fun giveTextualToEndLineJsonElement(elementJSON: JsonElement, rootTextualJsonElement: String, depthOfJsonElement: Int, JsonElementValue:String): String {
        var jsonTextual = rootTextualJsonElement
        val parent = elementJSON.parent
        jsonTextual +=  giveTabs(depthOfJsonElement) + elementJSON.keyToShow(false) + JsonElementValue
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
        jsonTextual += giveTabs(depthOfJsonElement) + elementJSON.keyToShow(false) + "$keyways\n"
        return jsonTextual
    }

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
    println("First object where is a String '$string' -> $resultObjectList") //Apenas para uma questão de prints e verificar se bate tudo certo
    return findObjectWithSpecificString.results
}
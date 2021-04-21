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
    jsonTextual += if(parent is JsonObject)
        textual
    else
        giveTabs(depth) + textual
    jsonTextual += if(parent is JsonObject && parent.jsonObjectContent[parent.jsonObjectContent.size-1].second != elementJSON || parent is JsonArray && parent.jsonArrayContent[parent.jsonArrayContent.size-1] != elementJSON){
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
    jsonTextual += if (parent is JsonObject && parent.jsonObjectContent[parent.jsonObjectContent.size-1].second != elementJSON || parent is JsonArray && parent.jsonArrayContent[parent.jsonArrayContent.size-1] != elementJSON) {
        giveTabs(depth-1) + "$keyways,\n"
    } else {
        giveTabs(depth-1) + "$keyways\n"
    }
    return jsonTextual
}
fun giveStartTextualToContinuosLineJsonElement(elementJSON: JsonElement,textualJSON:String,depth: Int): String {
    val keyways = if (elementJSON is JsonObject){"{"}else{"["}
    var jsonTextual = textualJSON
    jsonTextual += if (elementJSON.parent is JsonArray){
        giveTabs(depth) + "$keyways\n"
    }else{
        "$keyways\n"
    }
    return jsonTextual
}

fun passJsonObjectToTextual(objectJson: JsonObject): String {

    val toTextual = object : Visitor {
        var jsonTextual = ""
        var depth = 0
        override fun visit(o: JsonObject) {
            jsonTextual = giveStartTextualToContinuosLineJsonElement(o,jsonTextual,depth)
            depth++
            o.jsonObjectContent.forEach {
                jsonTextual += giveTabs(depth) + "\"" + it.first + "\": "
                it.second.accept(this)
            }
            jsonTextual = giveEndTextualToContinuosLineJsonElement(o,jsonTextual,depth)
            depth--
        }

        override fun visit(a: JsonArray) {
            jsonTextual = giveStartTextualToContinuosLineJsonElement(a,jsonTextual,depth)
            depth++
            a.jsonArrayContent.forEach {
                it.accept(this)
            }
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
    val findStrings = object : Visitor {
        var results = mutableListOf<String>()
        var newString = ""
        override fun visit(o: JsonObject) {
            o.jsonObjectContent.forEach {
                newString = "\"" + it.first + "\": "
                it.second.accept(this)
            }
        }

        override fun visit(a: JsonArray) {
            newString = ""
            a.jsonArrayContent.forEach {
                it.accept(this)
            }
        }

        override fun visit(s: JsonString) {
            results.add(newString + "\"" + s.value + "\"")
        }
    }
    val findObjectWithSpecificString = object : Visitor {
        var results = mutableListOf<Pair<String,JsonObject>>()
        var obj = JsonObject()
        var objectKey = ""
        var stringKey = ""
        override fun visit(o: JsonObject) {
            obj = o
            o.jsonObjectContent.forEach {
                if (it.second is JsonObject)
                    objectKey = it.first
                stringKey = it.first
                it.second.accept(this)
            }
        }

        override fun visit(a: JsonArray) {
            a.jsonArrayContent.forEach {
                it.accept(this)
            }
        }

        override fun visit(s: JsonString) {
            if(s.value == "Peka")
                results.add(Pair("$objectKey | $stringKey",obj))
        }
    }

    objectJson.accept(toTextual)
    objectJson.accept(findStrings)
    objectJson.accept(findObjectWithSpecificString)

    println("All object Strings and their keys -> " + findStrings.results)
    println("All object where is a String 'Peka' and their keys (Object | String) -> " + findObjectWithSpecificString.results/*[0].jsonObjectContent*/)

    return toTextual.jsonTextual
}


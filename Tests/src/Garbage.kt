/*fun searchInside(valueJ: Any,jsonObjToFind: JsonElement): Boolean {
    var toFindInsideObj = false
    valueJ as MutableList<Pair<Any,Any>>
    valueJ.forEach{
        if(it.second.toString() == jsonObjToFind.toString()){
            toFindInsideObj = true
        }else{
            if(it.second.toString() == JsonObject().toString() || it.second.toString() == JsonArray(arrayOf(0,0)).toString()){
                toFindInsideObj = true
            }
        }
    }
    return toFindInsideObj
}*/

/*override fun visit(c: JsonElement) {
            var valueJ = c.aux
            if (c.toString() == jsonObjToFind.toString() && c.toString() != JsonObject().toString() && c.toString() != JsonArray(arrayOf()).toString()){
                objectValues.add(valueJ as String)
            }else{
                var toFindInsideObj = searchInside(valueJ,jsonObjToFind)
                println(toFindInsideObj)
                if(toFindInsideObj){
                    if(jsonObjToFind.toString() == JsonObject().toString()) {
                        objectValues.add(jsonObjectFind(objectJson = c as JsonObject,jsonObjToFind,auxVar+1,inArray = inArray))
                    }else {
                        valueJ as MutableList<Pair<Any,Any>>
                        valueJ.clear()
                        c as JsonArray
                        c.valAux.forEach {
                            c.confirmProperty(value = it)
                        }
                        objectValues.add(jsonObjectFind(objectJson,jsonObjToFind,auxVar+1,c,true))
                    }
                }
            }
        }*/
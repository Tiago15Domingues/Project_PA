fun main(){
    println(passJsonElementToTextual(getJSON(studentMaleiro) as JsonObject))
    println(passJsonElementToTextual((getJSON(mutableListOf(studentMaleiro,studentAlfredo)) as JsonArray).value[0]))
}



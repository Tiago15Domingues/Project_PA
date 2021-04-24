import java.util.*
import javax.lang.model.type.NullType

fun main(){

    var value = mutableListOf<Any>()
    value.add(13)
    println(value.toString().replace("[", "").replace("]", ""))
    //println(t1 is )
    val jsonObject = JsonObject()
    val jsonObject1 = JsonObject()
    var s = jsonObject
    s = jsonObject1
    println(jsonObject)
    println(s)
    println(jsonObject1)

}
class JsonObject{
    var jsonElementContent = mutableListOf<JsonObjectProp>()
    fun setProperty(key: String, value: JsonNumber) {
        var jsono = JsonObjectProp(key,value)
        jsonElementContent.add(jsono)
    }
}
class JsonObjectProp(key: String,value: JsonNumber){
    var KEYS = ""
    var Values = ""
    fun setProperty(key: String, value: JsonNumber) {
        KEYS = key
        Values = value.toString()
    }
}

class JsonNumber(value: Number) { // 1
    var jsonElementContent = value
    var serializeJsonElementToTextContent = value.toString()
}
class test(valuer: LinkedList<Int>){
    var aux = valuer
}
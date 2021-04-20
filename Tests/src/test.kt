import java.util.*
import javax.lang.model.type.NullType

fun main(){

    var value = mutableListOf<Any>()
    value.add(13)
    println(value.toString().replace("[", "").replace("]", ""))
    //println(t1 is )
    val jsonObject = JsonObject()
    val jsonNumber1 = JsonNumber(123123)
    jsonObject.setProperty("id",jsonNumber1)
    jsonObject.setProperty("id",jsonNumber1)
    println(jsonObject.jsonElementContent)
    val jsonObject1 = jsonObject::class
    println(jsonObject1.objectInstance?.jsonElementContent)

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
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties

fun main(){
    println(passJsonElementToTextual(getJSON(studentMaleiro) as JsonObject))
    println(getJSONList(mutableListOf(studentMaleiro,studentAlfredo)))
}
fun getJSON(c:Any): JsonElement {
    val clazz: KClass<Any> = c::class as KClass<Any>
    val jsonObject = JsonObject()
    clazz.declaredMemberProperties.forEach {
        jsonObject.setProperty(it.name,mapObject(it.returnType.classifier,it.call(c)))
    }
    return jsonObject
}
fun getJSONList(c:MutableList<Any>): MutableList<JsonElement> {
    val jsonObjectList = mutableListOf<JsonElement>()
    c.forEach { it1->
        jsonObjectList.add(getJSON(it1))
    }
    return jsonObjectList
}


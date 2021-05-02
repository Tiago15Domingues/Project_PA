import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation

fun main(){
    println(passJsonElementToTextual(getJSON(studentMaleiro) as JsonObject))
    println(passJsonElementToTextual((getJSON(mutableListOf(studentMaleiro,studentAlfredo)) as JsonArray).value[0]))
}
fun getJSON(c:Any): Any {
    return if (c is MutableList<*>) {
        val jsonArray = mutableListOf<JsonElement>()
        c.forEach { it1 ->
            jsonArray.add(getJSON(it1!!) as JsonElement)
        }
        JsonArray(jsonArray.toTypedArray())
    } else {
        if (c::class.isData) {
            val clazz: KClass<Any> = c::class as KClass<Any>
            val jsonObject = JsonObject()
            clazz.declaredMemberProperties.forEach {
                if (it.hasAnnotation<NKey>()) {
                    jsonObject.setProperty(it.findAnnotation<NKey>()?.value!!, mapObject(it.returnType.classifier, it.call(c)))
                } else {
                    if (!it.hasAnnotation<Exclude>()) {
                        jsonObject.setProperty(it.name, mapObject(it.returnType.classifier, it.call(c)))
                    }
                }
            }
            jsonObject
        } else {
            throw IllegalArgumentException("Only Class´s or list of Class")
        }
    }
}


import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties

fun main(){
    println(passJsonElementToTextual(getJSON(studentMaleiro) as JsonObject))
    println(passJsonElementToTextual((getJSON(mutableListOf(studentMaleiro,studentAlfredo)) as MutableList<JsonElement>)[0]))
}
fun getJSON(c:Any): Any {
    return if (c is MutableList<*>){
        val jsonArray = mutableListOf<JsonElement>()
        c.forEach { it1->
            jsonArray.add(getJSON(it1!!) as JsonElement)
        }
        JsonArray(jsonArray.toTypedArray())
    }else{
        if (c::class.isData) {
            val clazz: KClass<Any> = c::class as KClass<Any>
            val jsonObject = JsonObject()
            clazz.declaredMemberProperties.forEach {
                jsonObject.setProperty(it.name, mapObject(it.returnType.classifier, it.call(c)))
            }
            jsonObject
        }else{
            throw IllegalArgumentException("Only Class´s or list of Class")
        }
    }
}


import kotlin.reflect.KClass
import kotlin.reflect.KClassifier
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.ClassData

fun main(){
    println(passJsonElementToTextual(getJSON(studentMaleiro) as JsonObject))
    println(passJsonElementToTextual((getJSON(mutableListOf(studentMaleiro,studentAlfredo)) as JsonArray).value[0]))
}

fun getJSON(c:Any): Any {
    fun mapObject(o: KClassifier?, call: Any?): JsonElement {
        var type : JsonElement = JsonNull(null)
        if (call != null){
            if(o == String::class || o == Enum::class){
                type = JsonString(call.toString())
            }else {
                if (o == Int::class) {
                    type = JsonNumber(call as Number)
                } else {
                    if (o == Boolean::class) {
                        type = JsonBoolean(call as Boolean)
                    } else {
                        if (o == Collection::class) {
                            val jsonArray = mutableListOf<JsonElement>()
                            (call as Collection<*>).toMutableList().forEach {
                                jsonArray.add(mapObject(it!!.javaClass.kotlin,it))
                            }
                            type = JsonArray(jsonArray.toTypedArray())
                        } else {
                            if (o == HashMap::class) {
                                val jsonObject = JsonObject()
                                (call as HashMap<*, *>).entries.forEach {
                                    jsonObject.setProperty(it.key.toString(),mapObject(it.key.javaClass.kotlin,it.value))
                                }
                                type = jsonObject
                            } else {
                                if (o == Student::class) {
                                    type = getJSON(call) as JsonElement
                                }
                            }
                        }
                    }
                }
            }
        }else{
            type = JsonNull(call)
        }
        return type
    }

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


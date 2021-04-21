import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties

data class Student(
    val number: Int,
    val name: String,
    val repeting: Boolean,
    val bestGrades: Collection<Int>,
    val activities: HashMap<String, String>?,
    val bestFriend: Student?,
    val studentType: Enum<StudentType>
)

interface TypeMapping {
    fun mapObject(o: KProperty1<*, *>, c1: Any): JsonElement
}

class JSON : TypeMapping {
    override fun mapObject(o: KProperty1<*, *>, c: Any): JsonElement {
        var type : JsonElement = JsonNull(null)
        if(o.returnType.classifier.toString().contains("String") || o.returnType.classifier.toString().contains("Enum")){
            type = if(o.call(c) != null)
                JsonString(o.call(c).toString())
            else
                JsonNull(null)
        }
        if(o.returnType.classifier.toString().contains("Int")){
            type = if(o.call(c) != null)
                JsonNumber(o.call(c) as Number)
            else
                JsonNull(null)
        }
        if(o.returnType.classifier.toString().contains("Boolean")){
            type = if(o.call(c) != null)
                JsonBoolean(o.call(c) as Boolean)
            else
                JsonNull(null)
        }
        if(o.returnType.classifier.toString().contains("Collection")){
            type = JsonNull(null)
        }
        if(o.returnType.classifier.toString().contains("HashMap")){
            val p = o.call(c)
            p as HashMap<Any,Any>
            val clazz: KClass<Any> = p::class as KClass<Any>
            println(p)
            clazz.declaredMemberProperties.forEach {
                if(it.toString().contains("values"))
                    println(it.call(p))
                if(it.toString().contains("key"))
                    println(it.call(p))
            }
        }
        if(o.returnType.classifier.toString().contains("Student")){
            type = if(o.call(c) != null)
                returnFun(o.call(c)!!,JSON())
            else
                JsonNull(null)
        }
        return type
    }
}

fun returnFun(c:Any,typeMapping: TypeMapping): JsonElement {
    val clazz: KClass<Any> = c::class as KClass<Any>
    val jsonObject = JsonObject()
    clazz.declaredMemberProperties.forEach {
        jsonObject.setProperty(it.name,typeMapping.mapObject(it,c))
    }
    return jsonObject
}

enum class StudentType {Bachelor, Master, Doctoral}
var studentZeca : Student = Student(8000,"Zeca",true, setOf(12,14), hashMapOf("Indoor" to "Darts","Outside" to "Cross Country"),null,StudentType.Master)
var studentAlfredo : Student = Student(83605,"Alfredo",false, setOf(19,18), hashMapOf("Indoor" to "Chest","Outside" to "Football"),studentZeca,StudentType.Master)




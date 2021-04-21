import kotlin.reflect.KClass
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
enum class StudentType {Bachelor, Master, Doctoral}
var studentZeca : Student = Student(8000,"Zeca",true, setOf(12,14), hashMapOf("Indoor" to "Darts","Outside" to "Cross Country"),null,StudentType.Master)
var studentAlfredo : Student = Student(83605,"Alfredo",false, setOf(19,18), hashMapOf("Indoor" to "Chest","Outside" to "Football"),studentZeca,StudentType.Master)

fun getTypes(c: Any){
    val jsonObject = JsonObject()
    val clazz: KClass<Any> = c::class as KClass<Any>
    clazz.declaredMemberProperties.forEach {
        if(it.returnType.classifier.toString().contains("String")){
            jsonObject.setProperty(it.name,JsonString(it.call(c).toString()))
        }
        if(it.returnType.classifier.toString().contains("Int")){
            jsonObject.setProperty(it.name,JsonNumber(it.call(c) as Number))
        }
        if(it.returnType.classifier.toString().contains("Boolean")){
            jsonObject.setProperty(it.name,JsonBoolean(it.call(c) as Boolean))
        }
        if(it.returnType.classifier.toString().contains("Collection")){
        }
        if(it.returnType.classifier.toString().contains("HashMap")){

        }
        if(it.returnType.classifier.toString().contains("Student")){

        }
        if(it.returnType.classifier.toString().contains("Enum")){
        }
    }
   println(passJsonObjectToTextual(jsonObject))
}
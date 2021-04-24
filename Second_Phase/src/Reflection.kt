import kotlin.reflect.KClassifier

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
                                type = getJSON(call)
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
data class Student(
        val number: Int?,
        val name: String?,
        val repeting: Boolean?,
        val bestGrades: Collection<Any>?,
        val activities: HashMap<String, String>?,
        val bestFriend: Student?,
        val studentType: Enum<StudentType>?
)
enum class StudentType {Bachelor, Master, Doctoral}
var studentZeca : Student = Student(8000,"Zeca",true, setOf(12,14), hashMapOf("Indoor" to "Darts","Outside" to "Cross Country"),null,StudentType.Doctoral)
var studentAlfredo : Student = Student(83605,"Alfredo",false, setOf(19,18), hashMapOf("Indoor" to "Chest","Outside" to "Football"),studentZeca,StudentType.Bachelor)
var studentMaleiro : Student = Student(83605,"Maleiro",false, setOf(studentZeca,studentAlfredo), hashMapOf("Indoor" to "Chest","Outside" to "Football"),studentZeca,StudentType.Master)




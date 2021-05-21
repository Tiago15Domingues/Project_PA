import kotlin.reflect.KClassifier

@Target(AnnotationTarget.PROPERTY)
annotation class NKey(val value: String)

@Target(AnnotationTarget.PROPERTY)
annotation class Exclude

data class Student(
        @Exclude
        val number: Int?,
        @NKey("studentName")
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
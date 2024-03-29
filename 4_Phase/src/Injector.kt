import java.io.File
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.jvm.isAccessible

@Target(AnnotationTarget.PROPERTY)
annotation class InjectApresentation

@Target(AnnotationTarget.PROPERTY)
annotation class InjectAction

class Injector {
    companion object {
        private val map: MutableMap<String, MutableList<KClass<*>>> = mutableMapOf()

        init {
            val scanner = Scanner(File("di.properties"))
            while(scanner.hasNextLine()){
                val line = scanner.nextLine()
                val parts = line.split("=")
                val classList:MutableList<KClass<*>> = mutableListOf()
                if(parts[1].contains(",")){
                    val partsR = parts[1].split(",")
                    partsR.forEach {
                        classList.add(Class.forName(it).kotlin)
                    }
                    map[parts[0]] = classList
                }else{
                    if (parts[1] != "")
                        try {
                            classList.add(Class.forName(parts[1]).kotlin)
                            map[parts[0]] = classList
                        } catch (e: ClassNotFoundException) {
                            throw ClassNotFoundException("In the configuration file, place only classes that use the \"Frame SetUp\" (place in \"Uijson.setup\") or \"Action\"(place in \"Uijson.actions\") interface")
                        }
                }
            }
            scanner.close()
        }

        fun <T:Any> create(type: KClass<T>) : T {
            val o: T = type.createInstance()
            type.declaredMemberProperties.forEach {
                if(it.hasAnnotation<InjectApresentation>()) {
                    it.isAccessible = true
                    val key = type.simpleName + "." + it.name
                    if (map[key] != null && map[key]!!.isNotEmpty()) {
                        val obj = map[key]!![0].createInstance()
                        (it as KMutableProperty<*>).setter.call(o, obj)
                    }else{
                        (it as KMutableProperty<*>).setter.call(o, null)
                    }
                }
                if(it.hasAnnotation<InjectAction>()){
                    it.isAccessible = true
                    val key = type.simpleName + "." + it.name
                    val classList = it.getter.call(o) as MutableList<Any>
                    map[key]?.forEach { it1 ->
                        val obj = it1.createInstance()
                        classList.add(obj)
                    }
                }
            }
            return o
        }
    }
}
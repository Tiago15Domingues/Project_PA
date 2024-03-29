import javax.lang.model.type.NullType

interface Visitor {
    fun visit(s: JsonString) {}
    fun visit(o: JsonObject) : Boolean = true
    fun endvisitObject(o: JsonObject) {}
    fun visit(a: JsonArray) : Boolean = true
    fun endvisitArray(a: JsonArray) {}
    fun visit(b: JsonBoolean) {}
    fun visit(n: JsonNull) {}
    fun visit(i: JsonNumber) {}
}

abstract class JsonElement {
    var parent: JsonElement? = null
    var key: String? = null
    abstract fun accept(v: Visitor)
}


class JsonObject : JsonElement() { //{ "name":"John", "age":30, "car":null }
    var jsonObjectContent = mutableListOf<JsonElement>()

    private fun setPropertyForArrayElement(value: JsonElement, array: JsonArray) {
        val newjsonElement: JsonElement?
        when (value) {
            is JsonObject -> {
                newjsonElement = JsonObject()
                value.jsonObjectContent.forEach { newjsonElement.setProperty(it.key!!,it) }
            }
            is JsonArray -> {
                newjsonElement = JsonArray(value.value)
                newjsonElement.value.forEach { setPropertyForArrayElement(it, newjsonElement) }
            }
            is JsonString -> {
                newjsonElement = JsonString(value.value)
            }
            is JsonBoolean -> newjsonElement = JsonBoolean(value.value)
            is JsonNumber -> newjsonElement = JsonNumber(value.value)
            is JsonNull -> newjsonElement = JsonNull(value.value)
            else -> newjsonElement = null
        }
        newjsonElement!!.parent = array
        array.jsonArrayContent.add(newjsonElement)
    }

    fun setProperty(key: String, jsonElement: JsonElement) {
        val newjsonElement: JsonElement?
        when (jsonElement) {
            is JsonObject -> {
                newjsonElement = JsonObject()
                jsonElement.jsonObjectContent.forEach { newjsonElement.setProperty(it.key!!,it) }
            }
            is JsonArray -> {
                newjsonElement = JsonArray(jsonElement.value)
                newjsonElement.value.forEach { setPropertyForArrayElement(it, newjsonElement) }
            }
            is JsonString -> {
                newjsonElement = JsonString(jsonElement.value)
            }
            is JsonBoolean -> newjsonElement = JsonBoolean(jsonElement.value)
            is JsonNumber -> newjsonElement = JsonNumber(jsonElement.value)
            is JsonNull -> newjsonElement = JsonNull(jsonElement.value)
            else -> newjsonElement = null
        }
        newjsonElement!!.parent = this
        newjsonElement.key = key
        this.jsonObjectContent.add(newjsonElement)
    }

    override fun accept(v: Visitor) {
        if(v.visit(this)){
            jsonObjectContent.forEach {
                it.accept(v)
            }
            v.endvisitObject(this)
        }
    }
}

class JsonArray(var value: Array<JsonElement>): JsonElement() { //{a, b, c}
    var jsonArrayContent = mutableListOf<JsonElement>()

    override fun accept(v: Visitor) {
        if (v.visit(this)) {
            jsonArrayContent.forEach {
                it.accept(v)
            }
            v.endvisitArray(this)
        }
    }
}

class JsonString(var value: String): JsonElement() { //"test"
    override fun accept(v: Visitor) {
        v.visit(this)
    }
}

class JsonNumber(var value: Number): JsonElement() { // 1
    override fun accept(v: Visitor) {
        v.visit(this)
    }
}

class JsonBoolean(var value: Boolean): JsonElement() { // "true/false"

    override fun accept(v: Visitor) {
        v.visit(this)
    }
}

class JsonNull(var value: NullType?): JsonElement() { // "null"

    override fun accept(v: Visitor) {
        v.visit(this)
    }
}
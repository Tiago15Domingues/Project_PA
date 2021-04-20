import javax.lang.model.type.NullType

interface Visitor {
    fun visit(s: JsonString) {}
    fun visit(o: JsonObject) {}
    fun visit(a: JsonArray) {}
    fun visit(b: JsonBoolean) {}
    fun visit(n: JsonNull) {}
    fun visit(i: JsonNumber) {}
}

abstract class JsonElement {
    var parent: JsonElement? = null
    abstract fun accept(v: Visitor)
}

class JsonObject : JsonElement() { //{ "name":"John", "age":30, "car":null }
    var jsonObjectContent = mutableListOf<Pair<String,JsonElement>>()

    fun setProperty(key: String, jsonElement: JsonElement) {
        var newjsonElement: JsonElement? = null
        when (jsonElement) {
            is JsonObject -> {
                newjsonElement = JsonObject()
                newjsonElement.jsonObjectContent = jsonElement.jsonObjectContent
            }
            is JsonArray -> {
                newjsonElement = JsonArray(jsonElement.value)
                newjsonElement.value.forEach { (newjsonElement as JsonArray).confirmProperty(it) }
            }
            is JsonString -> newjsonElement = JsonString(jsonElement.value)
            is JsonBoolean -> newjsonElement = JsonBoolean(jsonElement.value)
            is JsonNumber -> newjsonElement = JsonNumber(jsonElement.value)
            is JsonNull -> newjsonElement = JsonNull(jsonElement.value)
        }
        newjsonElement!!.parent = this
        this.jsonObjectContent.add(Pair(key,newjsonElement))
    }

    override fun accept(v: Visitor) {
        v.visit(this)
    }
}

class JsonArray(val value: Array<JsonElement>): JsonElement() { //{a, b, c}
    var jsonArrayContent = mutableListOf<JsonElement>()

    fun confirmProperty(value: JsonElement) {
        var newjsonElement: JsonElement? = null
        when (value) {
            is JsonObject -> {
                newjsonElement = JsonObject()
                newjsonElement.jsonObjectContent = value.jsonObjectContent
            }
            is JsonArray -> {
                newjsonElement = JsonArray(value.value)
                newjsonElement.value.forEach { (newjsonElement as JsonArray).confirmProperty(it) }
            }
            is JsonString -> newjsonElement = JsonString(value.value)
            is JsonBoolean -> newjsonElement = JsonBoolean(value.value)
            is JsonNumber -> newjsonElement = JsonNumber(value.value)
            is JsonNull -> newjsonElement = JsonNull(value.value)
        }
        newjsonElement!!.parent = this
        this.jsonArrayContent.add(newjsonElement)
    }

    override fun accept(v: Visitor) {
        v.visit(this)
    }
}

class JsonString(val value: String): JsonElement() { //"test"
    override fun accept(v: Visitor) {
        v.visit(this)
    }
}

class JsonNumber(val value: Number): JsonElement() { // 1
    override fun accept(v: Visitor) {
        v.visit(this)
    }
}

class JsonBoolean(val value: Boolean): JsonElement() { // "true/false"

    override fun accept(v: Visitor) {
        v.visit(this)
    }
}

class JsonNull(val value: NullType?): JsonElement() { // "null"

    override fun accept(v: Visitor) {
        v.visit(this)
    }
}

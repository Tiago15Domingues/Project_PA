import java.io.File

fun JsonElement.keyToShow(forTree: Boolean ?= true): String {
    return if ((this is JsonArray || this is JsonObject) && forTree == true) {
        if (key != null) {
            "\"" + key + "\""
        } else {
            toString()
        }
    } else {
        if (key != null) {
            "\"$key\": "
        } else {
            ""
        }
    }
}

fun JsonElement.allJsonElementWithCertainKeyInsideContinuosNode(keyToSearch: String): MutableList<JsonElement>? {
    val res = mutableListOf<JsonElement>()
    if (this is JsonObject || this is JsonArray){
        if (this is JsonObject){
            jsonObjectContent.forEach {
                if (it.key == keyToSearch){
                    res.add(it)
                }
            }
        }else if (this is JsonArray){
            jsonArrayContent.forEach {
                if (it.key == keyToSearch){
                    res.add(it)
                }
            }
        }
        return if (res.isEmpty())
            null
        else
            res
    }else{
        throw IllegalArgumentException("JsonElement that call the function must be a JsonObject or a JsonArray")
    }
}

fun Uijson.getSelectedElementData(): JsonElement {
    return tree.selection.first().data as JsonElement
}

fun Uijson.updateTreeText(){
    tree.traverse {
        when (val jsonElementToUpdate = it.data as JsonElement) {
            is JsonObject, is JsonArray -> {
                it.text = setup?.changeText(jsonElementToUpdate) ?: jsonElementToUpdate.keyToShow()
            }
            is JsonString -> {
                it.text = setup?.changeText(jsonElementToUpdate) ?: jsonElementToUpdate.keyToShow() + "\"" + jsonElementToUpdate.value + "\""
            }
            is JsonNumber -> {
                it.text = setup?.changeText(jsonElementToUpdate) ?: jsonElementToUpdate.keyToShow() + jsonElementToUpdate.value.toString()
            }
            is JsonBoolean -> {
                it.text = setup?.changeText(jsonElementToUpdate) ?: jsonElementToUpdate.keyToShow() + jsonElementToUpdate.value.toString()
            }
            is JsonNull -> {
                it.text = setup?.changeText(jsonElementToUpdate) ?: jsonElementToUpdate.keyToShow() + jsonElementToUpdate.value.toString()
            }
        }
    }
}

fun autoRenameFile(firstFileName: String, path: String, suffix: String, toWrite: String) {
    fun auxFun(count: Int, firstFileName: String, path: String, suffix: String, toWrite: String) {
        val newFile = File(path + firstFileName.substring(0, firstFileName.length - suffix.length) + "($count)" + suffix)
        if (newFile.createNewFile()) {
            newFile.writeText(toWrite)
            println("File \"${newFile.name}\" created successfully")
        } else {
            auxFun(count + 1, firstFileName, path, suffix, toWrite)
        }
    }
    auxFun(1,firstFileName,path,suffix,toWrite)
}

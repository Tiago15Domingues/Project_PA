import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.TreeItem
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

fun JsonElement.firstJsonElementWithCertainKeyInsideContinuosNode(keyToSearch: String): JsonElement? {
    if (this is JsonObject || this is JsonArray){
        var je: JsonElement? = null
        if (this is JsonObject){
            jsonObjectContent.forEach {
                if (it.key == keyToSearch){
                    je = it
                    return@forEach
                }
            }
        }else if (this is JsonArray){
            jsonArrayContent.forEach {
                if (it.key == keyToSearch){
                    je = it
                    return@forEach
                }
            }
        }
        return je
    }else{
        throw IllegalArgumentException("First argument must be a JsonObject or a JsonArray")
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

fun Uijson.setParentInTree (allTreeParents: MutableList<TreeItem>): TreeItem {
    return if (allTreeParents.size == 0){
        TreeItem(tree, SWT.NONE)
    }else{
        TreeItem(allTreeParents[allTreeParents.size-1], SWT.NONE)
    }
}

fun autoRenameFile(count: Int, firstFileName: String, path: String, suffix: String, toWrite: String){
    val newFile = File(path + firstFileName.substring(0, firstFileName.length - suffix.length) + "($count)" + suffix)
    if (newFile.createNewFile()){
        newFile.writeText(toWrite)
        println("File \"${newFile.name}\" created successfully")
    }else{
        autoRenameFile(count+1,firstFileName,path,suffix,toWrite)
    }
}
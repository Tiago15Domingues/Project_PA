import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.TreeItem

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

fun Uijson.setParentInTree (allTreeParents: MutableList<TreeItem>): TreeItem {
    return if (allTreeParents.size == 0){
        TreeItem(tree, SWT.NONE)
    }else{
        TreeItem(allTreeParents[allTreeParents.size-1], SWT.NONE)
    }
}
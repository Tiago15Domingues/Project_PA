import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.Tree
import org.eclipse.swt.widgets.TreeItem

fun keyForContinuousNode(jsonElement: JsonElement, forTree: Boolean ?= true): String {
    return if (jsonElement.key != null) {
        if (forTree == true) {
            "\"" + jsonElement.key + "\""
        }else{
            "\"" + jsonElement.key + "\": "
        }
    } else {
        if (forTree == true)
            jsonElement.toString()
        else
            ""
    }
}
fun keyForEndNode(jsonElement: JsonElement): String {
    return if (jsonElement.key != null) {
        "\"" + jsonElement.key + "\": "
    } else {
        ""
    }
}
fun setParentInTree (rootTree: Tree, allTreeParents: MutableList<TreeItem>): TreeItem {
    return if (allTreeParents.size == 0){
        TreeItem(rootTree, SWT.NONE)
    }else{
        TreeItem(allTreeParents[allTreeParents.size-1], SWT.NONE)
    }
}
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
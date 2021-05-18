import org.eclipse.swt.SWT
import org.eclipse.swt.events.SelectionAdapter
import org.eclipse.swt.events.SelectionEvent
import org.eclipse.swt.graphics.Color
import org.eclipse.swt.layout.GridData
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.widgets.*

fun main() {
    val jsonObject = JsonObject()
    val jsonNumber1 = JsonNumber(123123)
    val jsonString1 = JsonString("Tiago")
    val jsonNull1 = JsonNull(null)
    val jsonBool1 = JsonBoolean(true)

    jsonObject.setProperty("id",jsonNumber1)
    jsonObject.setProperty("name",jsonString1)
    jsonObject.setProperty("male", jsonBool1)
    jsonObject.setProperty("smart", jsonNull1)

    val jsonObject4 = JsonObject()
    val jsonNumber2 = JsonNumber(10)
    val jsonString2 = JsonString("18")
    jsonObject4.setProperty("PA",jsonNumber2)
    jsonObject4.setProperty("IAA",jsonString2)

    val jsonObject6 = JsonObject()
    val jsonNumber3 = JsonNumber(30)
    val jsonArray = JsonArray(arrayOf(jsonNumber3,jsonObject4))
    jsonObject6.setProperty("NOTAS PA", jsonArray)
    jsonObject6.setProperty("NOTA PROG",jsonObject4)

    val jsonObject5 = JsonObject()
    val jsonNumber4 = JsonNumber(19)
    jsonObject5.setProperty("PCD",jsonNumber4)
    jsonObject5.setProperty("NOTAS",jsonObject4)

    val jsonBool2 = JsonBoolean(true)
    val jsonNull2 = JsonNull(null)
    val jsonString3 = JsonString("PASS")
    val jsonArray1 = JsonArray(arrayOf(jsonObject5,jsonNull2))
    val jsonArray2 = JsonArray(arrayOf(jsonString3,jsonObject6,jsonArray1,jsonBool2,jsonObject4))
    jsonObject.setProperty("grades", jsonArray2)

    val jsonObject12 = JsonObject()
    val jsonString4 = JsonString("MirandaDoDouro")
    jsonObject12.setProperty("town",jsonString4)
    jsonObject12.setProperty("born",jsonString4)

    val jsonObject1 = JsonObject()
    val jsonString5 = JsonString("Peka")
    jsonObject1.setProperty("animalID",jsonNumber1)
    jsonObject1.setProperty("name",jsonString5)
    jsonObject1.setProperty("petHome",jsonObject12)

    jsonObject.setProperty("pet",jsonObject1)

    val jsonObject3 = JsonObject()
    val jsonString6 = JsonString("Almada")
    jsonObject3.setProperty("town",jsonString6)
    jsonObject3.setProperty("born",jsonString6)
    jsonObject.setProperty("home",jsonObject3)

    val jsonObject2 = JsonObject()
    jsonObject2.setProperty("MEI_Student",jsonObject)
    println(passJsonElementToTextual((jsonObject2)))
    Uijson().openJsonUI(jsonObject2)
}

class Uijson{
    val shell: Shell = Shell(Display.getDefault())
    val tree: Tree
    val content: Label

    init {
        shell.setSize(450, 500)
        shell.setLocation(1200,100)
        shell.text = "JSON skeleton"
        shell.layout = GridLayout(2,false)

        tree = Tree(shell, SWT.SINGLE or SWT.BORDER or SWT.V_SCROLL)

        content = Label(shell,SWT.BORDER)
        tree.addSelectionListener(object : SelectionAdapter() {
            override fun widgetSelected(e: SelectionEvent) {
                content.text = passJsonElementToTextual(tree.selection.first().data as JsonElement)
                content.pack()
                shell.layout(true)
                shell.pack()
            }
        })

        val label = Text(shell, SWT.BORDER)
        label.layoutData = GridData(SWT.FILL, SWT.CENTER, false, false)
        label.toolTipText = "Search in the Tree for JSONStrings"
        label.addModifyListener {
            tree.traverseTree {
                when (val jsonElem = it.data) {
                    is JsonString -> {
                        if (jsonElem.value.contains(label.text) && label.text != ""){
                            it.background = Color(233,233,143)
                        }else{
                            it.background = Color(255,255,255)
                        }
                    }
                }
            }
        }
    }

    private fun setTreeElementsDefault(jsonElement: JsonElement){
        val parents = mutableListOf<TreeItem>()
        val toTree = object : Visitor {
            override fun visit(o: JsonObject): Boolean {
                val jo = setParentInTree(tree,parents)
                jo.text = keyForContinuousNode(o)
                jo.data = o
                parents.add(jo)
                return true
            }

            override fun endvisitObject(o: JsonObject) {
                parents.removeLast()
            }

            override fun visit(a: JsonArray): Boolean {
                val ja = setParentInTree(tree,parents)
                ja.text = keyForContinuousNode(a)
                ja.data = a
                parents.add(ja)
                return true
            }

            override fun endvisitArray(a: JsonArray) {
                parents.removeLast()
            }

            override fun visit(s: JsonString) {
                val js = TreeItem(parents[parents.size-1], SWT.NONE)
                js.text = keyForEndNode(s) + "\"" + s.value + "\""
                js.data = s
            }

            override fun visit(b: JsonBoolean) {
                val jb = TreeItem(parents[parents.size-1], SWT.NONE)
                jb.text = keyForEndNode(b) + b.value.toString()
                jb.data = b
            }
            override fun visit(n: JsonNull) {
                val jn = TreeItem(parents[parents.size-1], SWT.NONE)
                jn.text = keyForEndNode(n) + n.value.toString()
                jn.data = n
            }
            override fun visit(i: JsonNumber) {
                val ji = TreeItem(parents[parents.size-1], SWT.NONE)
                ji.text = keyForEndNode(i) + i.value.toString()
                ji.data = i
            }
        }
        jsonElement.accept(toTree)
    }

    fun openJsonUI(jsonElement: JsonElement) {
        setTreeElementsDefault(jsonElement)
        tree.expandAllElements()
        shell.pack()
        shell.open()
        val display = Display.getDefault()
        while (!shell.isDisposed) {
            if (!display.readAndDispatch()) display.sleep()
        }
        display.dispose()
    }
}

fun Tree.expandAllElements() = traverseTree { it.expanded = true }

fun Tree.traverseTree(visitor: (TreeItem) -> Unit) {
    fun TreeItem.traverse() {
        visitor(this)
        items.forEach {
            it.traverse()
        }
    }
    items.forEach {
        it.traverse()
    }
}



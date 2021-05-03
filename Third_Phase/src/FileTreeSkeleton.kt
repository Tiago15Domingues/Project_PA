import org.eclipse.swt.SWT
import org.eclipse.swt.events.SelectionAdapter
import org.eclipse.swt.events.SelectionEvent
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
    FileTreeSkeleton(jsonObject2).open()
}

class FileTreeSkeleton(jsonObject: JsonObject) {
    val shell: Shell
    val tree: Tree
    val content: Label

    init {
        shell = Shell(Display.getDefault())
        shell.setSize(450, 500)
        shell.setLocation(1200,100)
        shell.text = "JSON skeleton"
        shell.layout = GridLayout(2,false)

        tree = Tree(shell, SWT.SINGLE or SWT.BORDER or SWT.V_SCROLL)

        setTreeElements(jsonObject)

        val comp = Composite(shell, SWT.BORDER)
        content = Label(comp,SWT.BORDER)
        tree.addSelectionListener(object : SelectionAdapter() {
            override fun widgetSelected(e: SelectionEvent) {
                content.text = tree.selection.first().data.toString()
                content.pack()
                shell.layout(true)
                shell.pack()
            }
        })

        val label = Label(shell, SWT.NONE)
        label.text = "skeleton"

        val button = Button(shell, SWT.PUSH)
        button.text = "depth"
        button.addSelectionListener(object: SelectionAdapter() {
            override fun widgetSelected(e: SelectionEvent) {
                val item = tree.selection.first()
                label.text = item.depth().toString()
            }
        })
    }

    private fun setTreeElements(jsonObject: JsonObject){
        val parents = mutableListOf<TreeItem>()
        var depth = -1
        val toTree = object : Visitor {
            override fun visit(o: JsonObject): Boolean {
                val jo = if (depth == -1){
                    TreeItem(tree, SWT.NONE)
                }else{
                    TreeItem(parents[depth], SWT.NONE)
                }
                if (o.key != null) {
                    jo.text = "\"" + o.key + "\""
                }else {
                    jo.text = o.toString()
                }
                jo.data = passJsonElementToTextual(o)
                parents.add(jo)
                depth++
                return true
            }

            override fun endvisitObject(o: JsonObject) {
                depth--
                parents.removeLast()
            }

            override fun visit(a: JsonArray): Boolean {
                val ja = if (depth == -1){
                    TreeItem(tree, SWT.NONE)
                }else{
                    TreeItem(parents[depth], SWT.NONE)
                }
                if (a.key != null) {
                    ja.text = "\"" + a.key + "\""
                }else {
                    ja.text = a.toString()
                }
                ja.data = passJsonElementToTextual(a)
                parents.add(ja)
                depth++
                return true
            }

            override fun endvisitArray(a: JsonArray) {
                depth--
                parents.removeLast()
            }

            override fun visit(s: JsonString) {
                val js = TreeItem(parents[depth], SWT.NONE)
                if (s.key != null) {
                    js.text = "\"" + s.key + "\": " + "\"" + s.value + "\""
                }else {
                    js.text = "\"" + s.value + "\""
                }
                js.data = passJsonElementToTextual(s)
            }

            override fun visit(b: JsonBoolean) {
                val jb = TreeItem(parents[depth], SWT.NONE)
                if (b.key != null) {
                    jb.text = "\"" + b.key + "\": " + b.value.toString()
                }else {
                    jb.text = b.value.toString()
                }
                jb.data = passJsonElementToTextual(b)
            }
            override fun visit(n: JsonNull) {
                val jn = TreeItem(parents[depth], SWT.NONE)
                if (n.key != null) {
                    jn.text = "\"" + n.key + "\": " + n.value.toString()
                }else {
                    jn.text = n.value.toString()
                }
                jn.data = passJsonElementToTextual(n)
            }
            override fun visit(i: JsonNumber) {
                val ji = TreeItem(parents[depth], SWT.NONE)
                if (i.key != null) {
                    ji.text = "\"" + i.key + "\": " + i.value.toString()
                }else {
                    ji.text = i.value.toString()
                }
                ji.data = passJsonElementToTextual(i)
            }
        }
        jsonObject.accept(toTree)
    }

    // auxiliar para profundidade do nó
    fun TreeItem.depth(): Int =
        if(parentItem == null) 0
        else 1 + parentItem.depth()


    fun open() {
        tree.expandAll()
        shell.pack()
        shell.open()
        val display = Display.getDefault()
        while (!shell.isDisposed) {
            if (!display.readAndDispatch()) display.sleep()
        }
        display.dispose()
    }
}


// auxiliares para varrer a árvore

fun Tree.expandAll() = traverse { it.expanded = true }

fun Tree.traverse(visitor: (TreeItem) -> Unit) {
    fun TreeItem.traverse() {
        visitor(this)
        items.forEach {
            it.traverse()
        }
    }
    items.forEach { it.traverse() }
}



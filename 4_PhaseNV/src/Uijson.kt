import org.eclipse.swt.SWT
import org.eclipse.swt.events.SelectionAdapter
import org.eclipse.swt.events.SelectionEvent
import org.eclipse.swt.graphics.Color
import org.eclipse.swt.graphics.Image
import org.eclipse.swt.layout.GridData
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.layout.RowLayout
import org.eclipse.swt.widgets.*
import java.io.File

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
    val jsonObject7 = JsonObject()
    val jsonNumber3 = JsonNumber(10)
    val jsonNumber5 = JsonNumber(20)
    val jsonNumber6 = JsonNumber(30)
    val jsonArray = JsonArray(arrayOf(jsonNumber3,jsonObject4))
    val jsonArray3 = JsonArray(arrayOf(jsonNumber3,jsonNumber5,jsonNumber6))
    jsonObject6.setProperty("NOTAS PA", jsonArray)
    jsonObject6.setProperty("NOTA PROG",jsonObject4)
    jsonObject.setProperty("ChartArray",jsonArray3)
    jsonObject7.setProperty("x1",jsonNumber3)
    jsonObject7.setProperty("x2",jsonNumber5)
    jsonObject7.setProperty("x3",jsonNumber6)
    jsonObject.setProperty("ChartObject",jsonObject7)

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

    val src = JsonObject()
    val srcName = JsonString("Students")
    src.setProperty("name",srcName)
    val name = JsonString("Tiago")
    val student1 = JsonObject()
    student1.setProperty("name",name)
    src.setProperty("83605",student1)

    val w = Injector.create(Uijson::class)
    w.openJsonUI(jsonObject2)
}

interface FrameSetup {
    fun execute(window: Uijson, jsonElem: JsonElement)
}

interface Action {
    val name: String
    val hintText: String
    fun execute(window: Uijson)
}

class Uijson {
    val shell: Shell = Shell(Display.getDefault())
    val tree: Tree
    val content: Label

    @InjectApresentation
    private var setup: FrameSetup? = null

    @InjectAdd
    private var actions = mutableListOf<Action>()

    init {
        shell.setSize(450, 500)
        shell.setLocation(1200,2)
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
            tree.traverse {
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
        var depth = -1
        val toTree = object : Visitor {
            override fun visit(o: JsonObject): Boolean {
                val res: String = if (o.key != null) {
                    "\"" + o.key + "\""
                }else {
                    o.toString()
                }
                val jo = if (depth == -1){
                    TreeItem(tree, SWT.NONE)
                }else{
                    TreeItem(parents[depth], SWT.NONE)
                }
                jo.text = res
                jo.data = o
                parents.add(jo)
                depth++
                return true
            }

            override fun endvisitObject(o: JsonObject) {
                depth--
                parents.removeLast()
            }

            override fun visit(a: JsonArray): Boolean {
                val res: String = if (a.key != null) {
                    "\"" + a.key + "\""
                }else {
                    a.toString()
                }
                val ja = if (depth == -1){
                    TreeItem(tree, SWT.NONE)
                }else{
                    TreeItem(parents[depth], SWT.NONE)
                }
                ja.text = res
                ja.data = a
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
                } else {
                    js.text = "\"" + s.value + "\""
                }
                js.data = s
            }

            override fun visit(b: JsonBoolean) {
                val jb = TreeItem(parents[depth], SWT.NONE)
                if (b.key != null) {
                    jb.text = "\"" + b.key + "\": " + b.value.toString()
                }else {
                    jb.text = b.value.toString()
                }
                jb.data = b
            }
            override fun visit(n: JsonNull) {
                val jn = TreeItem(parents[depth], SWT.NONE)
                if (n.key != null) {
                    jn.text = "\"" + n.key + "\": " + n.value.toString()
                }else {
                    jn.text = n.value.toString()
                }
                jn.data = n
            }
            override fun visit(i: JsonNumber) {
                val ji = TreeItem(parents[depth], SWT.NONE)
                if (i.key != null) {
                    ji.text = "\"" + i.key + "\": " + i.value.toString()
                }else {
                    ji.text = i.value.toString()
                }
                ji.data = i
            }
        }
        jsonElement.accept(toTree)
    }

    fun openJsonUI(jsonElement: JsonElement) {
        if (setup == null){
            setTreeElementsDefault(jsonElement)
        }else{
            setup!!.execute(this,jsonElement)
        }
        tree.expandAll()
        actions.forEach { action ->
            val button = Button(shell,SWT.PUSH)
            button.text = action.name
            button.toolTipText = action.hintText
            val ui = this
            button.addSelectionListener(object: SelectionAdapter(){
                override fun widgetSelected(e: SelectionEvent?) {
                    action.execute(ui)
                }
            })
        }
        shell.pack()
        shell.open()
        val display = Display.getDefault()
        while (!shell.isDisposed) {
            if (!display.readAndDispatch()) display.sleep()
        }
        display.dispose()
    }
}

fun Tree.expandAll() = traverse { it.expanded = true }

fun Tree.traverse(visitor: (TreeItem) -> Unit) {
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

open class PutIcons : FrameSetup { //Associates icons with JSON Elements
    private var rawImgFolder = Image(Display.getCurrent(),"4_Phase/Icons/Folder.png")
    private var rawImgFile = Image(Display.getCurrent(),"4_Phase/Icons/Text.png")
    val folderIcon: Image = Image(Display.getCurrent(),rawImgFolder.imageData.scaledTo(rawImgFolder.bounds.width/30,rawImgFolder.bounds.height/30))
    val fileIcon: Image =  Image(Display.getCurrent(),rawImgFile.imageData.scaledTo(rawImgFile.bounds.width/30,rawImgFile.bounds.height/30))
    override fun execute(window: Uijson, jsonElem: JsonElement) {
        val parents = mutableListOf<TreeItem>()
        var depth = -1
        val toTree = object : Visitor {
            override fun visit(o: JsonObject): Boolean {
                val res: String = if (o.key != null) {
                    "\"" + o.key + "\""
                }else {
                    o.toString()
                }
                val jo = if (depth == -1){
                    TreeItem(window.tree, SWT.NONE)
                }else{
                    TreeItem(parents[depth], SWT.NONE)
                }
                jo.text = res
                jo.data = o
                jo.image =  folderIcon
                parents.add(jo)
                depth++
                return true
            }

            override fun endvisitObject(o: JsonObject) {
                depth--
                parents.removeLast()
            }

            override fun visit(a: JsonArray): Boolean {
                val res: String = if (a.key != null) {
                    "\"" + a.key + "\""
                }else {
                    a.toString()
                }
                val ja = if (depth == -1){
                    TreeItem(window.tree, SWT.NONE)
                }else{
                    TreeItem(parents[depth], SWT.NONE)
                }
                ja.text = res
                ja.data = a
                ja.image =  folderIcon
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
                } else {
                    js.text = "\"" + s.value + "\""
                }
                js.image = fileIcon
                js.data = s
            }

            override fun visit(b: JsonBoolean) {
                val jb = TreeItem(parents[depth], SWT.NONE)
                if (b.key != null) {
                    jb.text = "\"" + b.key + "\": " + b.value.toString()
                }else {
                    jb.text = b.value.toString()
                }
                jb.image = fileIcon
                jb.data = b
            }
            override fun visit(n: JsonNull) {
                val jn = TreeItem(parents[depth], SWT.NONE)
                if (n.key != null) {
                    jn.text = "\"" + n.key + "\": " + n.value.toString()
                }else {
                    jn.text = n.value.toString()
                }
                jn.image = fileIcon
                jn.data = n
            }
            override fun visit(i: JsonNumber) {
                val ji = TreeItem(parents[depth], SWT.NONE)
                if (i.key != null) {
                    ji.text = "\"" + i.key + "\": " + i.value.toString()
                }else {
                    ji.text = i.value.toString()
                }
                ji.image = fileIcon
                ji.data = i
            }
        }
        jsonElem.accept(toTree)
    }
}

open class ChangeText : FrameSetup { //Change the name of JSONArrays/JSONObjects if they have a certain JSONElement with a certain key

    val propertiesKeyToUpdateNodeText: String = "town"

    override fun execute(window: Uijson, jsonElem: JsonElement) {
        val parents = mutableListOf<TreeItem>()
        var depth = -1
        val toTree = object : Visitor {
            override fun visit(o: JsonObject): Boolean {
                val res: String = if (o.key != null) {
                    "\"" + o.key + "\""
                }else {
                    o.toString()
                }
                val jo = if (depth == -1){
                    TreeItem(window.tree, SWT.NONE)
                }else{
                    TreeItem(parents[depth], SWT.NONE)
                }
                jo.text = res
                jo.data = o
                parents.add(jo)
                depth++
                return true
            }

            override fun endvisitObject(o: JsonObject) {
                depth--
                parents.removeLast()
            }

            override fun visit(a: JsonArray): Boolean {
                val res: String = if (a.key != null) {
                    "\"" + a.key + "\""
                }else {
                    a.toString()
                }
                val ja = if (depth == -1){
                    TreeItem(window.tree, SWT.NONE)
                }else{
                    TreeItem(parents[depth], SWT.NONE)
                }
                ja.text = res
                ja.data = a
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
                } else {
                    js.text = "\"" + s.value + "\""
                }
                js.data = s
                if (s.key == propertiesKeyToUpdateNodeText) {
                    parents[depth].text = s.value
                }
            }

            override fun visit(b: JsonBoolean) {
                val jb = TreeItem(parents[depth], SWT.NONE)
                if (b.key != null) {
                    jb.text = "\"" + b.key + "\": " + b.value.toString()
                }else {
                    jb.text = b.value.toString()
                }
                jb.data = b
            }
            override fun visit(n: JsonNull) {
                val jn = TreeItem(parents[depth], SWT.NONE)
                if (n.key != null) {
                    jn.text = "\"" + n.key + "\": " + n.value.toString()
                }else {
                    jn.text = n.value.toString()
                }
                jn.data = n
            }
            override fun visit(i: JsonNumber) {
                val ji = TreeItem(parents[depth], SWT.NONE)
                if (i.key != null) {
                    ji.text = "\"" + i.key + "\": " + i.value.toString()
                }else {
                    ji.text = i.value.toString()
                }
                ji.data = i
            }
        }
        jsonElem.accept(toTree)
    }
}

open class OmitNoKeysObjectsAndArrays : FrameSetup { //Remove all objects and arrays that have no keys
    override fun execute(window: Uijson, jsonElem: JsonElement) {
        val parents = mutableListOf<TreeItem>()
        var depth = -1
        val toTree = object : Visitor {
            override fun visit(o: JsonObject): Boolean {
                val res: String = if (o.key != null) {
                    "\"" + o.key + "\""
                }else {
                    o.toString()
                }
                if (o.key != null || depth == -1) {
                    val jo = if (depth == -1) {
                        TreeItem(window.tree, SWT.NONE)
                    } else {
                        TreeItem(parents[depth], SWT.NONE)
                    }
                    jo.text = res
                    jo.data = o
                    parents.add(jo)
                    depth++
                }
                return true
            }

            override fun endvisitObject(o: JsonObject) {
                if (o.key != null) {
                    depth--
                    parents.removeLast()
                }
            }

            override fun visit(a: JsonArray): Boolean {
                val res: String = if (a.key != null) {
                    "\"" + a.key + "\""
                }else {
                    a.toString()
                }
                if (a.key != null || depth == -1) {
                    val ja = if (depth == -1) {
                        TreeItem(window.tree, SWT.NONE)
                    } else {
                        TreeItem(parents[depth], SWT.NONE)
                    }
                    ja.text = res
                    ja.data = a
                    parents.add(ja)
                    depth++
                }
                return true
            }

            override fun endvisitArray(a: JsonArray) {
                if (a.key != null) {
                    depth--
                    parents.removeLast()
                }
            }

            override fun visit(s: JsonString) {
                val js = TreeItem(parents[depth], SWT.NONE)
                if (s.key != null) {
                    js.text = "\"" + s.key + "\": " + "\"" + s.value + "\""
                } else {
                    js.text = "\"" + s.value + "\""
                }
                js.data = s
            }

            override fun visit(b: JsonBoolean) {
                val jb = TreeItem(parents[depth], SWT.NONE)
                if (b.key != null) {
                    jb.text = "\"" + b.key + "\": " + b.value.toString()
                }else {
                    jb.text = b.value.toString()
                }
                jb.data = b
            }
            override fun visit(n: JsonNull) {
                val jn = TreeItem(parents[depth], SWT.NONE)
                if (n.key != null) {
                    jn.text = "\"" + n.key + "\": " + n.value.toString()
                }else {
                    jn.text = n.value.toString()
                }
                jn.data = n
            }
            override fun visit(i: JsonNumber) {
                val ji = TreeItem(parents[depth], SWT.NONE)
                if (i.key != null) {
                    ji.text = "\"" + i.key + "\": " + i.value.toString()
                }else {
                    ji.text = i.value.toString()
                }
                ji.data = i
            }
        }
        jsonElem.accept(toTree)
    }
}

class Edit: Action {

    lateinit var content: Text

    override val name: String
        get() = "Edit"
    override val hintText: String
        get() = "JsonNulls cant be changed"

    override fun execute(window: Uijson) {
        val jsonSelected = (window.tree.selection.first().data as JsonElement)
        if(jsonSelected !is JsonNull) {
            window.shell.enabled = false
            val popUp = Shell(Display.getDefault())
            popUp.setSize(150, 300)
            popUp.setMinimumSize(250, 50)
            popUp.setLocation(1200, 100)
            popUp.text = "Edit JSON"
            popUp.layout = RowLayout(SWT.VERTICAL or SWT.WRAP)
            val button = Button(popUp, SWT.PUSH)
            button.text = "Confirm"
            val label = Label(popUp, SWT.NONE)
            content = Text(popUp, SWT.BORDER or SWT.MULTI)
            if (jsonSelected is JsonObject || jsonSelected is JsonArray) {
                label.text = passJsonElementToTextual(jsonSelected)
                if (jsonSelected.key != null) {
                    content.text = jsonSelected.key
                } else {
                    content.text = jsonSelected.toString()
                }
            } else {
                if (jsonSelected.key != null) {
                    label.text = "KEY -> \"" + jsonSelected.key + "\""
                } else {
                    label.text = "NO KEY ASSIGNED"
                }
                when (jsonSelected) {
                    is JsonNumber -> {
                        content.text = jsonSelected.value.toString()
                        content.addVerifyListener { e ->
                            val b = ("0123456789.".indexOf(e!!.text) >= 0)
                            e.doit = b
                        }
                        content.toolTipText = "Write a number only, otherwise the change will not be saved"
                    }
                    is JsonBoolean -> {
                        content.toolTipText = "Write \"true\" or \"false\" to make changes possible"
                        content.text = jsonSelected.value.toString()
                    }
                    is JsonString -> {
                        content.text = jsonSelected.value
                    }
                }
            }
            button.addSelectionListener(object : SelectionAdapter() {
                override fun widgetSelected(e: SelectionEvent?) {
                    when (jsonSelected) {
                        is JsonObject, is JsonArray -> {
                            jsonSelected.key = content.text
                            window.tree.selection.first().text = content.text
                        }
                        is JsonString -> {
                            jsonSelected.value = content.text
                            if (jsonSelected.key != null) {
                                window.tree.selection.first().text = "\"" + jsonSelected.key + "\": \"" + content.text + "\""
                            } else {
                                window.tree.selection.first().text = "\"" + content.text + "\""
                            }
                        }
                        is JsonNumber -> {
                            if (content.text.toFloatOrNull() != null) {
                                jsonSelected.value = content.text.toFloatOrNull()!!
                                if (jsonSelected.key != null) {
                                    window.tree.selection.first().text = "\"" + jsonSelected.key + "\": " + content.text
                                } else {
                                    window.tree.selection.first().text = content.text
                                }
                            }
                        }
                        is JsonBoolean -> {
                            if (content.text.equals("true", true) || content.text.equals("false", true)) {
                                jsonSelected.value = content.text.toBoolean()
                                if (jsonSelected.key != null) {
                                    window.tree.selection.first().text = "\"" + jsonSelected.key + "\": " + content.text.toBoolean()
                                } else {
                                    window.tree.selection.first().text = content.text.toBoolean().toString()
                                }
                            }
                        }
                    }
                    window.tree.update()
                    popUp.dispose()
                    window.shell.enabled = true
                }
            })
            popUp.pack()
            popUp.open()
            val display = Display.getCurrent()
            while (!popUp.isDisposed) {
                if (!display.readAndDispatch()) display.sleep()
            }
            popUp.dispose()
            window.shell.enabled = true
            window.shell.open()
        }
    }
}

class Write: Action {
    override val name: String
        get() = "Write"
    override val hintText: String
        get() = "Write JsonTextual to a file"

    override fun execute(window: Uijson) {
        val jsonSelected = (window.tree.selection.first().data as JsonElement)
        val textual = passJsonElementToTextual(jsonSelected)
        val file = if (jsonSelected.key != null)
            File("4_PhaseNV/" + jsonSelected.key + ".txt")
        else
            File("4_PhaseNV/$jsonSelected.txt")
        if (file.createNewFile())
            println("File created successfully")
        else
            println("${file.name} already exists.")
        file.writeText(textual)
    }
}

class Validate: Action {
    private val keyToSearch = "name"

    override val name: String
        get() = "Validate"
    override val hintText: String
        get() = "Validate if every JsonObjects/JsonArrays have an a element (JSONString) with a key \"$keyToSearch\""

    override fun execute(window: Uijson) {
        var validate = false
        window.tree.traverse {
            when (val jsonElem = it.data) {
                is JsonArray -> {
                    validate = false
                    jsonElem.jsonArrayContent.forEach { it1->
                        if (it1.key == keyToSearch && it1 is JsonString){
                            validate = true
                        }
                    }
                    if (!validate){
                        return@traverse
                    }
                }
                is JsonObject -> {
                    validate = false
                    jsonElem.jsonObjectContent.forEach { it1->
                        if (it1.key == keyToSearch && it1 is JsonString){
                            validate = true
                        }
                    }
                    if (!validate){
                        return@traverse
                    }
                }
            }
        }
        window.shell.enabled = false
        val popUp = Shell(Display.getDefault())
        popUp.setSize(150, 300)
        popUp.setMinimumSize(250, 50)
        popUp.setLocation(1200, 100)
        popUp.text = "Validation"
        popUp.layout = RowLayout(SWT.VERTICAL or SWT.WRAP)
        val label = Label(popUp, SWT.NONE)
        label.text = "Every JsonObjects/JsonArrays have an element(JSONString) with the key \"$keyToSearch\"? $validate"
        popUp.pack()
        popUp.open()
        val display = Display.getCurrent()
        while (!popUp.isDisposed) {
            if (!display.readAndDispatch()) display.sleep()
        }
        popUp.dispose()
        window.shell.enabled = true
        window.shell.open()
    }
}

class ChangeJsonDisplayMode: Action {
    override val name: String
        get() = "More"
    override val hintText: String
        get() = "Alternative view for the selected object (Only for arrays and objects with only numbers)"

    override fun execute(window: Uijson) {
        val jsonSelected = (window.tree.selection.first().data as JsonElement)
        if (jsonSelected is JsonArray || jsonSelected is JsonObject) {
            val content = when (jsonSelected) {
                is JsonArray -> {
                    jsonSelected.jsonArrayContent
                }
                is JsonObject -> {
                    jsonSelected.jsonObjectContent
                }
                else -> {
                    null
                }
            }
            val listToPlot = mutableListOf<Number>()
            val listToPlotNames = mutableListOf<String>()
            val graphTitle = if (jsonSelected.key != null) {
                jsonSelected.key
            } else {
                jsonSelected.toString()
            }
            var cantPlot = false
            content!!.forEach {
                if (it !is JsonNumber) {
                    cantPlot = true
                    return@forEach
                } else {
                    listToPlot.add(it.value)
                    if (it.key != null) {
                        listToPlotNames.add(it.key!!)
                    } else {
                        listToPlotNames.add("x" + content.indexOf(it).toString())
                    }
                }
            }
            if (!cantPlot) {
                println(listToPlot)
                println(graphTitle)
                println(listToPlotNames)
            }
        }
    }
}



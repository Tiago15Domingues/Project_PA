# Project_PA
Advanced Programming Project with 4 phases - Curricular unit of the 2nd semester of the master's degree in computer engineering at ISCTE-IUL

Isto é, em particular no módulo correspondente à "4_Phase" que contém tudo o que foi realizado nas outras fases, uma biblioteca pronto para ser utilzada para a produção de dados no formato JSON.

Sendo possível utilizar o modelo de dados construído e desenvolver plug-ins para o visualizador do JSON em questão - Esta utilização e desenvolvimento está explicada com mais detalhe nos tutoriais presentes no Wiki do gitHub, podendo também os "mains" de cada fase ajudá-lo a perceber melhor como se deve utilizar esta biblioteca.

---------------------------------------WIKI (Caso o projeto esteja privado)---------------------------------------
---------------------------------------Modelo de dados
# **Introdução:**
* Este modelo de dados está preparado para produzir e analisar dados no formato JSON;

* Está também preparado para instanciar objetos da biblioteca e fazer a sua serialização para texto.
Com este modelo de dados é também possível derivar JSON a partir de objetos, podendo o processo ser adaptado por via de anotações nas classes desses objetos.
***
# **Aspetos a ter em conta:**
* Todos os elementos do JSON são neste modelo de dados objetos do tipo da classe abstrata "JsonElement"; 

* Podendo depois esses elementos serem objetos da classe "JsonObject", "JsonArray", "JsonString", "JsonNumber", "JsonBoolean" ou um "JsonNull", consoante o tipo de elemento JSON que se pretende produzir;

* A classe abstrata "JsonElement" é constítuida por um método "accept(v: Visitor)" (que permite realizar um varrimento simples a um certo "JsonElement", utilizando a função "accept" desse mesmo "JsonElement", através da utilização do padrão de desenhos dos visitantes), uma variável "parent" (que representa o pai associado ao "JsonElement" em questão - que pode ser null caso o "JsonElement" seja o "rootNode" do JSON, ou seja, caso o "JsonElement" tenha na sua constituição todos os outros "JsonElement" do JSON em questão - esta variável poderá ser apenas um "JsonArray", um "JsonObject" ou null) e uma variável "key" (que representa a chave associada ao "JsonElement" em questão - que pode ser null, caso o "JsonElement" em causa não tenha uma "key" associada, caso que acontece quando o "parent" do "JsonElement" é um "JsonArray", caso contrário terá uma "key" que será uma String);

* Cada elemento do JSON tem um valor. Portanto, cada classe que usa a classe "JsonElement" ("JsonObject", "JsonArray", "JsonString", "JsonNumber", "JsonBoolean" e "JsonNull"), além das propriedades que herda da classe "JsonElement", têm também um valor a elas associado ("JsonArray" - Array de "JsonElement", "JsonString" - String, "JsonNumber" - Number, "JsonBoolean" - Boolean, "JsonNull" - null, "JsonObject" - lista de "JsonElement"); 

* Todas as instâncias de "JsonElement" que se usam para criar um JSON serão, no seu processo de criação e associação a um "JsonObject" ou "JsonArray" descartadas. Preservando as características do "JsonElement" em questão na mesma. Isto acontece pois pode acontecer um "JsonElement" ser reutilizado em diferentes profundidades e portanto irá ter diferentes "parents". Dito isto, de forma a ter sempre o "parent" correto associado a cada "JsonElement", é criada sempre uma nova instância de cada "JsonElement" que entra para a criação do JSON.
***
# **Como utilizar:**
* Para criar um JSON pode fazê-lo criando instância das classes que que representam os elementos de um JSON ("JsonObject", "JsonArray", "JsonString", "JsonNumber", "JsonBoolean" e "JsonNull");

* Para associar os elementos do JSON como acher melhor tem ao seu dispor o método "setProperty" da classe "JsonObject". Para então associar um certo "JsonElement" ao "JsonObject" em questão (aqui estará a defenir o valor do "JsonObject" em causa, nos outros tipos de "JsonElement" essa defenição é feita no momento em que se intância a classe em causa). Sendo necessário, aqui, defenir a "key" que quer associar a esse "JsonElement"; Necessita, para que todos os processos dos modelos de dados funcionem corretamente (e.g "passJsonElementToTextual"), de associar sempre todos os "JsonElement" que cria a um "JsonArray" ou "JsonObject", excepto o "JsonObject" que pretende que seja o root do seu JSON, ou seja, o "JsonElement" que não terá "key" e não terá um "parent" associado a ele. A partir daí, se quiser, pode obter todas as características de todos os seus "JsonElement" individualmente;

* Caso pretenda analisar e percorrer todos os elementos de um JSON poderá e deverá utilizar o padrão de desenhos dos visitantes utilizando o método "accept" da classe "JsonElement", percorrendo assim todos os "JsonElement" presentes na constituição do "JsonElement" em questão;

* Caso queira serializar um elemento JSON (um "JsonElement") para texto poderá e deverá utilizar o método "passJsonElementToTextual";

* Caso queira uma lista com todas as Strings que existem num certo "JsonElement" (elemento do JSON), poderá e deverá utilizar o método "findAllStrings";

* Caso queira uma lista com todos os "JsonObject", num certo "JsonElement", que tenham uma certa "JsonString" com um certo valor, poderá e deverá utilizar o método "findJsonObjectWithSpecificString";

* Caso pretenda obter a "key" de um "JsonElement", pronta para ser colocada na serialização para texto de um JSON ou numa árvore na UI, Poderá e deverá utilizar o método "keyToShow" da classe "JsonElement";

* Caso queira derivar JSON (obter o "JsonElement") a partir de objetos, podendo o processo ser adptado por vias de anotações nas classes desses objetos (podendo omitir (anotação "Exlude") ou alterar o nome associado a uma certa característica ((anotação  "NKey"), que será uma futura "key" no JSON, do objeto em questão), poderá e deverá utilizar o método "getJSON" - o método está preparado para qualquer classe, tenham elas um "Int", "String", "Boolean", "Collection", "HasMap", "dataclass", "enum" ou "null" nas suas características.
***
# **Exemplos de utilização:**
* ### Cria um JSON com diferentes "JsonElement":  
```
    val jo = JsonObject()
    val jn = JsonNumber(123)
    jo.setProperty("KeyFor 'jn'",jn)
    val js = JsonString("Test")
    val jb = JsonBoolean(true)
    val jo2 = JsonObject()
    val jN = JsonNull(null)
    jo2.setProperty("KeyFor 'jN'",jN)
    val ja = JsonArray(arrayOf(js,jb,jN,jo2))
    jo.setProperty("KeyFor 'ja'",ja)
```
* ### Fazer a serialização para texto de um JSON ("JsonElement"):
```
passJsonElementToTextual(jo)
```
* ### Utilização do padrão de desenhos dos visitantes:
```
Presente no visitor presente no método "passJsonElementToTextual"
```
* ### Obter todas as Strings que existem num certo "JsonElement" (JSON):
```
findAllStrings(ja)
```
* ### Obter todos os "JsonObject", num certo "JsonElement", que tenham uma certa "JsonString" com um certo valor:
```
findJsonObjectWithSpecificString(jo,"Peka")
```
* ### Obter a "key" de um "JsonElement", pronta para ser colocada na serialização para texto de um JSON ou numa árvore na UI
```
ja.keyToShow(false)
```
* ### Obter o "JsonElement" (JSON) a partir de objetos:
```
data class Student(
        @Exclude
        val number: Int?,
        @NKey("studentName")
        val name: String?,
        val repeting: Boolean?,
        val bestGrades: Collection<Any>?,
        val activities: HashMap<String, String>?,
        val bestFriend: Student?,
        val studentType: Enum<StudentType>?
)
var studentZeca : Student = Student(8000,"Zeca",true, setOf(12,14), hashMapOf("Indoor" to "Darts","Outside" to "Cross Country"),null,StudentType.Doctoral)
getJSON(studentZeca)
```
---------------------------------------Visualizador

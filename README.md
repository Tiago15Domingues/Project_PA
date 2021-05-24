# Project_PA
Advanced Programming Project with 4 phases - Curricular unit of the 2nd semester of the master's degree in computer engineering at ISCTE-IUL

Isto é, em particular no módulo correspondente à "4_Phase" que contém tudo o que foi realizado nas outras fases, uma biblioteca pronto para ser utilzada para a produção de dados no formato JSON.

Sendo possível utilizar o modelo de dados construído e desenvolver plug-ins para o visualizador do JSON em questão - Esta utilização e desenvolvimento está explicada com mais detalhe nos tutoriais presentes no Wiki do gitHub, podendo também os "mains" de cada fase ajudá-lo a perceber melhor como se deve utilizar esta biblioteca.

--------------------------------------- #WIKI (Caso o projeto esteja privado)---------------------------------------
# **Modelo de dados**
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
----------------------------------------------
# Visualizador
# **Introdução:**
* É possível, através desta biblioteca, o desenvolvimento de plug-ins para a visualização e aplicação de ações a um JSON ("JsonElement").
***
# **Aspetos a ter em conta:**
* Para o desenvolvimento dos plug-ins e utilização do visualizador é também importante perceber e saber utilizar o modelos de dados e todas as suas propriedades explicadas no wiki "Modelo de dados";

* A UI atualmente presente no código torna possível visualizar em forma de árvore todos os elementos do "JsonElement"(JSON) em questão, os seus valores e a sua serialização para texto (selecionando o "JsonElement" que se pretende visualizar). Através da UI existe também a possibilidade de pesquisar quais os elementos da árvore são uma "JsonStrings" que têm o valor que se está a procurar, acontecendo um realce (a amarelo), dessas mesmas "JsonStrings" na árvore; 

* O código está preparado para existirem 2 tipos de plug-ins adicionados. Plug-ins de visualização e plug-ins de ações.

* Plug-ins de visualização só podem ser utilizados 1 de cada vez. Plug-ins de ações podem ser usados vários ao mesmo tempo.

* **Os plug-in de ações presentes no código fazem as seguintes coisas:**
* * A "Edit", onde se dá a possibilidade de editar qualquer tipo de "JsonElement", exceto "JsonObject" e "JsonArray" que não tenham nenhuma "key" e "JsonNull", os restantes se não forem "JsonObject" e "JsonArray" é possível editar o seu valor, se forem será possível editar a sua "key". Esta edição acontece não só na árvore (visualmente) mas também no "JsonElement" associado ao elemento da árvore selecionado, dito isto, se um "JsonElement" tiver associado a mais que um elemento na árvore então esses elementos serão também eles alterados. Pois aqui estamos a editar o "JsonElement" e as suas propriedades, que ao serem alteradas, irão então alterar os itens da árvore.
* * A "Write" que permite criar um ficheiro ".txt" cujo o conteúdo é a serialização para texto do "JsonElement" associado ao elemento da árvore selecionado;
* * A "Validate" que valida se todos os "JsonObject" têm uma certa "JsonString" com uma certa "key" no seu valor;
* * A "ChangeJsonDisplayMode" que fornece uma diferente forma de visualização de "JsonArray" e "JsonObject" que sejam apenas constituídos por "JsonNumber". Disponibilizando listas com o necessário para criar um gráfico com base nos valores desses "JsonElement".

* **O plug-in de visualização presente no código faz a seguinte coisa:**
* * Define-se um ícone de uma pasta para todos os "JsonObject" e "JsonArray" da árvore;
* * Altera-se o nome (que corresponde à sua "key") de cada "JsonObject" que tenha "key" se no seu valor (lista de "JsonElement") tiver uma "JsonString" com a "key" "town". A alteração ocorre e o novo valor será o valor associado a essa mesma "JsonString", em caso de mais que uma "JsonString" com essa "key", irá prevalecer a última;
* * Omite-se todos os "JsonObject" e "JsonArray" que não tenham nenhuma "key" associadas a eles. Não sendo omitido o seu conteúdo (se não forem também um "JsonObject" ou "JsonArray" sem "key").
***
# **Como utilizar:**
* Caso pretenda ter acesso a este visualizador necessita apenas de criar uma instância da classe "Uijson" usando a classe "Injector" e o seu método "create" e, após isso, utilizar o método "open" dessa mesma classe.

* **Visualização:**
* * Caso pretenda adicionar um novo plug-in do tipo de visualização terá apenas de criar uma classe que use a interface "FrameSetup". De seguida, através dos métodos dessa interface, que serão herdados pela classe que criou, poderá realizar as alterações que melhor entender ao processo de criação da árvore que, portanto, irão alterar a forma como se irá ver a árvore criada. Esses alterações prendem-se com:
* * * No método "setIcons" da interface poderá definir qual o ícone associado a cada um dos "JsonElements" na árvore, podendo realizar um simples "Switch-Case" e diferenciar o que pretende fazer aos ícones de cada "JsonElement" da árvore, retornando a "Image" que pretende para o "JsonElement" em questão - Isto pois essa função irá ser chamada na construção da árvore para se ir buscar a ela o valor da "Image" que é suposto associar ao elemento da árvore que se está associar a um "JsonElement" naquele momento (que será o "JsonElement" que estará a ser passado como argumento no método); Caso pretenda que nenhuma "Image" seja associada basta retornar null;
* * * No método "changeText" da interface poderá definir qual texto quer associar a cada um dos "JsonElements" na árvore (nome do nó), podendo realizar um simples "Switch-Case" e diferenciar o que pretende fazer ao texto de cada "JsonElement" da árvore, retornando a "String"(texto) que pretende para o "JsonElement" em questão na árvore - Isto pois essa função irá ser chamada na construção da árvore para se ir buscar a ela o valor que é suposto associar ao elemento da árvore que se está a associar ao "JsonElement" naquele momento (que será o "JsonElement" que estará a ser passado como argumento no método); Caso pretenda que seja aplicado o texto default basta retornar null;
* * * No método "omitNodes" da interface poderá definir que "JsonElement" pretende omitir na criação da árvore (não aparecendo na árvore na UI), podendo realizar um simples "Switch-Case" e diferenciar o que pretende fazer a cada "JsonElement" da árvore, retornando true caso pretenda que o "JsonElement" em questão seja omitido da árvore - Isto pois essa função irá ser chamada na construção da árvore para se ir buscar a ela o "Boolean" em questão que é suposto aplicar ao "JsonElement" que se está a associar a um elemento da árvore no momento (que será o "JsonElement" que estará a ser passado como argumento no método); Caso pretenda que o "JsonElement" não seja omitido basta retornar null ou false.
* * * Para que os plug-ins que realizou sejam aplicados corretamente terá, no ficheiro de configuração ("di.properties"), colocar numa linha "Uijson.setup=" e escrever à frente a classe que criou (que usa a interface "FrameSetup") para fazer o seu plug-in. Se a classe for "Visualization" (que é o que está atualmente no código) o ficheiro de configuração ficará "Uijson.setup=Visualization". Podendo apenas ser usado 1 tipo de plug-in de cada vez. Não devendo colocar algo do género "Uijson.setup=Visualization,Icons", pois desta forma, só a primeira classe irá ser tida em conta ("Visualization");
* **Ação:**
* * Caso pretenda adicionar um novo plug-in do tipo de ação terá apenas de criar uma classe que use a interface Action". De seguida, através do método dessa interface, que será herdado pela classe que criou, poderá realizar a ação que melhor entender. Esses ações prendem-se com:
* * * No método "execute" da interface poderá definir o que pretende fazer com aquela ação. Tendo, através do argumento do método (a classe "Uijson") os principais valores (a árvore em questão - "tree" ; a shell usada - "shell" ; o "JsonElement" usado para a criação da árvore - "jsonElementRoot", etc) e métodos (que falarei em seguida) que necessita para realizar uma grande variedade de ações.
* * * Para que o que os plug-ins que realizou sejam aplicados corretamente terá, no ficheiro de configuração ("di.properties"), colocar numa linha "Uijson.actions=" e escrever à frente a classe que criou (que usa a interface "Action") para fazer o seu plug-in. Se a classe for "Edit" (que é o que está atualmente no código) o ficheiro de configuração ficará "Uijson.setup=Edit". Podendo serem usados vários tipos de plug-in ao mesmo tempo. Podendo ser colocado algo do género "Uijson.actions=Edit,Write,Validate,ChangeJsonDisplayMode". Desta forma todas as ações estarão disponíveis na UI;
* Caso, na criação dos seus plug-ins, queira obter todos os "JsonElement" presentes num "JsonArray" ou "JsonObject" com uma certa "key" poderá e deverá utilizar o método "allJsonElementWithCertainKeyInsideContinuosNode" da classe "JsonElement";
* Caso, na criação dos seus plug-ins, queira obter o elemento ("JsonElement") atualmente selecionado na árvore poderá e deverá utilizar o método "getSelectedElementData" da classe "Uijson";
* Caso queira atualizar a árvore com base nos mesmos elementos que foram usados na sua construção, havendo uma alteração na árvore caso esses elementos tenham sofrido alterações, poderá e deverá utilizar o método "updateTreeText" da classe "Uijson";
* Caso queira renomear automaticamente um ficheiro que esteja a ser criado mas que nesse processo acontece o nome do ficheiro já ter sido utilizado para a criação de outro ficheiro poderá e deverá utilizar o método "autoRenameFile. Pois ao acontecer isso, basta chamar esta função, passando nos argumentos o nome do ficheiro que se ia escrever (default), o caminho até onde se pretende colocar esse ficheiro, o sufixo a ser aplicado a essa ficheiro e o que se pretende escrever nesse ficheiro;
* Caso queira expandir, visualmente, todos os elementos da árvore poderá e deverá utilizar o método "expandAll" da classe "Tree" da biblioteca "SWT";
* Caso queira percorrer (como se de uma lista se trata-se) todos os elementos da árvore poderá e deverá utilizar o método "traverse" da classe "Tree" da biblioteca "SWT";
***
# **Exemplos de utilização:**
* ### Visualizar a UI:
```
		val w = Injector.create(Uijson::class)
    		w.openJsonUI(jsonElement)
```
* ### Criação de um plug-in de visualização:
```
		Classe "Visualization" atualmente presente no código;
```
* ### Criação de plug-ins de ações:
```
		Classes "Edit,Write,Validate,ChangeJsonDisplayMode" atualmente presentes no código;
```
* ### Após a criação dos plug-ins fazer com tenham efeito, adicionando isto ao ficheiro de configuração:
```
		Uijson.setup=Visualization
		Uijson.actions=Edit,Write,Validate,ChangeJsonDisplayMode
```
* ### Obter todos os "JsonElement" presentes num "JsonArray" ou "JsonObject" com uma certa "key":
```
		jsonElement.allJsonElementWithCertainKeyInsideContinuosNode("name")
```
* ### Obter o elemento ("JsonElement") atualmente selecionado na árvore:
```
		uiJson.getSelectedElementData()
```
* ### Atualizar a árvore com base nos mesmos elementos que foram usados na sua construção:
```
		uiJson.updateTreeText()
```
* ### Renomear automaticamente um ficheiro que esteja a ser criado mas que nesse processo acontece o nome do ficheiro já ter sido utilizado para a criação de outro ficheiro:
```
		Presente na classe "Write" um exemplo da sua utilização
```
* ### Expandir, visualmente, todos os elementos da árvore:
```
		Presente no método "open" da classe "Uijson":
		tree.expandAll()
```
* ### Percorrer (como se de uma lista se trata-se) todos os elementos da árvore:
```
		Presente no método "updateTreeText" da classe "Uijson"
		tree.traverse { }
```

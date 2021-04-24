import org.junit.jupiter.api.Assertions.*

internal class MainKtTest {

    @org.junit.jupiter.api.Test
    fun getJSON() {
        assertEquals("{\n" +
                "\t\"activities\": {\n" +
                "\t\t\"Indoor\": \"Chest\",\n" +
                "\t\t\"Outside\": \"Football\"\n" +
                "\t},\n" +
                "\t\"bestFriend\": {\n" +
                "\t\t\"activities\": {\n" +
                "\t\t\t\"Indoor\": \"Darts\",\n" +
                "\t\t\t\"Outside\": \"Cross Country\"\n" +
                "\t\t},\n" +
                "\t\t\"bestFriend\": null,\n" +
                "\t\t\"bestGrades\": [\n" +
                "\t\t\t12,\n" +
                "\t\t\t14\n" +
                "\t\t],\n" +
                "\t\t\"name\": \"Zeca\",\n" +
                "\t\t\"number\": 8000,\n" +
                "\t\t\"repeting\": true,\n" +
                "\t\t\"studentType\": \"Doctoral\"\n" +
                "\t},\n" +
                "\t\"bestGrades\": [\n" +
                "\t\t{\n" +
                "\t\t\t\"activities\": {\n" +
                "\t\t\t\t\"Indoor\": \"Darts\",\n" +
                "\t\t\t\t\"Outside\": \"Cross Country\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"bestFriend\": null,\n" +
                "\t\t\t\"bestGrades\": [\n" +
                "\t\t\t\t12,\n" +
                "\t\t\t\t14\n" +
                "\t\t\t],\n" +
                "\t\t\t\"name\": \"Zeca\",\n" +
                "\t\t\t\"number\": 8000,\n" +
                "\t\t\t\"repeting\": true,\n" +
                "\t\t\t\"studentType\": \"Doctoral\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"activities\": {\n" +
                "\t\t\t\t\"Indoor\": \"Chest\",\n" +
                "\t\t\t\t\"Outside\": \"Football\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"bestFriend\": {\n" +
                "\t\t\t\t\"activities\": {\n" +
                "\t\t\t\t\t\"Indoor\": \"Darts\",\n" +
                "\t\t\t\t\t\"Outside\": \"Cross Country\"\n" +
                "\t\t\t\t},\n" +
                "\t\t\t\t\"bestFriend\": null,\n" +
                "\t\t\t\t\"bestGrades\": [\n" +
                "\t\t\t\t\t12,\n" +
                "\t\t\t\t\t14\n" +
                "\t\t\t\t],\n" +
                "\t\t\t\t\"name\": \"Zeca\",\n" +
                "\t\t\t\t\"number\": 8000,\n" +
                "\t\t\t\t\"repeting\": true,\n" +
                "\t\t\t\t\"studentType\": \"Doctoral\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"bestGrades\": [\n" +
                "\t\t\t\t19,\n" +
                "\t\t\t\t18\n" +
                "\t\t\t],\n" +
                "\t\t\t\"name\": \"Alfredo\",\n" +
                "\t\t\t\"number\": 83605,\n" +
                "\t\t\t\"repeting\": false,\n" +
                "\t\t\t\"studentType\": \"Bachelor\"\n" +
                "\t\t}\n" +
                "\t],\n" +
                "\t\"name\": \"Maleiro\",\n" +
                "\t\"number\": 83605,\n" +
                "\t\"repeting\": false,\n" +
                "\t\"studentType\": \"Master\"\n" +
                "}", passJsonElementToTextual(getJSON(studentMaleiro) as JsonObject))
        assertEquals(JsonObject()::class,(getJSON(studentMaleiro) as JsonObject)::class)
    }

    @org.junit.jupiter.api.Test
    fun getJSONList() {                 //Verifico se o Textual do primeiro elemento da lista corresponde ao esperado, uma vez que tenho uma lista de JSONObjects
                                        //e não conseguiria saber a instância exata dos jsonObjects retornados,
                                        //mas consigo ter a certeza que as suas características (serialização para texto) são as mesmas.
        assertEquals("{\n" +
                "\t\"activities\": {\n" +
                "\t\t\"Indoor\": \"Chest\",\n" +
                "\t\t\"Outside\": \"Football\"\n" +
                "\t},\n" +
                "\t\"bestFriend\": {\n" +
                "\t\t\"activities\": {\n" +
                "\t\t\t\"Indoor\": \"Darts\",\n" +
                "\t\t\t\"Outside\": \"Cross Country\"\n" +
                "\t\t},\n" +
                "\t\t\"bestFriend\": null,\n" +
                "\t\t\"bestGrades\": [\n" +
                "\t\t\t12,\n" +
                "\t\t\t14\n" +
                "\t\t],\n" +
                "\t\t\"name\": \"Zeca\",\n" +
                "\t\t\"number\": 8000,\n" +
                "\t\t\"repeting\": true,\n" +
                "\t\t\"studentType\": \"Doctoral\"\n" +
                "\t},\n" +
                "\t\"bestGrades\": [\n" +
                "\t\t{\n" +
                "\t\t\t\"activities\": {\n" +
                "\t\t\t\t\"Indoor\": \"Darts\",\n" +
                "\t\t\t\t\"Outside\": \"Cross Country\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"bestFriend\": null,\n" +
                "\t\t\t\"bestGrades\": [\n" +
                "\t\t\t\t12,\n" +
                "\t\t\t\t14\n" +
                "\t\t\t],\n" +
                "\t\t\t\"name\": \"Zeca\",\n" +
                "\t\t\t\"number\": 8000,\n" +
                "\t\t\t\"repeting\": true,\n" +
                "\t\t\t\"studentType\": \"Doctoral\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"activities\": {\n" +
                "\t\t\t\t\"Indoor\": \"Chest\",\n" +
                "\t\t\t\t\"Outside\": \"Football\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"bestFriend\": {\n" +
                "\t\t\t\t\"activities\": {\n" +
                "\t\t\t\t\t\"Indoor\": \"Darts\",\n" +
                "\t\t\t\t\t\"Outside\": \"Cross Country\"\n" +
                "\t\t\t\t},\n" +
                "\t\t\t\t\"bestFriend\": null,\n" +
                "\t\t\t\t\"bestGrades\": [\n" +
                "\t\t\t\t\t12,\n" +
                "\t\t\t\t\t14\n" +
                "\t\t\t\t],\n" +
                "\t\t\t\t\"name\": \"Zeca\",\n" +
                "\t\t\t\t\"number\": 8000,\n" +
                "\t\t\t\t\"repeting\": true,\n" +
                "\t\t\t\t\"studentType\": \"Doctoral\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"bestGrades\": [\n" +
                "\t\t\t\t19,\n" +
                "\t\t\t\t18\n" +
                "\t\t\t],\n" +
                "\t\t\t\"name\": \"Alfredo\",\n" +
                "\t\t\t\"number\": 83605,\n" +
                "\t\t\t\"repeting\": false,\n" +
                "\t\t\t\"studentType\": \"Bachelor\"\n" +
                "\t\t}\n" +
                "\t],\n" +
                "\t\"name\": \"Maleiro\",\n" +
                "\t\"number\": 83605,\n" +
                "\t\"repeting\": false,\n" +
                "\t\"studentType\": \"Master\"\n" +
                "}", passJsonElementToTextual(getJSONList(mutableListOf(studentMaleiro, studentAlfredo))[0]))
        assertEquals(mutableListOf(JsonObject())::class,(getJSONList(mutableListOf(studentMaleiro, studentAlfredo)))::class)
    }
}

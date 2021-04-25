import org.junit.jupiter.api.Assertions.*

internal class MainKtTest {

    @org.junit.jupiter.api.Test
    fun getJSON() {
        val jsonObject = JsonObject()
        val jsonObject1 = JsonObject()
        val jsonStringChest = JsonString("Chest")
        val jsonStringFootball = JsonString("Football")
        val jsonStringDarts= JsonString("Darts")
        val jsonStringCrossCountry = JsonString("Cross Country")
        val jsonNull = JsonNull(null)
        jsonObject1.setProperty("Indoor",jsonStringChest)
        jsonObject1.setProperty("Outside",jsonStringFootball)
        jsonObject.setProperty("activities",jsonObject1)

        val jsonObject2 = JsonObject()
        val jsonObject3 = JsonObject()
        jsonObject3.setProperty("Indoor",jsonStringDarts)
        jsonObject3.setProperty("Outside",jsonStringCrossCountry)
        jsonObject2.setProperty("activities",jsonObject3)
        jsonObject2.setProperty("bestFriend",jsonNull)
        val jsonArray = JsonArray(arrayOf(JsonNumber(12),JsonNumber(14)))
        jsonObject2.setProperty("bestGrades",jsonArray)
        jsonObject2.setProperty("name",JsonString("Zeca"))
        jsonObject2.setProperty("number",JsonNumber(8000))
        jsonObject2.setProperty("repeting",JsonBoolean(true))
        jsonObject2.setProperty("studentType",JsonString("Doctoral"))
        jsonObject.setProperty("bestFriend",jsonObject2)

        val jsonObject7 = JsonObject()
        jsonObject7.setProperty("activities",jsonObject1)
        jsonObject7.setProperty("bestFriend",jsonObject2)
        val jsonArray4 = JsonArray(arrayOf(JsonNumber(19),JsonNumber(18)))
        jsonObject7.setProperty("bestGrades",jsonArray4)
        jsonObject7.setProperty("name",JsonString("Alfredo"))
        jsonObject7.setProperty("number",JsonNumber(83605))
        jsonObject7.setProperty("repeting",JsonBoolean(false))
        jsonObject7.setProperty("studentType",JsonString("Bachelor"))

        val jsonArray1 = JsonArray(arrayOf(jsonObject2,jsonObject7))
        jsonObject.setProperty("bestGrades",jsonArray1)
        jsonObject.setProperty("name",JsonString("Maleiro"))
        jsonObject.setProperty("number",JsonNumber(83605))
        jsonObject.setProperty("repeting",JsonBoolean(false))
        jsonObject.setProperty("studentType",JsonString("Master"))
        assertEquals(passJsonElementToTextual(jsonObject),passJsonElementToTextual(getJSON(studentMaleiro) as JsonObject)) //Verificar se retorna o objeto correto defenindo esse mesmo objeto
        assertEquals("{\n" + //Verificar se retorna a serialização em JSON da dataClass
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
        assertEquals(JsonObject()::class,(getJSON(studentMaleiro) as JsonObject)::class) //Verificar se retorna um JSONObject
        assertEquals("{\n" +  ////Verificar se retorna a serialização em JSON da primeira dataClass da lista
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
                "}",passJsonElementToTextual((getJSON(mutableListOf(studentMaleiro,studentAlfredo)) as JsonArray).value[0]))
    }
}

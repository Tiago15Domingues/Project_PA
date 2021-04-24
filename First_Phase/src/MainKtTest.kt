import kotlin.test.assertEquals
import findJsonObjectWithSpecificString as findJsonObjectWithSpecificString

internal class MainKtTest {

    @org.junit.jupiter.api.Test
    fun passJsonObjectToTextual() {
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
        assertEquals("{\n" +
                "\t\"id\": 123123,\n" +
                "\t\"name\": \"Tiago\",\n" +
                "\t\"male\": true,\n" +
                "\t\"smart\": null,\n" +
                "\t\"grades\": [\n" +
                "\t\t\"PASS\",\n" +
                "\t\t{\n" +
                "\t\t\t\"NOTAS PA\": [\n" +
                "\t\t\t\t30,\n" +
                "\t\t\t\t{\n" +
                "\t\t\t\t\t\"PA\": 10,\n" +
                "\t\t\t\t\t\"IAA\": \"18\"\n" +
                "\t\t\t\t}\n" +
                "\t\t\t],\n" +
                "\t\t\t\"NOTA PROG\": {\n" +
                "\t\t\t\t\"PA\": 10,\n" +
                "\t\t\t\t\"IAA\": \"18\"\n" +
                "\t\t\t}\n" +
                "\t\t},\n" +
                "\t\t[\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"PCD\": 19,\n" +
                "\t\t\t\t\"NOTAS\": {\n" +
                "\t\t\t\t\t\"PA\": 10,\n" +
                "\t\t\t\t\t\"IAA\": \"18\"\n" +
                "\t\t\t\t}\n" +
                "\t\t\t},\n" +
                "\t\t\tnull\n" +
                "\t\t],\n" +
                "\t\ttrue,\n" +
                "\t\t{\n" +
                "\t\t\t\"PA\": 10,\n" +
                "\t\t\t\"IAA\": \"18\"\n" +
                "\t\t}\n" +
                "\t],\n" +
                "\t\"pet\": {\n" +
                "\t\t\"animalID\": 123123,\n" +
                "\t\t\"name\": \"Peka\",\n" +
                "\t\t\"petHome\": {\n" +
                "\t\t\t\"town\": \"MirandaDoDouro\",\n" +
                "\t\t\t\"born\": \"MirandaDoDouro\"\n" +
                "\t\t}\n" +
                "\t},\n" +
                "\t\"home\": {\n" +
                "\t\t\"town\": \"Almada\",\n" +
                "\t\t\"born\": \"Almada\"\n" +
                "\t}\n" +
                "}",passJsonElementToTextual(jsonObject))
    }

    @org.junit.jupiter.api.Test
    fun findStrings() {
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
        assertEquals(mutableListOf("Tiago","PASS","18","Peka","MirandaDoDouro","Almada"),findAllStrings(jsonObject))
    }

    @org.junit.jupiter.api.Test
    fun findObjectWithSpecificString() {
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
        println(jsonObject1)
        assertEquals(mutableListOf(jsonObject1), findJsonObjectWithSpecificString(jsonObject,"Peka"))
    }
}
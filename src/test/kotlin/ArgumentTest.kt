import org.junit.Test

import org.junit.Assert.*


class ArgumentTest {

    @Test
    fun testEval() {
        VariableManager["var123"] = "657849"
        assertEquals(Argument("'dwedwe\$var123'").eval(), "dwedwe\$var123")
    }

    @Test
    fun testSubstituteVariables() {
        VariableManager["var123"] = "657849"
        assertEquals(Argument("\"\$var123\"").eval(), "657849")
        assertEquals(Argument("\"212121\$var123\"").eval(), "212121657849")
    }
}
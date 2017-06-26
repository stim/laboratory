package nl.tbvh.lab.puz.dzone;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ChemicalSymbolTest {

    private String element;
    private String symbol;
    private boolean valid;

    ChemicalSymbol chemicalSymbol = new ChemicalSymbol();

    @Parameters(name = "{index}: {0}-{1}")
    public static List<Object[]> parameters() {
        // @formatter:off
        return Arrays.asList(new Object[][] {
            {"Spenglerium", "Ee" , true },
            {"Zeddemorium", "Zr" , true },
            {"Venkmine", "Kn" , true },
            {"Stantzon", "Zt" , false },
            {"Melintzum", "Nn" , false },
            {"Tullium", "Ty" , false },
            {"Tullium", "Mu" , false },
            {"Tullium", "Ll" , true },
        });
        // @formatter:on
    }

    public ChemicalSymbolTest(String element, String symbol, boolean valid) {
        this.element = element;
        this.symbol = symbol;
        this.valid = valid;
    }

    @Test
    public void testSymbolChecker() {
        boolean checked = chemicalSymbol.check(element, symbol);
        assertEquals(valid, checked);
    }
}

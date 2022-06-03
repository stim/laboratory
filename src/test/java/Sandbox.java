import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Sandbox {

    public static void main(String[] args) {

        // DecimalFormat df = new DecimalFormat("#.###,00");
        NumberFormat nf = NumberFormat.getInstance(new Locale("nl"));
        NumberFormat bef = NumberFormat.getInstance(new Locale("be"));

        System.out.println(nf);
        System.out.println(bef);

        List<BigDecimal> bds = new ArrayList<>();

        bds.add(BigDecimal.valueOf(44.543));
        bds.add(BigDecimal.valueOf(44));
        bds.add(BigDecimal.valueOf(44.44));
        bds.add(BigDecimal.valueOf(0));
        bds.add(BigDecimal.valueOf(-10));
        bds.add(BigDecimal.valueOf(-10.43));
        bds.add(BigDecimal.valueOf(123456.43));

        for (BigDecimal bigDecimal : bds) {
            System.out.println(nf.format(bigDecimal));
        }
    }
}

import org.junit.Test;

import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

public class StringReassemblyTest {

    @Test
    public void test1() {
        SimpleWriter out = new SimpleWriter1L();
        String text = "hello~";
        StringReassembly.printWithLineSeparators(text, out);
    }

}

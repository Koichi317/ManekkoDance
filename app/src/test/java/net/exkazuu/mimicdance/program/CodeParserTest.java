package net.exkazuu.mimicdance.program;

import net.exkazuu.mimicdance.BuildConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class CodeParserTest {
    @Test
    public void parseSimpleProgram() {
        String code = "左腕を上げる右腕を上げる\n左腕を下げる\n右腕を下げる";
        Block block = CodeParser.parse(code);
        assertThat(block.unroll(true).getCode(), is(code));
    }

    @Test
    public void parseLoopProgram() {
        String code = "くりかえし2\n左腕を上げる右腕を上げる\n右腕を下げる\nここまで";
        String unrolledCode =
            "左腕を上げる右腕を上げる\n右腕を下げる\n左腕を上げる右腕を上げる\n右腕を下げる";
        Block block = CodeParser.parse(code);
        assertThat(block.unroll(true).getCode(), is(unrolledCode));
    }
}

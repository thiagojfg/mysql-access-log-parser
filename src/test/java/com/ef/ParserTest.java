package com.ef;

import org.junit.Test;

public class ParserTest {

    @Test
    public void testMain() throws Exception {

        String[] args = ConfigTest.getValidArgs();

        Parser.main(args);
    }
}

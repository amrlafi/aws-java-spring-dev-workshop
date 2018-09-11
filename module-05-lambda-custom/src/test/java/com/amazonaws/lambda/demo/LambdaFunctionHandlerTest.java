package com.amazonaws.lambda.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.lambda.LambdaCustomHandler;
import com.amazonaws.lambda.io.CustomEventInput;
import com.amazonaws.lambda.io.CustomEventOutput;
import com.amazonaws.services.lambda.runtime.Context;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class LambdaFunctionHandlerTest {

    private static CustomEventInput input;

    @BeforeClass
    public static void createInput() throws IOException {
        // TODO: set up your sample input object here.
        input = null;
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }

    @Test
    public void testLambdaFunctionHandler() {
        LambdaCustomHandler handler = new LambdaCustomHandler();
        Context ctx = createContext();
        
        List<Integer> list = new ArrayList();
        list.add(1);
        list.add(5);
        list.add(7);
        input = new CustomEventInput();
        input.setValues(list);
       
        int max = 7;
        CustomEventOutput output = handler.handleRequest(input, ctx);

        // TODO: validate output here if needed.
        Assert.assertEquals((int) max, (int)output.getValue());
    }
}

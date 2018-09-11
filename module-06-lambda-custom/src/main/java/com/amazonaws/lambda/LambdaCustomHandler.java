package com.amazonaws.lambda;

import com.amazonaws.lambda.io.CustomEventInput;
import com.amazonaws.lambda.io.CustomEventOutput;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class LambdaCustomHandler implements RequestHandler<CustomEventInput, CustomEventOutput> {

    @Override
    public CustomEventOutput handleRequest(CustomEventInput input, Context context) {
        context.getLogger().log("Input: " + input);

        int maxValue = Integer.MIN_VALUE;
        for (Integer value : input.getValues()) {
            if (value > maxValue) {
                maxValue = value;
            }
        }
        return new CustomEventOutput(maxValue);
    }

}

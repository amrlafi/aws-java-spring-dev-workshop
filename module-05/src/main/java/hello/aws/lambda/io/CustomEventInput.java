package hello.aws.lambda.io;

import java.util.List;

public class CustomEventInput {
    private List<Integer> values;
    public List<Integer> getValues() {
        return values;
    }
    public void setValues(List<Integer> values) {
        this.values = values;
    }
}

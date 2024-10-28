package school.sptech.apimiseenplace.enums;

public enum ELambdaFunction {


    LAMBDA_FUNCTION_NAME("arn:aws:lambda:us-east-1:415450303439:function:vini-o-brabo-lambda"),
    BUCKET_NAME("vini-o-brabo");


    private final String value;

    ELambdaFunction(String value) {
        this.value = value;
    }



    public String getValue() {
        return value;
    }


}

package school.sptech.apimiseenplace.enums;

public enum ELambdaFunction {


    LAMBDA_FUNCTION_NAME("arn:aws:lambda:us-east-1:701215625772:function:sobeParaS3"),
    BUCKET_NAME("bucket-teste-mise");


    private final String value;

    ELambdaFunction(String value) {
        this.value = value;
    }



    public String getValue() {
        return value;
    }


}

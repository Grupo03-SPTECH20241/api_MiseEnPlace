package school.sptech.apimiseenplace.enums;

public enum ELambdaFunction {


    LAMBDA_FUNCTION_NAME("arn:aws:lambda:us-east-1:532779703639:function:sobeParaS3"),
    BUCKET_NAME("leandro-bucket-01");


    private final String value;

    ELambdaFunction(String value) {
        this.value = value;
    }



    public String getValue() {
        return value;
    }


}

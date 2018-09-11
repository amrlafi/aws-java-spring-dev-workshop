rm -rf ./temp/* 
mvn compile package -Dmaven.test.skip=true
cd temp
cp ../target/module-05-lamdba-api-sam-1.0.0.jar .
unzip *.jar
rm *.jar
cp ../sam-api-lambda.yaml .
aws cloudformation package --template ./sam-api-lambda.yaml --s3-bucket seon-virginia-01 --output-template template-export.yml
aws cloudformation deploy --template-file ./template-export.yml --stack-name test-hello-02 --capabilities CAPABILITY_IAM

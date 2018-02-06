mvn clean install
cp ./application.properties ./target
cd ./target
java  -jar QA-Dashboard.war 


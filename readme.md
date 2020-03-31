# Stock Application with spring boot
Restful java based backend application to create ,update , fetch stock data and also track stock prices.

####Features
1)Backend and frontend implemented
2)Authorization with JWT.
3)persisting data in h2 database
4)Dockerizing the solution
5)Unit test,Swagger documentation(http://localhost:8080/swagger-ui.html)

###Technology Used
Java 8,Spring Boot,Junit4, Docker,Spring JPA,JWT,H2 Database

#Running the solution
docker build -t payconiq-stock .
docker run -p 8080:8080 payconiq-stock

#Resources
GET- /api/stocks:return a list of stocks
GET /api/stocks/{id} return one stock from the list
GET api/stocks/{id}/history return the historical price changes of a stock
PUT /api/stocks/{id} update the price of a single stock
POST /api/stocks create a stock
POST /authenticate to authenticate user and generate a JWT.

###ScreenShot
Show All Stocks:

![ScreenShot](https://github.com/shubgene/uploadFileWithSpringBootMySQLDocker/blob/master/screenshot1.png)

Click on Add Stock:

![ScreenShot](https://github.com/shubgene/uploadFileWithSpringBootMySQLDocker/blob/master/screenshot2.png)

update price of a stock on clicking on stock link:
![ScreenShot](https://github.com/shubgene/uploadFileWithSpringBootMySQLDocker/blob/master/screenshot2.png)

See Stock history on clicking show history:
![ScreenShot](https://github.com/shubgene/uploadFileWithSpringBootMySQLDocker/blob/master/screenshot2.png)
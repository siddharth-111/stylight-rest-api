# Pinguin  Coding Challenge

## Content
- Prerequisites
- Running the application
- API Documentation
- Further Implementation and open discussion

## Prerequisites
- Maven 3.8+
- Java JDK 17+

## Running the application
- Navigate to the root folder of the application
- Run mvn 'spring-boot:run'
- The server should run on localhost:8080

## API Documentation

### Create Mapping
This API is used to create a mapping between ordered parameters and pretty url

#### API Signature
POST - {http/s}://{server}:{port}/stylight/api/urls

#### API Sample
POST - http://localhost:8080/stylight/api/urls
Request Body:
{
"orderedParameter": "/products?gender=female&tag=123",
"prettyUrl": "/Women/Adidas"
}

#### Response data
{
"orderedParameter": "/products?gender=female&tag=123&tag=543",
"prettyUrl": "/Women/Adidas/Shoes"
}

### Get Mapped pretty urls 
This API is used to get the mapped pretty urls for given ordered parameters 

#### API Signature
GET - {http/s}://{server}:{port}/stylight/api/urls/prettyUrl

#### API Sample
GET - http://localhost:8080/stylight/api/urls/prettyUrl
Request Body:
["/products", "/products?gender=female", "/products?gender=female&tag=123&tag=1234&tag=5678", "/products?brand=123&tag=new", "/products?tag=new", "/"]

#### Response data
[
{
"orderedParameter": "/products",
"prettyUrl": "/Fashion"
},
{
"orderedParameter": "/products?gender=female",
"prettyUrl": "/Women/"
},
{
"orderedParameter": "/products?gender=female&tag=123&tag=1234&tag=5678",
"prettyUrl": "/Women/Shoes/?tag=5678"
},
{
"orderedParameter": "/products?brand=123&tag=new",
"prettyUrl": "/Adidas/?tag=new"
},
{
"orderedParameter": "/products?tag=new",
"prettyUrl": "/Fashion?tag=new"
},
{
"orderedParameter": "/",
"prettyUrl": "/"
}
]

### Get Mapped ordered parameters
This API is used to get the mapped ordered parameters for given pretty urls

#### API Signature
GET - {http/s}://{server}:{port}/stylight/api/urls/orderedParameters

#### API Sample
GET - http://localhost:8080/stylight/api/urls/orderedParameters
Request Body:
["/Fashion", "/Adidas/", "/Women/Shoes/", "/Women/Shoes/Adidas/Footwear", "/Nike/Women", "/"]

#### Response data
[
{
"orderedParameter": "/products",
"prettyUrl": "/Fashion"
},
{
"orderedParameter": "/products?brand=123",
"prettyUrl": "/Adidas/"
},
{
"orderedParameter": "/products?gender=female&tag=123&tag=1234",
"prettyUrl": "/Women/Shoes/"
},
{
"orderedParameter": "/products/Adidas/Footwear?gender=female&tag=123&tag=1234",
"prettyUrl": "/Women/Shoes/Adidas/Footwear"
},
{
"orderedParameter": "/Nike/Women",
"prettyUrl": "/Nike/Women"
},
{
"orderedParameter": "/",
"prettyUrl": "/"
}
]

## Further Implementation and open discussion
- Implement validation for input ordered parameters/pretty urls.
- Scale the application by building microservices to lookup urls/parameters within certain range.
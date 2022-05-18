
I have used SpringBoot framework with H2 database.

1. We need to import the project and do the maven clean build.
2. Run the project in the local and hit the http://localhost:8080/swagger-ui.html#/building-controller
3. First better to add some building details so we can check other api's.
Example request for adding building (/property/buildings):

[
  {
    "buildingname": "A",
    "city": "Lappeenranta",
    "country": "Finland",
    "description": "house",
    "number": 2,
    "postalcode": 53900,
    "streetname": "Taipalsaarentie"
  },
{
    "buildingname": "Prisma",
    "city": "Lappeenranta",
    "country": "Finland",
    "description": "Super Market",
    "number": 30,
    "postalcode": 53900,
    "streetname": "Satamatie"
  }
]
4. For updating the detail of a building, I have taken postal code and building number as to search from database so to update other details of the building.
But I have not added any validation check yet.
5. While fetching all the list of the buildings we can specify pageNo,pageSize,sortBy as request parameter. 
6. I have added junit test cases for controller, service , repository java class.



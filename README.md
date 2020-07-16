![Continuos Integration](https://github.com/CharlesLuxinger/file-scan-api/workflows/Continuos%20Integration/badge.svg)
# File Scan API

A test project with the objective of checking some Github repository and returning the number of bits and number of lines per group of type files.

## Testing Api
   
   There are 2 access options Heroku Server or run local.
   
   * Heroku Server
   
   The Api Swagger is available in:
   
       http://file-scan-api.herokuapp.com/api/v1
       
   Endpoint test is available in:
       
       http://file-scan-api.herokuapp.com/api/v1/repository
       
   * Run local
   
   If you prefer run local project make sure that Docker and Docker Compose are installed on your computer, run in the root project:

    docker-compose up -d
      
   The Api Swagger is available in:

    http://localhost:9009/api/v1
    
   Endpoint test is available in:
    
    http://localhost:9009/api/v1/repository

## Schema
   
   * Request body:
   
    {
        "url": "https://github.com/CharlesLuxinger/file-scan-api"
    }
    
   * Success Response payload:
   
    [
        {
            "fileType": "file",
            "lines": 1,
            "bytes": 123
        }
    ]
    
   * Error Response payload:
   
    {
        "path": "/api/v1/resource/1"
        "detail": "Some network cable was broken."
        'title': "Network crashes"
        "status": 999
        'timestamp': '2020-04-24T19:27:01.718Z'
    }

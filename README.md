# File Scan API

A test project with the objective of checking some Github repository and returning the number of files and the number of lines per group of files.

## Running Api
   
   Make sure Docker and Docker Compose are installed on your computer, and running:

    docker-compose up -d
    
   The Api Swagger is available in:

    http://localhost:9009/api/v1/
    
   Endpoint test is available in:
    
    http://localhost:9009/api/v1/repository
   
   Request body:
   
    {
        "url": "https://github.com/CharlesLuxinger/file-scan-api"
    }
    
   Response payload:
   
    [
        {
            "fileType": "file",
            "lines": 1,
            "bytes": 123
        }
    ]

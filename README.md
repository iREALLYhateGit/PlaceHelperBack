# PlaceHelper backend service
Java Micronaut server with REST API and PostgreSQL db.

<a id="default-config"></a>
**Default config is:**
- localhost:8080
- db connection:
  - url: jdbc::postgresql:://localhost:2004/placesdb
  - user: placesdbadmin
  - password: placesdbadminpassword

## How to run

### First step (Start DB instance)

Before starting Micronaut server you need to start PostgreSQL instance with the config provided in [Default config section](#default-config)

### Second step (Start server)

To run server use the following command from **backend** folder:
```bash
  ./gradlew.bat run
```

## Interaction

Http requests over Http 1.*

**Requests:**
- GET /places<br>
  Retrieves all places.<br>
  Possible results:
  - OK (code = 200):
    JSON with places in Response Body.

- GET /places/{id}<br>
  Retrieves place with specified id.<br>
  Possible results:
  - OK (code = 200):<br>
    JSON with only one place in Response Body.
  - NOT_FOUND (code = 404):<br>
    Response Body is null.

- POST /places<br>
  Obtains JSON Body with new place and saves it.<br>
  Possible results:
  - CREATED (code = 201):<br>
    JSON with only one place in Response Body.

- PUT /places/{id}<br>
  Updates specified place according to obtained JSON.<br>
  Possible results:
  - OK (code = 200):<br>
    JSON with the updated place in Response Body.
  - NOT_FOUND (code = 404):<br>
    Response Body is null.

- DELETE /places/{id}<br>
  Deletes specified place.<br>
  Possible results:
    - OK (code = 200):<br>
      JSON with the deleted place in Response Body.
    - NOT_FOUND (code = 404):<br>
      Response Body is null.


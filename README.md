Hæ
Hæ

Render link: https://hugbunadarverkefni-weog.onrender.com

GET "/" - returns all movies that are currently showing

GET "/movies" - returns all movies in the databaxse

GET "/movies/:id" - returns a specific movie by id

POST "/movies" - create a new movie
{"title": "Inception","genre": "Sci-Fi","ageRating": "PG-13","duration": 148, }

DELETE "/movies/:id" - delete a specific movie by id

PATCH "/movies/:id" - update a specific movie by id

POST "/auth/login" login - returns a token
{"email": "admin@example.com","password": "Admin123"}

POST "/auth/signup"
{"email": "","password": ""}

GET "/auth/profile" - returns the currently logged in user

PATCH /auth/profile - update user profile 

PATCH /auth/profile/picture - update user profile picture

GET "/moviehalls" - returns all movie halls

POST "/moviehalls" - create a new movie hall

PATCH "/moviehalls/:id" - update a movie hall

DELETE "/moviehalls/:id" - delete a movie hall

POST http://localhost:8080/bookings
{
  "movieId": 1,
  "hallId": 1,
  "seatId": 5
}

GET http://localhost:8080/bookings

Merkja sæti sem bókað
PATCH http://localhost:8080/seats/2/status?booked=true

Sækja öll sæti fyrir sal
GET http://localhost:8080/seats/hall/1

Bæta við sæti
POST http://localhost:8080/seats/add?hallId=1&rowNumber=2&seatNumber=5



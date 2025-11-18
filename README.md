Hæ
Hæ

# Movie Theater Web Application

## Render link: https://hugbunadarverkefni-weog.onrender.com

## Sprint 1

### Use case 1: Sign in to website

POST "/auth/login" login - returns a token
{"email": "admin@example.com","password": "Admin123"}

---

### Use case 7: Manage movies

DELETE "/movies/:id" - delete a specific movie by id

PATCH "/movies/:id" - update a specific movie by id

---

### Use case 8: Create account

POST "/auth/signup"
{"email": "","password": ""}

---

### Use case 16: Add new movies

POST "/movies" - create a new movie: <br>
{"title": "Inception","genre": "Sci-Fi","ageRating": "PG-13","duration": 148, }

---

## Sprint 2

### Use case 2: Manage user profile with a profile picture

PATCH /auth/profile/picture - update user profile picture

PATCH /auth/profile - update user profile

---

### Use case 11: Browse available movies

GET "/" - returns all movies that are currently showing

GET "/movies" - returns all movies in the database

GET "/movies/:id" - returns a specific movie by id

---

### Use case 15: Add a movie poster image to a movie

PATCH "/movies/{movieId}/picture" - update movie poster image

### Use case 17: Modify a profile picture

PATCH /auth/profile/picture - update user profile picture

---

## Sprint 3

### Use case 9: Filter movies by genre

GET "/movies/genre/{genre}" - filter movies by genre

### Use case 3: Invite friends to a movie

POST "/friends/invite" {"email": "","movieId": 1}

### Use case 5: Cancel booking

DELETE /bookings/{bookingId} 

### Use case 10: Delete account

DELETE /profile/delete

---

## Sprint 4

### Use case 4: Apply discount code

POST http://localhost:8080/bookings
{
"movieId": 1,
"hallId": 1,
"seatId": 5
"Discount Code": "STUDENT20"
}


Sjá Discount kóða
GET http://localhost:8080/discounts

### Use case 6: Add friends

POST /friends/request {"email": ""}

Accept request 
POST /friends/request/{id}/accept

Reject request
POST /friends/request/{id}/reject

### Use case 12: Mark favorite movies

Bæta við favorite
POST http://localhost:8080/favorites/movie/1

### Use case 13: Unmark favorite movies

DELETE /favorites/{movieId}

### Use case 14: View favorite movies

fá favorite
GET http://localhost:8080/favorites

### Use case 18: View a friends profile

GET /users/{Email}/profile

Sjá vini <br>
GET friends/list

---

## Other Endpoints

GET "/auth/profile" - returns the currently logged in user

GET "/moviehalls" - returns all movie halls

POST "/moviehalls" - create a new movie hall

PATCH "/moviehalls/:id" - update a movie hall

DELETE "/moviehalls/:id" - delete a movie hall


GET http://localhost:8080/bookings

Merkja sæti sem bókað
PATCH http://localhost:8080/seats/2/status?booked=true

Sækja öll sæti fyrir sal
GET http://localhost:8080/seats/hall/1

Bæta við sæti
POST http://localhost:8080/seats/add?hallId=1&rowNumber=2&seatNumber=5&price=2500

Bæta við screening
POST http://localhost:8080/screenings?screeningTime=2025-10-14T20:00:00

fá screening
GET http://localhost:8080/screenings

Fá sent invite
GET /movies/invitations/sent





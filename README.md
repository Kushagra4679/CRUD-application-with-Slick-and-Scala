# Movie CRUD Application

This is a simple CRUD (Create, Read, Update, Delete) application for managing movies in a PostgreSQL database inside a Docker container. The application is built using *Slick*, a reactive database library for *Scala*, and is containerized using Docker for easy deployment.

## Prerequisites

Make sure you have the following installed on your machine:

- Docker Desktop

## Setup

1. **Clone the repository:**

   ```bash
   git clone https://github.com/Kushagra4679/CRUD-application-with-Slick-and-Scala.git

   cd CRUD-application-with-Slick-and-Scala

   docker compose up

   docker ps

2. **Now copy the name of DB which is spinning**

   ```bash
   docker exec -it db_name psql -U postgres
   
   select * from movies."Movie";

3. **Keep tinkering with the queries to get your desired results**


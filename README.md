# storage-service

Storage service is a Spring Boot-based microservice for managing users' storage.

## Server port: 8081

## Requirements

- Java version 17.0.7
- Spring Boot 3.1.3
- Maven 3.9.1

## Technologies Used

1. Java 17
2. Spring Boot

## Application Features

The Storage service manages user's storage by providing the following features:

- Base folder creation in the root directory of container.
- Managing user's folders in the base folder.
- Performing file transferring.
## Running the Application with Docker

To run the Storage service with Docker, follow these steps:

1. Ensure that Docker and Docker Compose are installed on your system.
2. Clone the repository and navigate to the project directory.
3. Run in `cmd`
   ```sh
      mvn clean install
   ```
4. Build the Docker image by running the following command. Make sure to replace the placeholders `<your_*>` with your
   actual values:

   ```sh
   docker build \
       --build-arg DB_HOST=<your_db_host> \
       --build-arg DB_USER=<your_db_user> \
       --build-arg DB_PASSWORD=<your_db_password> \
       --build-arg EUREKA_URL=<your_eureka_url> \
       --build-arg RABBITMQ_HOST=<your_rabbitmq_host> \
       --build-arg STORAGE_BASE_PATH=<your_base_folder_path> \
       --build-arg RABBITMQ_MOVE_QUEUE_NAME=<your_rabbitmq_move_queue_name> \
       --build-arg RABBITMQ_EXCHANGE_NAME=<your_rabbitmq_exchange_name> \
       --build-arg RABBITMQ_ROUTING_KEY=<your_rabbitmq_routing_key> \
       -t storage-service .
    ```
http://localhost:8081/swagger-ui/index.html
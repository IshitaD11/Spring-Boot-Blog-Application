# Spring-Boot-Blog-Application

A comprehensive blogging platform built with Spring Boot, designed for users to post, edit, and view solutions to Data Structures and Algorithms (DSA) problems. This project includes user registration, authentication, personalized profiles, CKEditor for rich text editing, Amazon S3 for image storage, and Gmail SMTP for sending emails
The website is hosted [here](https://algoblog.onrender.com/).


## Features
- User registration and secure login
- Create, edit, and delete blog posts with CKEditor
- View all posts
- User profile personalization
- Tag-based search and filtering of posts
- Image storage using Amazon S3
- Gmail SMTP for sending emails on the Contact Us page and for password recovery
- Responsive design for optimal viewing on all devices

## Post Structure
Each post includes:

- Title: The title of the DSA problem
- URL: A link to the problem statement on an external website
- Problem Statement: A detailed description of the DSA problem copied from the external website
- Content: An explanation of the solution approach
- Code Block: A formatted code snippet demonstrating the solution

## Prerequisites
Make sure you have Java (JDK), Maven, Docker, and any database (MySQL or PostgreSQL) installed on your system.
Additionally, set up an AWS S3 bucket and a Gmail account for SMTP.


## Project Setup

1. Clone the repository:
    
    `git clone https://github.com/IshitaD11/Spring-Boot-Blog-Application`

2. Navigate to the project directory:

    `cd spring-boot-blog-application`

3. Configure the database:

   Set your database configuration in application.properties file or in Environment variable.

    ```
   spring.datasource.url=${DB_URL}
   spring.datasource.username=${DB_USERNAME}
   spring.datasource.password=${DB_PASSWORD}
   ```
   
4. Configure Amazon S3:

    Add your AWS credentials and S3 bucket details in the application.properties or Environment variable.

    ```
    aws.access.key.id=${AWS_ACCESS_KEY_ID}
    aws.secret.access.key=${AWS_SECRET_ACCESS_KEY}
    aws.s3.bucket.name=${AWS_BUCKET_NAME}
    aws.s3.region=${AWS_REGION}
   ```
   
5. Configure Gmail SMTP:

   Add your Gmail ID and passwords in the application.properties or environment variable.

    ```
    spring.mail.username=${EMAIL_ID}
    spring.mail.password=${EMAIL_PW}
   ```
   
6. Install dependencies and build the project:

    `mvn clean install`

7. Build the Docker image with passing all environment variables (if used) and a image name:

   ```
   docker build -t <Image_Name> 
   --build-arg AWS_ACCESS_KEY_ID=<AWS_ACCESS_KEY_ID> 
   --build-arg AWS_BUCKET_NAME=<AWS_BUCKET_NAME> 
   --build-arg AWS_REGION=<AWS_REGION>
   --build-arg AWS_SECRET_ACCESS_KEY=<AWS_SECRET_ACCESS_KEY>
   --build-arg DB_URL=<DB_URL>
   --build-arg DB_USERNAME=<DB_USERNAME>
   --build-arg DB_PASSWORD=<DB_PASSWORD>
   --build-arg EMAIL_ID=<DB_PASSWORD>
   --build-arg EMAIL_PW=<EMAIL_PW> 
   ```

8. Run the Docker container passing all environment variables (if used) and the image name used in previous step:

   ```
   docker run -p 8080:8080
   --build-arg AWS_ACCESS_KEY_ID=<AWS_ACCESS_KEY_ID>
   --build-arg AWS_BUCKET_NAME=<AWS_BUCKET_NAME>
   --build-arg AWS_REGION=<AWS_REGION>
   --build-arg AWS_SECRET_ACCESS_KEY=<AWS_SECRET_ACCESS_KEY>
   --build-arg DB_URL=<DB_URL>
   --build-arg DB_USERNAME=<DB_USERNAME>
   --build-arg DB_PASSWORD=<DB_PASSWORD>
   --build-arg EMAIL_ID=<DB_PASSWORD>
   --build-arg EMAIL_PW=<EMAIL_PW> 
   <Image_Name>
   ```


## Deploying the Website
To deploy your Spring Boot application with PostgreSQL using a free platform like Render, follow these steps:

1. Create a PostgreSQL Database: Sign up for Render's free tier and create a new PostgreSQL database. Add the database credentials to your project's configuration.
2. Deploy your Website: Create a new website on Render and link your Git repository for continuous deployment. Pass your environment variables while setup. This will use your Docker file to build the project.


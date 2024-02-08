# 🎵 Music-app

## Description

The music project is a comprehensive solution based on Java technology and a set of popular frameworks and tools, such as Spring, Hibernate, JUnit, Mockito, Docker, Apache Kafka, and Spring Security. The main goal of the project is to create a music application, with features including interaction with the PayPal and Spotify Rest API, ensuring user security through the use of JWT token, PreAuthorize, 2FA, and user acceptance via email. The main functionality is resource management for the user, premium user, and admin. Only premium users will have the right to create their own music (using the DJ interface in the application), becoming the author of the music, for which they could earn money with each play by other users. The next functionality of the project will be the ability to create your own music, which will undergo verification (implementation of AI, which would check whether the music is correct, i.e., does not contain prohibited words, and does not discriminate against anyone). Many other features will be visible as the project progresses.

## 🚀 Technologies

- Java
- Spring Framework
- Hibernate
- JUnit
- Mockito
- Docker
- Apache Kafka
- Spring Security

## 🎶 Features implemented:

- **User registration:** Full authentication, implementing complete user security.
- **User authorization:** Implemented using Spring Security and JWT token.
- **Connection with Spotify Rest API:** Allows interaction with music data on the Spotify platform.
- **Error handling:** Utilization of custom Enum Exceptions to handle HTTP errors.
- **Data modeling using Hibernate:** Data models are connected and interact, enabling efficient use of Hibernate.
- **Layered architecture:** Project based on a layered architecture, ensuring clarity and scalability.
- **User permissions:** Utilization of Spring Security to assign appropriate permissions to users, such as User, UserPremium, and Admin (Use of the Permission class created by me in the project).
- **Uploading and downloading music files:** Function enabling users to upload and download music files has been implemented.
  
## 🔨 Features currently in progress:

- **User activation:** Implements functionality allowing user account activation by generating a link upon registration (activateToken from JWTServis, generating a link) and sending it by email (The user will be in 3 states, depending on whether they clicked the link or not).

## 🛠️ Next feature planned for implementation

- **Two-Factor Authentication (2FA):** Implementation of two-factor authentication, allowing users to secure their accounts through an additional authentication step, generating QR codes, and backup codes for each user.

## 🚀 Installation
1. Clone the repository: `git clone https://github.com/Marawie/music-app`
2. Run the project using Docker Compose: `docker-compose up -d`
3. Open a browser and go to [http://localhost:8080](http://localhost:8080)
 
## ✍️ Author
**Marek Marczak** - Project Creator

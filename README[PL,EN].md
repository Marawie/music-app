## English version ##

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


...



## Polish version ##

# 🎵 Music-app

## Opis

Projekt muzyczny to kompleksowe rozwiązanie oparte na technologii Java i szeregu popularnych frameworków i narzędzi, takich jak Spring, Hibernate, JUnit, Mockito, Docker, Apache Kafka i Spring Security. Głównym celem projektu jest stworzenie aplikacji muzycznej, której funkcje obejmują interakcję z Rest API platformy PayPal oraz Spotify, dbając przy tym o bezpieczeństwo użytkownika, wykorzystując (JWT token, PreAuthorize, 2FA, akceptacja użytkownika za pomocą email). Główną funkcjonalnością jest rozporządzenie zasobami dla użytkownika, użytkownika premium, admina. Tylko użytkownik premium będzie miał prawo tworzyć własną muzykę (za pomocą interfejsu DJ w aplikacji), tworząc muzykę będzie jej autorem, za którą mógłbym dostawać pieniądze wraz z każdym odtworzeniem jej innego użytkownika. Następną funkcjonalnością projektu będzie możliwość tworzenia własnej muzyki, która będzie przechodzić przez weryfikację (implementacja AI, który miałby sprawdzać, czy muzyka jest prawidłowa, tzn. nie zawiera zakazanych słów, w kontekście nie dyskryminuje kogokolwiek). Wiele innych funkcji, które zobaczycie wraz z postępem projektu.

## 🚀 Technologie

- Java
- Spring Framework
- Hibernate
- JUnit
- Mockito
- Docker
- Apache Kafka
- Spring Security

## 🎶 Funkcje zrealizowane:

- **Rejestracja użytkownika:** Pełna autentykacja, implementująca pełne bezpieczeństwo użytkownika.
- **Autoryzacja użytkownika:** Realizowana za pomocą Spring Security i JWT tokenu.
- **Połączenie z Rest API Spotify:** Umożliwia interakcję z danymi muzycznymi na platformie Spotify.
- **Obsługa błędów:** Wykorzystanie własnych Enum Exception do obsługi błędów HTTP.
- **Modelowanie danych z wykorzystaniem Hibernate:** Modele danych są połączone ze sobą i współgrają, co umożliwia efektywne korzystanie z Hibernate.
- **Architektura warstwowa:** Projekt oparty na architekturze warstwowej, co zapewnia czytelność i skalowalność.
- **Uprawnienia użytkowników:** Wykorzystanie Spring Security do nadawania odpowiednich uprawnień użytkownikom, takich jak User, UserPremium i Admin (Wykorzystanie klasy Permission stworzonej przeze mnie w projekcie).
- **Przesyłanie i pobieranie plików muzycznych:** Funkcja umożliwiająca użytkownikom przesyłanie i pobieranie plików muzycznych została zaimplementowana.
  
## 🔨 Funkcje obecnie w trakcie realizacji:

- **Aktywacja Użytkownika:** Implementuje funkcjonalność umożliwiającą aktywację konta użytkownika poprzez generowanie linku przy dokonanej rejestracji (token od JWTServisu (activateToken), generujący link) i wysyłanie go mailem (Użytkownik będzie w 3 stanach, w zależności od tego, czy kliknął na link czy nie).

## 🛠️ Następna funkcja planowana do zaimplementowania

- **Dwuetapowa Weryfikacja Tożsamości (2FA):** Implementacja dwuetapowej weryfikacji tożsamości, która umożliwi użytkownikom zabezpieczenie konta poprzez dodatkowy etap autentykacji, generujący przy tym kody QR, oraz zapasowe dla każdego użytkownika.

## 🚀 Instalacja
1. Sklonuj repozytorium: `git clone https://github.com/Marawie/music-app`
2. Uruchom projekt za pomocą Docker Compose: `docker-compose up -d`
3. Otwórz przeglądarkę i wejdź na stronę [http://localhost:8080](http://localhost:8080)
 
## ✍️ Autor
**Marek Marczak** - Twórca projektu

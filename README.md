# video-rental

HOW TO RUN APPLICATION.

    Run the following in terminal:
        1. git clone https://github.com/wmifsud/video-rental
        2. cd video-rental
        3. mvn clean install
        4. ./target/video-rental.jar
        
    Application runs on port 8080 and can be amended from the application.properties file.
    
TESTING

    Run all tests under VideoRentalControllerTest.
    Additional testing can be done by importing the video-rental.postman_collection into postman.
    
Available RESTful APIs.

    http://localhost:8080/video-rental/{idCard}/returnFilms
    
    http://localhost:8080/video-rental/{idCard}/rentFilms/{days}
    
    http://localhost:8080/video-rental/retrieveAvailableFilms
    
    http://localhost:8080/video-rental/retrieveAllFilms
    
    http://localhost:8080/video-rental/calculateRent/{days}
    
    http://localhost:8080/video-rental/retrieveAllCustomers
    
FUTURE CONSIDERATIONS.

    Unit tests do not cover all test scenarios and need to be beefed up.
    
    Exception Handling is being handled through a controller advise but should be segragated 
    and have proper specific exception handling instead of throwing Runtime exceptions.
    
    Ability to create new customers and create new films through REST API.

Please include a README in any format about decisions you made along the way, what you focused on, what you didn't focus on and why, as well as how to run and use the program.
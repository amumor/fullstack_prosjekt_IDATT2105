describe("Login Functionality", () => {
    beforeEach(() => {
      cy.intercept("GET", "**/api/v1/listing/get-suggestions*", {
        statusCode: 200,
        body: {
          suggestions: [], 
        },
      }).as("getSuggestions");
  
      cy.intercept("GET", "**/api/v1/users/get-my-profile", {
        statusCode: 200,
        body: {
          email: "test@example.com",
          firstName: "John",
          lastName: "Doe",
          phoneNumber: "12345678",
        },
      }).as("getMyProfile");
  
      cy.visit("/login");
    });
  
    it("should login successfully with valid credentials", () => {
      cy.intercept("POST", "**/authenticate", {
        statusCode: 200,
        body: {
          token: "fake-jwt-token",
        },
      }).as("loginRequest");
  
      cy.get('input[placeholder="E-mail"]').type("test@example.com");
      cy.get('input[placeholder="Password"]').type("Password123!");
      cy.get(".basic-blue-btn").click();
  
      cy.wait("@loginRequest");
      cy.wait("@getMyProfile");
  
      cy.url().should("eq", `${Cypress.config().baseUrl}/`);
    });
  
    it("should not send the user to the homepage", () => {
      cy.intercept("POST", "**/authenticate", {
        statusCode: 401,
        body: { message: "Invalid credentials" },
      }).as("loginRequest");
  
      cy.get('input[placeholder="E-mail"]').type("wrong@example.com");
      cy.get('input[placeholder="Password"]').type("WrongPassword!");
      cy.get(".basic-blue-btn").click();


      cy.url().should("not.eq", `${Cypress.config().baseUrl}/`);
    });
  });
describe("Registration Functionality", () => {
  beforeEach(() => {
    cy.visit("/"); 
    cy.visit("/login"); // Visit the login page
    cy.contains("Sign up").click(); // Switch to the registration form
  });

  it("should register successfully with valid details", () => {
    // Stub the registration API response
    cy.intercept("POST", "**/register", {
      statusCode: 201,
      body: {
        token: "fake-jwt-token",
        email: "newuser@example.com",
        firstName: "Jane",
        lastName: "Doe",
        phoneNumber: "87654321",
      },
    }).as("registerRequest");

    // Fill in the registration form
    cy.get('input[placeholder="First name"]').type("Jane");
    cy.get('input[placeholder="Last name"]').type("Doe");
    cy.get('input[placeholder="E-mail"]').type("newuser@example.com");
    cy.get('input[placeholder="Phone number"]').type("87654321");
    cy.get('input[placeholder="Password"]').type("Password123!");
    cy.get('input[placeholder="Confirm password"]').type("Password123!");
    cy.get(".basic-blue-btn").click();

    // Wait for the stubbed API call
    cy.wait("@registerRequest");

    // Assert that the user is redirected to the home page
    cy.url().should("eq", `${Cypress.config().baseUrl}/`);
  });

    it("should not send the user to the homepage", () => {
        // Stub the registration API response with an error
        cy.intercept("POST", "**/register", {
        statusCode: 400,
        body: { message: "Registration failed" },
        }).as("registerRequest");
    
        // Fill in the registration form with invalid details
        cy.get('input[placeholder="First name"]').type("Jane");
        cy.get('input[placeholder="Last name"]').type("Doe");
        cy.get('input[placeholder="E-mail"]').type("invalid-email");
        cy.get('input[placeholder="Phone number"]').type("invalid-phone");
        cy.get('input[placeholder="Password"]').type("123");
        cy.get('input[placeholder="Confirm password"]').type("123");
        cy.get(".basic-blue-btn").click();
    
        // should not redirect to the home page
        cy.url().should("not.eq", `${Cypress.config().baseUrl}/`);
    });
});

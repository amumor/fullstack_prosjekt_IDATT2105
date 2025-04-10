describe("Login Functionality", () => {
  beforeEach(() => {
    cy.visit("/"); // Visit the base URL
    cy.visit("/login"); // Visit the login page
  });

  it("should login successfully with valid credentials", () => {
    // Stub the login API response
    cy.intercept("POST", "**/authenticate", {
      statusCode: 200,
      body: {
        token: "fake-jwt-token",
        email: "test@example.com",
        firstName: "John",
        lastName: "Doe",
        phoneNumber: "12345678",
      },
    }).as("loginRequest");

    // Fill in the login form
    cy.get('input[placeholder="E-mail"]').type("test@example.com");
    cy.get('input[placeholder="Password"]').type("Password123!");
    cy.get(".basic-blue-btn").click();

    // Wait for the stubbed API call
    cy.wait("@loginRequest");

    // Assert that the user is redirected to the home page
    cy.url().should("eq", `${Cypress.config().baseUrl}/`);
  });

  it("should show an error message for invalid credentials", () => {
    // Stub the login API response with an error
    cy.intercept("POST", "**/authenticate", {
      statusCode: 401,
      body: { message: "Invalid credentials" },
    }).as("loginRequest");

    // Fill in the login form with invalid credentials
    cy.get('input[placeholder="E-mail"]').type("wrong@example.com");
    cy.get('input[placeholder="Password"]').type("WrongPassword!");
    cy.get(".basic-blue-btn").click();

    // Wait for the stubbed API call
    cy.wait("@loginRequest");

    // Assert that the error message is displayed
    cy.contains("Try again.").should("be.visible");
  });
});

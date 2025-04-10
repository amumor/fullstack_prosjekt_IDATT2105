describe("Admin Settings and Category Management", () => {
  beforeEach(() => {
    cy.intercept("POST", "**/authenticate", {
      statusCode: 200,
      body: {
        token: "fake-admin-jwt-token",
      },
    }).as("loginRequest");

    cy.intercept("GET", "**/api/v1/users/get-my-profile", {
      statusCode: 200,
      body: {
        email: "admin@example.com",
        firstName: "Admin",
        lastName: "User",
        phoneNumber: "12345678",
        role: "ROLE_ADMIN",
      },
    }).as("getMyProfile");

    cy.intercept("GET", "**/api/v1/category/all", {
      statusCode: 200,
      body: {
        categories: [
          { id: "1", name: "Electronics" },
          { id: "2", name: "Books" },
        ],
      },
    }).as("getAllCategories");

    cy.visit("/login");
    cy.get('input[placeholder="E-mail"]').type("admin@example.com");
    cy.get('input[placeholder="Password"]').type("AdminPassword123!");
    cy.get(".basic-blue-btn").click();

    cy.wait("@loginRequest");
    cy.wait("@getMyProfile");

    cy.visit("/admin");
    cy.wait("@getAllCategories");
  });

  it("should display the admin settings page", () => {
    cy.contains("Admin settings").should("be.visible");
    cy.contains("Register Admin").should("be.visible");
  });

  it("should create a new category successfully", () => {
    cy.intercept("POST", "**/api/v1/category/create", {
      statusCode: 201,
      body: {
        id: "3",
        name: "Test",
      },
    }).as("createCategory");

    cy.get("#edit-btn").click();

    cy.get("#category-input").type("Test");
    cy.get("#new-category-btn").click();

    cy.wait("@createCategory");

    cy.get(".category-item").should("have.length", 4);
  });

  it("should delete a category successfully", () => {
    cy.intercept("DELETE", "**/api/v1/category/delete/1", {
      statusCode: 200,
    }).as("deleteCategory");

    cy.get("#edit-btn").click();

    cy.get("#delete-btn").first().click();

    cy.wait("@deleteCategory");

    cy.contains(".category-text", "Electronics").should("not.exist");
  });
});

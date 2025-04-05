/**
 * OpenAPI Documentation - find.no
 * OpenApi documentation for find.no
 *
 * The version of the OpenAPI document: 1.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 *
 */

(function(root, factory) {
  if (typeof define === 'function' && define.amd) {
    // AMD.
    define(['expect.js', process.cwd()+'/src/index'], factory);
  } else if (typeof module === 'object' && module.exports) {
    // CommonJS-like environments that support module.exports, like Node.
    factory(require('expect.js'), require(process.cwd()+'/src/index'));
  } else {
    // Browser globals (root is window)
    factory(root.expect, root.OpenApiDocumentationFindNo);
  }
}(this, function(expect, OpenApiDocumentationFindNo) {
  'use strict';

  var instance;

  beforeEach(function() {
    instance = new OpenApiDocumentationFindNo.ListingCreationRequestDTO();
  });

  var getProperty = function(object, getter, property) {
    // Use getter method if present; otherwise, get the property directly.
    if (typeof object[getter] === 'function')
      return object[getter]();
    else
      return object[property];
  }

  var setProperty = function(object, setter, property, value) {
    // Use setter method if present; otherwise, set the property directly.
    if (typeof object[setter] === 'function')
      object[setter](value);
    else
      object[property] = value;
  }

  describe('ListingCreationRequestDTO', function() {
    it('should create an instance of ListingCreationRequestDTO', function() {
      // uncomment below and update the code to test ListingCreationRequestDTO
      //var instance = new OpenApiDocumentationFindNo.ListingCreationRequestDTO();
      //expect(instance).to.be.a(OpenApiDocumentationFindNo.ListingCreationRequestDTO);
    });

    it('should have the property title (base name: "title")', function() {
      // uncomment below and update the code to test the property title
      //var instance = new OpenApiDocumentationFindNo.ListingCreationRequestDTO();
      //expect(instance).to.be();
    });

    it('should have the property description (base name: "description")', function() {
      // uncomment below and update the code to test the property description
      //var instance = new OpenApiDocumentationFindNo.ListingCreationRequestDTO();
      //expect(instance).to.be();
    });

    it('should have the property categoryName (base name: "categoryName")', function() {
      // uncomment below and update the code to test the property categoryName
      //var instance = new OpenApiDocumentationFindNo.ListingCreationRequestDTO();
      //expect(instance).to.be();
    });

    it('should have the property listingStatus (base name: "listingStatus")', function() {
      // uncomment below and update the code to test the property listingStatus
      //var instance = new OpenApiDocumentationFindNo.ListingCreationRequestDTO();
      //expect(instance).to.be();
    });

    it('should have the property price (base name: "price")', function() {
      // uncomment below and update the code to test the property price
      //var instance = new OpenApiDocumentationFindNo.ListingCreationRequestDTO();
      //expect(instance).to.be();
    });

    it('should have the property latitude (base name: "latitude")', function() {
      // uncomment below and update the code to test the property latitude
      //var instance = new OpenApiDocumentationFindNo.ListingCreationRequestDTO();
      //expect(instance).to.be();
    });

    it('should have the property longitude (base name: "longitude")', function() {
      // uncomment below and update the code to test the property longitude
      //var instance = new OpenApiDocumentationFindNo.ListingCreationRequestDTO();
      //expect(instance).to.be();
    });

  });

}));

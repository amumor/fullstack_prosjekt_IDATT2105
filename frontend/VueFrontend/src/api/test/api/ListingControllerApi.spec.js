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
    instance = new OpenApiDocumentationFindNo.ListingControllerApi();
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

  describe('ListingControllerApi', function() {
    describe('create', function() {
      it('should call create successfully', function(done) {
        //uncomment below and update the code to test create
        //instance.create(function(error) {
        //  if (error) throw error;
        //expect().to.be();
        //});
        done();
      });
    });
    describe('getById', function() {
      it('should call getById successfully', function(done) {
        //uncomment below and update the code to test getById
        //instance.getById(function(error) {
        //  if (error) throw error;
        //expect().to.be();
        //});
        done();
      });
    });
    describe('getSuggestions', function() {
      it('should call getSuggestions successfully', function(done) {
        //uncomment below and update the code to test getSuggestions
        //instance.getSuggestions(function(error) {
        //  if (error) throw error;
        //expect().to.be();
        //});
        done();
      });
    });
  });

}));

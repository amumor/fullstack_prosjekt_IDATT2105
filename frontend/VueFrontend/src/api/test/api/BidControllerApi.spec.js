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
    instance = new OpenApiDocumentationFindNo.BidControllerApi();
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

  describe('BidControllerApi', function() {
    describe('acceptBid', function() {
      it('should call acceptBid successfully', function(done) {
        //uncomment below and update the code to test acceptBid
        //instance.acceptBid(function(error) {
        //  if (error) throw error;
        //expect().to.be();
        //});
        done();
      });
    });
    describe('cancelBid', function() {
      it('should call cancelBid successfully', function(done) {
        //uncomment below and update the code to test cancelBid
        //instance.cancelBid(function(error) {
        //  if (error) throw error;
        //expect().to.be();
        //});
        done();
      });
    });
    describe('getAcceptedBids', function() {
      it('should call getAcceptedBids successfully', function(done) {
        //uncomment below and update the code to test getAcceptedBids
        //instance.getAcceptedBids(function(error) {
        //  if (error) throw error;
        //expect().to.be();
        //});
        done();
      });
    });
    describe('placeBid', function() {
      it('should call placeBid successfully', function(done) {
        //uncomment below and update the code to test placeBid
        //instance.placeBid(function(error) {
        //  if (error) throw error;
        //expect().to.be();
        //});
        done();
      });
    });
    describe('rejectBid', function() {
      it('should call rejectBid successfully', function(done) {
        //uncomment below and update the code to test rejectBid
        //instance.rejectBid(function(error) {
        //  if (error) throw error;
        //expect().to.be();
        //});
        done();
      });
    });
  });

}));

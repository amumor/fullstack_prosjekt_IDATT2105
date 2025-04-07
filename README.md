# READ ME

# Guides

## Update Frontend services if Backend endpoints are updated

### 1. Generate OpenAPI functions using this command:

```sh
npx @openapitools/openapi-generator-cli generate \
  -i http://localhost:8080/v3/api-docs \
  -g javascript \
  -o ./src/api \
  --additional-properties=usePromises=true,useES6=true
```

### 2. Removed everything OTHER than these files and folders:
- /api (the one containing the SomeControllerApi files)
- /model (containing the DTOs)
- /test
- ApiClient.js
- index.js

### 3. Comment out / remove user-Agent in ApiClient.js
As shown below:

```javascript
* The default HTTP headers to be included for all API calls.
* @type {Array.<String>}
* @default {}
*/
this.defaultHeaders = {
   // 'User-Agent': 'OpenAPI-Generator/1.0/Javascript'
};
```

### 4. Run npm install
First remove the old **node_modules** and **package-lock.json**, run this command in the project root dir containing **package.json**, **node_modules** folder and **package-lock.json** file
```sh
rm -rf node_modules package-lock.json
```

Run npm install in the same folder
```sh
npm install
```

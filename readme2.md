If the TypeScript plugin is not available in the IntelliJ IDEA marketplace or plugins, it's possible that you are using a version of IntelliJ IDEA that does not include built-in TypeScript support. In that case, you can still work with TypeScript files by configuring the project to use the TypeScript compiler externally.

Here's an alternative approach to build and run TypeScript code in IntelliJ IDEA without the TypeScript plugin:

Install Node.js:

Download and install Node.js from the official website: https://nodejs.org
Create a new JavaScript project:

Open IntelliJ IDEA and select "Create New Project" or "File" -> "New" -> "Project".
Choose "Empty Project" and click "Next".
Provide a name and location for the project and click "Finish".
Initialize the project:

Open a terminal within IntelliJ (View -> Tool Windows -> Terminal).
Navigate to the project directory using the terminal.
Run npm init to initialize the project and create a package.json file.
Install required dependencies:

Run npm install jose to install the jose library.
Create a new TypeScript file:

Right-click on the project root in the Project Explorer.
Select "New" -> "JavaScript File".
Provide a name for the file (e.g., generateJwtToken.ts) and click "OK".
Copy the code from the previous answer into the generateJwtToken.ts file.

Edit the generateJwtToken.ts file:

Replace <Private Key in PEM Format> with your actual private key in PEM format.
Replace <JSON Payload> with your desired JSON payload.
Build and run the TypeScript script:

Open a terminal within IntelliJ (View -> Tool Windows -> Terminal).
Navigate to the project directory using the terminal.
Run npx tsc generateJwtToken.ts to compile the TypeScript file.
Run node generateJwtToken.js to execute the generated JavaScript file.
The script should now execute, and the generated JWT token will be printed in the terminal window within IntelliJ IDEA.

This approach relies on using the external TypeScript compiler (tsc) and Node.js to build and run TypeScript code.

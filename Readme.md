import { JWK, JWT } from 'jose';

async function generateJwtToken(privateKeyPem: string, payloadJson: string): Promise<void> {
try {
// Load the PKCS8 private key from a PEM string
const privateKey = await JWK.asKey(privateKeyPem, 'pem');

    // Set the expiration time to 1 hour from now
    const expiresInOneHour = Math.floor(Date.now() / 1000) + 3600;

    // Generate the JWT token
    const token = JWT.sign(payloadJson, privateKey, {
      algorithm: 'RS512',
      expiresIn: expiresInOneHour,
    });

    console.log('Generated JWT token:');
    console.log(token);
} catch (error) {
console.error('Error generating JWT token:', error);
}
}

// Example usage
const privateKeyPem = `<Private Key in PEM Format>`;
const payloadJson = `<JSON Payload>`;

generateJwtToken(privateKeyPem, payloadJson);


To build and run the TypeScript script in IntelliJ IDEA, you need to set up a TypeScript project and configure the IDE accordingly. Here's a step-by-step guide:

1. Create a new TypeScript project:
  - Open IntelliJ IDEA and select "Create New Project" or "File" -> "New" -> "Project".
  - Choose "Empty Project" and click "Next".
  - Provide a name and location for the project and click "Finish".

2. Configure TypeScript compiler:
  - Right-click on the project root in the Project Explorer.
  - Select "Add Framework Support".
  - Choose "TypeScript" and click "OK".
  - Select "Create tsconfig.json" and click "OK".

3. Install required dependencies:
  - Open a terminal within IntelliJ (View -> Tool Windows -> Terminal).
  - Navigate to the project directory using the terminal.
  - Run `npm init` to initialize the project and create a `package.json` file.
  - Run `npm install jose` to install the `jose` library.

4. Create a new TypeScript file:
  - Right-click on the project root in the Project Explorer.
  - Select "New" -> "TypeScript File".
  - Provide a name for the file (e.g., `generateJwtToken.ts`) and click "OK".

5. Copy the code from the previous answer into the `generateJwtToken.ts` file.

6. Edit the `generateJwtToken.ts` file:
  - Replace `<Private Key in PEM Format>` with your actual private key in PEM format.
  - Replace `<JSON Payload>` with your desired JSON payload.

7. Build and run the TypeScript script:
  - Right-click on the `generateJwtToken.ts` file in the Project Explorer.
  - Select "Run 'generateJwtToken.ts'".

The script should now execute, and the generated JWT token will be printed in the Run tool window at the bottom of the IntelliJ IDEA interface.

Make sure you have Node.js and TypeScript installed on your system before following these steps.

const { JWK, JWT } = require('jose');

  exports.handler = async (event) => {
  try {
  // Load the PKCS8 private key
  const privateKey = JWK.asKey({
  key: '-----BEGIN PRIVATE KEY-----\n<Your PKCS8 Private Key>\n-----END PRIVATE KEY-----',
  format: 'pem',
  passphrase: '<Optional passphrase if your private key is encrypted>'
  });

  // JSON payload as a string
  const payloadString = '{"sub":"user_id","name":"John Doe","role":"admin"}';

  // Create the JWT token
  const token = JWT.sign(payloadString, privateKey, {
  algorithm: 'RS512' // Specify the RS512 algorithm used for signing
  // Add any other JWT options as needed
  });

  return {
  statusCode: 200,
  body: token
  };
  } catch (error) {
  console.error('Error creating JWT:', error);
  return {
  statusCode: 500,
  body: 'Internal Server Error'
  };
  }
  };

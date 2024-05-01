import React from 'react';
import { GoogleLogin } from 'react-google-login';

const clientId = "YOUR_CLIENT_ID.apps.googleusercontent.com";

function GoogleRegistrationButton() {
  const responseGoogle = (response: unknown) => {
    console.log(response);
  }

  return (
    <div>
      <GoogleLogin
        clientId={clientId}
        buttonText="Cadastrar com Google"
        onSuccess={responseGoogle}
        onFailure={responseGoogle}
        cookiePolicy={'single_host_origin'}
      />
    </div>
  );
}

export default GoogleRegistrationButton;
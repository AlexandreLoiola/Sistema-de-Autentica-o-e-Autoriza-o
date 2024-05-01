import React from 'react';
import { GoogleLogin } from 'react-google-login';

const clientId = "YOUR_CLIENT_ID.apps.googleusercontent.com";

const GoogleLoginButton: React.FC = () => {
  const responseGoogle = (response: unknown) => {
    console.log(response);
  }

  return (
    <div>
      <GoogleLogin
        clientId={clientId}
        buttonText="Acessar com Google"
        onSuccess={responseGoogle}
        onFailure={responseGoogle}
        cookiePolicy={'single_host_origin'}
      />
    </div>
  );
}

export default GoogleLoginButton;

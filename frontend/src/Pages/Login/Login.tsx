import React, { useState } from "react";
import { Container, ContainerLeft, StyledLink, StyledTitle } from "./styles";
import PasswordInput from "../../Components/Forms/PasswordForm";
import TextForm from "../../Components/Forms/TextForm";
import { FormContainer, StyledFormButton } from "../../Components/Forms/styles";
import CheckboxForm from "../../Components/Forms/CheckboxForm";
import ShieldContainer from "../../Components/ShieldContainer/ShieldContainer";
import Footer from "../../Components/Footer/Footer";
import GoogleLoginButton from "../../Components/Buttons/GoogleLoginButton";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const Login: React.FC = () => {
  const [username, setUsername] = useState<string>('');
  const [password, setPassword] = useState<string>('');

  const navigate = useNavigate();

  const handleLogin = async (email: string, password: string): Promise<void> => {
    try {
      axios
        .post(`http://localhost:8080/api/management/users/login`, {
          email: email,
          password: password,
        })
        .then((response) => {
          navigate("/", {state: {
            username: response.data.username,
            role: response.data.roles[0].description,
          }});
        })
        .catch((error) => {
          console.log(error);
          alert("Credenciais Inválidas!");
        });
    } catch (error) {
      console.error(error);
    }
  }

  return (
    <Container>
      <ContainerLeft>
        <StyledTitle>Login</StyledTitle>
        <FormContainer>
          <TextForm
            label={"Usuário"}
            placeHolder={""}
            message={""}
            value={username}
            onInputChange={(value) => setUsername(value)}
          />
          <PasswordInput
            label={"Senha"}
            placeHolder={""}
            message={""}
            value={password}
            onInputChange={(value) => setPassword(value)}
          />
          <StyledFormButton style={{ width: "100%" }} onClick={() => handleLogin(username, password)}>ENTRAR</StyledFormButton>
          <div
            style={{
              display: "flex",
              flexDirection: "row",
              width: "100%",
              marginTop: "1.5vh",
              justifyContent: "space-between",
            }}
          >
            <CheckboxForm
              label={"Lembrar-me"}
              message={""}
              onInputChange={function (value: string): void {
                throw new Error("Function not implemented.");
              }}
            />
            <StyledLink to="/forgot-password">Esqueceu a senha?</StyledLink>
          </div>
          <GoogleLoginButton />
          <div
            style={{
              width: "86%",
              borderTop: "1px solid #00000058",
              marginTop: "4vh",
              marginBottom: "6vh",
            }}
          ></div>
          <span>
            Não possui conta? <StyledLink to="/signup">Clique aqui</StyledLink>
          </span>
        </FormContainer>
      </ContainerLeft>
      <ShieldContainer />
      <Footer />
    </Container>
  );
};
export default Login;
